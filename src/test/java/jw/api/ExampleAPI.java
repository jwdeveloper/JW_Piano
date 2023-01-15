/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.api;

import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.Piano;
import jw.piano.api.piano.keyboard.PianoKey;
import jw.piano.spigot.PianoApi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Optional;

public class ExampleAPI {


    public void creatingPiano(Player player) {
        Optional<Piano> optional = PianoApi.create(player.getLocation(), "new piano");
        if (optional.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Unable to create piano ;<");
        }
        Piano piano = optional.get();
    }

    public void addSound(Player player) {
        Optional<Piano> optional = PianoApi.create(player.getLocation(), "new piano");
        if (optional.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Unable to create piano ;<");
        }
        Piano piano = optional.get();
        PianoSound customSound = new PianoSound();
        customSound.setName("custom sound");
        customSound.setNamespace("minecraft");
        customSound.setSoundCategory(SoundCategory.VOICE);

        HashMap<Integer, String> soundsWithoutPedalMapping = new HashMap<>();
        soundsWithoutPedalMapping.put(21,"folder.withoutpedal.sound_21");

        customSound.setSoundsWithoutPedal(soundsWithoutPedalMapping);

        HashMap<Integer, String> soundsMapping = new HashMap<>();
        soundsMapping.put(21,"folder.withpedal.sound_21");
        customSound.setSoundsWithPedal(soundsMapping);

        piano.getSoundsManager().register(customSound);
        piano.getSoundsManager().setCurrent(customSound);
    }


    public void addSkin(Piano piano) {
        int customModelId = 100;
        String name = "custom skin";
        ItemStack itemStack = new ItemStack(Material.STICK);
        PianoSkin customSkin = new PianoSkin(customModelId, name, itemStack);
        piano.getSkinManager().register(customSkin);
        piano.getSkinManager().setCurrent(customSkin);
    }

    public void setColor(Player player) {
        Optional<Piano> optional = PianoApi.create(player.getLocation(), "new piano");
        if (optional.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Unable to create piano ;<");
        }
        Piano piano = optional.get();
        piano.setColor(Color.AQUA);
    }


    public void addNewEffect(Player player) {
        Optional<Piano> optional = PianoApi.create(player.getLocation(), "new piano");
        if (optional.isEmpty()) {
            Bukkit.getConsoleSender().sendMessage("Unable to create piano ;<");
        }
        Piano piano = optional.get();
        EffectInvoker customEffect = new CustomEffect();
        piano.getEffectManager().register(customEffect);
        piano.getEffectManager().setCurrent(customEffect);
    }


    public class CustomEffect implements EffectInvoker {
        @Override
        public String getName() {
            return "custom";
        }

        @Override
        public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color, boolean isPressed) {
            Bukkit.getConsoleSender().sendMessage(color + "Note: " + noteIndex + "  Volume:" + velocity);
            location.getWorld().spawnParticle(Particle.NOTE, location, 1);
        }


        @Override
        public void onDestroy() {
            Bukkit.getConsoleSender().sendMessage(getName() + "Destroyed");
        }

        @Override
        public void onCreate() {
            Bukkit.getConsoleSender().sendMessage(getName() + "Created");
        }

        @Override
        public void refresh() {
            Bukkit.getConsoleSender().sendMessage(getName() + "Refreshed");
        }
    }


}
