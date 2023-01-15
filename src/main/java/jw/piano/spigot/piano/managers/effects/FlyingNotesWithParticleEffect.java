/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot.piano.managers.effects;

import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.keyboard.PianoKey;
import jw.piano.core.factory.ArmorStandFactory;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class FlyingNotesWithParticleEffect implements EffectInvoker {
    private double maxHeight = 3;
    private double worldMaxHeight = -1;
    private List<FlyingNotesWithParticleEffect.FlyingNote> notes;
    private double speed = 0.05f;
    private SimpleTaskTimer taskTimer;
    private final ArmorStandFactory armorStandFactory;

    private final PianoData pianoData;

    public FlyingNotesWithParticleEffect(PianoData pianoData) {
        notes = new ArrayList<>(200);
        this.pianoData = pianoData;
        armorStandFactory = FluentApi.container().findInjection(ArmorStandFactory.class);

    }

    @Override
    public String getName() {
        return "flying notes with particle";
    }

    @Override
    public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color, boolean isPressed) {
        if (worldMaxHeight == -1) {
            worldMaxHeight = location.getY() + maxHeight;
        }

        location = location.clone().add(0, 0, 0);
        var note = notes.stream().filter(c -> c.free).findFirst();
        if (note.isPresent()) {
            note.get().enable(location, color);
            return;
        }

        var flyingNote = new FlyingNotesWithParticleEffect.FlyingNote(noteIndex, location);
        flyingNote.enable(location, color);
        notes.add(flyingNote);
    }


    @Override
    public void onDestroy() {
        for (var note : notes) {
            note.armorStand.remove();
        }
        taskTimer.stop();
    }

    @Override
    public void onCreate() {
        notes.clear();
        taskTimer = FluentTasks.taskTimer(1, (iteration, task) ->
        {
            for (var note : notes) {

                if (note.disabled) {
                    note.postDisable();
                    continue;
                }

                if (note.free) {
                    continue;
                }
                if (note.location.getY() + speed > worldMaxHeight) {
                    note.disable();
                    continue;
                }

                note.move();
            }
        }).run();
    }

    @Override
    public void refresh() {
        onDestroy();
        onCreate();
    }


    public class FlyingNote {
        public ArmorStand armorStand;
        public ItemStack itemStack;
        public boolean free = false;
        public Location location;
        public Location lastLoc;
        public ItemStack air;

        public int maxLocks = 6;
        public int locks;

        public double pianoY;

        public static FlyingNotesWithParticleEffect.FlyingNote instance;

        int disableLocks = 7;
        int maxDisableLocks = 7;
        boolean disabled = false;

        public FlyingNote(int index, Location location) {
            this.location = location;
            this.lastLoc = location;
            pianoY = location.getY();
            armorStand = armorStandFactory.create(location, pianoData.getUuid().toString());
            armorStand.setSmall(true);


            itemStack = new ItemStack(PluginConsts.MATERIAL, 1);
            air = new ItemStack(Material.AIR, 1);
            var meta = itemStack.getItemMeta();
            meta.setCustomModelData(PluginModels.FLYINGNOTE.id());
            itemStack.setItemMeta(meta);
            locks = maxLocks;
            if (instance == null) {
                instance = this;
            }
        }

        private Color color;

        public void enable(Location location, Color color) {
            this.lastLoc = this.location;
            this.location = location.clone();


            if (itemStack.getItemMeta() instanceof LeatherArmorMeta meta) {
                this.color = color;
                meta.setColor(color);
                itemStack.setItemMeta(meta);
            }
            free = false;
        }

        public void move() {
            locks -= 1;
            if (locks == 5) {
                armorStand.teleport(location);

            }

            if (locks == 1) {
                armorStand.getEquipment().setHelmet(itemStack);
            }

            if (locks <= 0) {
                armorStand.teleport(location.add(0, speed, 0));
                location.getWorld().spawnParticle(Particle.REDSTONE, location.clone().add(0,1.5f,0), 1, new Particle.DustOptions(color, 0.2f));
            }


        }


        public void postDisable() {
            if (disableLocks == 7) {
                armorStand.setHelmet(air);

            }
            if (disableLocks == 4) {
                armorStand.teleport(new Location(location.getWorld(), location.getX(), pianoY, location.getZ()));
            }

            if (disableLocks == 0) {
                free = true;
                locks = maxLocks;
                disabled = false;
            }
            disableLocks--;
        }

        public void disable() {
            disabled = true;
            disableLocks = maxDisableLocks;
        }
    }
}
