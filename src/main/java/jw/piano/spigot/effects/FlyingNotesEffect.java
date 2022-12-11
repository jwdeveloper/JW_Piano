package jw.piano.spigot.effects;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.PluginConsts;
import jw.piano.data.enums.PianoKeysConst;
import jw.piano.spigot.effects.api.PianoEffectInvoker;
import jw.piano.factory.ArmorStandFactory;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FlyingNotesEffect implements PianoEffectInvoker {

    private double maxHeight = 3;
    private double worldMaxHeight = -1;
    private List<FlyingNote> notes;
    private double speed = 0.05f;
    private SimpleTaskTimer taskTimer;
    private final ArmorStandFactory armorStandFactory;

    public FlyingNotesEffect() {
        notes = new ArrayList<>(200);
        armorStandFactory = FluentApi.container().findInjection(ArmorStandFactory.class);

    }

    @Override
    public void invoke(Location location, int noteIndex, int sensivity) {
        if (worldMaxHeight == -1) {
            worldMaxHeight = location.getY() + maxHeight;
        }

        location = location.clone().add(0, 0, 0);
        var note = notes.stream().filter(c -> c.free).findFirst();
        if (note.isPresent()) {
            note.get().enable(location);
            return;
        }

        var flyingNote = new FlyingNote(noteIndex, location);
        flyingNote.enable(location);
        notes.add(flyingNote);
    }

    @Override
    public void destroy() {
        for (var note : notes) {
            note.armorStand.remove();
        }
        taskTimer.stop();
    }

    @Override
    public void create() {
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

        public static FlyingNote instance;

        public FlyingNote(int index, Location location) {
            this.location = location;
            this.lastLoc = location;
            pianoY = location.getY();
            armorStand = armorStandFactory.create(location, "");
            armorStand.setSmall(true);
            itemStack = new ItemStack(PluginConsts.SKINS_MATERIAL, 1);
            air = new ItemStack(Material.AIR, 1);
            var meta = itemStack.getItemMeta();
            meta.setCustomModelData(PianoKeysConst.FLYING_NOTE.getId());
            itemStack.setItemMeta(meta);
            locks = maxLocks;
            if (instance == null) {
                instance = this;
            }
        }


        public void enable(Location location) {
            this.lastLoc = this.location;
            this.location = location.clone();
            //  armorStand.teleport(location);
            //    armorStand.setHelmet(itemStack);
            //   FluentLogger.log(lastLoc.getY()+" - "+location.getY());
            free = false;
        }

        public void move() {
            locks -= 1;
            if (locks == 5) {
                armorStand.teleport(location);
            }

            if (locks == 1) {
                armorStand.setHelmet(itemStack);
            }

            if (locks <= 0) {
                armorStand.teleport(location.add(0, speed, 0));
            }
            //   armorStand.teleport(location.add(0,speed,0));

        }

        int disableLocks = 7;
        int maxDisableLocks = 7;
        boolean disabled = false;


        public void postDisable() {
            if (disableLocks == 7) {
                armorStand.setHelmet(air);

            }
            if (disableLocks == 4) {
                armorStand.teleport(new Location(location.getWorld(), location.getX(), pianoY, location.getZ()));
                //FluentLogger.log("teleporting");
            }

            if (disableLocks == 0) {
                free = true;
                locks = maxLocks;
                disabled = false;
                //FluentLogger.log("ready");
            }
            disableLocks--;
            // FluentLogger.log("post disable");
        }

        public void disable() {
            disabled = true;
            disableLocks = maxDisableLocks;
        }
    }
}
