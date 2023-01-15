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
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.keyboard.PianoKey;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MidiPlayerEffect implements EffectInvoker {

    private final Map<Integer, ColorLineEntry> lines;
    private final SimpleTaskTimer taskRunner;
    private final ColorLineEntry[] entries;
    public MidiPlayerEffect() {
        taskRunner = FluentApi.tasks().taskTimer(1, this::onTask);
        entries = new ColorLineEntry[88*4];
        lines = new HashMap<>();
    }

    @Override
    public void onCreate() {
        taskRunner.run();
    }

    @Override
    public void onDestroy() {
        lines.clear();
        Arrays.fill(entries, null);
        taskRunner.stop();
    }


    @Override
    public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color, boolean isPressed) {
        if (lines.containsKey(noteIndex)) {
            lines.get(noteIndex).endLine();
            lines.remove(noteIndex);
            return;
        }

        if (!isPressed) {
            return;
        }

        final var line = new ColorLineEntry(color, location.clone().add(0, 1.6, 0));
        lines.put(noteIndex, line);
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                entries[i] = line;
                return;
            }
        }
    }

    public void onTask(int iteration, SimpleTaskTimer task) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                continue;
            }
            if (entries[i].isStopGrowing()) {
                entries[i].moveLine();
            } else {
                if (iteration % 2 == 0) {
                    entries[i].growTail();
                }
            }

            if (entries[i].isDone()) {
                entries[i] = null;
            }
        }
    }

    @Override
    public String getName() {
        return "midi player";
    }


    @Override
    public void refresh() {

    }

    public class ColorLineEntry {

        private final int MAX_HEIGHT = 5;
        private final float PARTICLE_DISTANCE = 0.02f;
        private final float PARTICLE_SIZE = 0.11f;
        private final float SPEED = 0.05f;

        private final int GROW_SPEED = 2;
        private final Color color;

        private final double initialY;
        private Location location;
        private int tailSize = 1;
        @Getter
        private boolean stopGrowing = false;
        public ColorLineEntry(Color color, Location location) {
            this.color = color;
            this.location = location;
            initialY = location.getY();
        }

        public void moveLine() {
            location.add(0, SPEED, 0);
            render();
        }

        public void growTail() {
            tailSize += GROW_SPEED;
            render();
        }


        private void render() {
            for (var i = 0; i < tailSize; i++) {
                var y = location.getY() + i * PARTICLE_DISTANCE;
                location.getWorld()
                        .spawnParticle(Particle.REDSTONE,
                                location.getX(),
                                y,
                                location.getZ(),
                                1,
                                new Particle.DustOptions(color, PARTICLE_SIZE));
                if (y - initialY >= MAX_HEIGHT) {
                    break;
                }
            }
        }

        public void endLine() {
            stopGrowing = true;
        }

        public boolean isDone() {
            return location.getY() - initialY >= MAX_HEIGHT;
        }
    }



}
