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
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.keyboard.PianoKey;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WaterfallEffect implements EffectInvoker {
    private SimpleTaskTimer taskTimer;
    private final List<AdvancedParticleDto> particles;
    private final int maxTime = 60;
    private final double particleHeight = 3.5;
    private final double particleStartY = 1.7;
    private final double particleWidthZ =0.5;
    private final double particleSpeedZ = 0.005;

    private final float PARTICLE_SIZE = 0.15f;
    public WaterfallEffect() {
        particles = new ArrayList<>(100);
        taskTimer = FluentTasks.taskTimer(1,this::onTask);
    }

    @Override
    public String getName() {
        return "waterfall";
    }

    @Override
    public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color, boolean isPressed) {
        if(!isPressed)
        {
            return;
        }
        location = location.clone().add(0, 0, 0);
        var note = particles.stream().filter(c -> c.free).findFirst();
        if (note.isPresent()) {
            note.get().enable(location, color);
            return;
        }

        var particle = new AdvancedParticleDto();
        particle.enable(location, color);
        particles.add(particle);
    }



    @Override
    public void onCreate() {
        taskTimer.run();
    }


    public void onTask(int iteration, SimpleTaskTimer task)
    {
        for (var particle : particles) {
            if (particle.free) {
                continue;
            }
            if (particle.time > maxTime) {
                particle.free = true;
                continue;
            }
            particle.next();
        }
    }
    @Override
    public void onDestroy() {
        taskTimer.stop();
        particles.clear();
    }



    @Override
    public void refresh() {

    }

    class AdvancedParticleDto {
        public boolean free;
        private Location location;
        private World world;
        private int time;

        private Particle.DustOptions options;

        public void enable(Location location, Color color) {
            this.location = location.clone();
            world = location.getWorld();
            time = 0;
            free = false;
            options = new Particle.DustOptions(color,PARTICLE_SIZE);
        }

        public void next() {
            world.spawnParticle(Particle.REDSTONE,
                    location.clone().add(
                            0,
                            particleStartY + Math.sin(Math.toRadians(time) * particleHeight) * particleWidthZ,
                            -time * particleSpeedZ),
                    1,
                    options);
            time++;
        }
    }
}
