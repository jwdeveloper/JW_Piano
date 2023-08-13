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

import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginModels;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.keyboard.PianoKey;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestStuff implements EffectInvoker {

    private final Map<Integer, ObjectLineEntry> lines;
    private final SimpleTaskTimer taskRunner;
    private final ObjectLineEntry[] entries;

    public TestStuff() {
        taskRunner = FluentApi.tasks().taskTimer(1, this::onTask);
        entries = new ObjectLineEntry[88*4];
        lines = new HashMap<>();
    }

    @Override
    public void onCreate() {
        taskRunner.run();
    }

    @Override
    public void onDestroy() {
        lines.clear();

        for(var e : entries)
        {
            if(e == null)
            {
                continue;
            }
            e.destroy();
        }

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

        final var line = new ObjectLineEntry(color, location.clone().add(0, 1.6, 0));
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
            if (entries[i].isStopGrowing())
            {
                entries[i].moveLine();
            } else {
                entries[i].growTail();
            }

            if (entries[i].isDone()) {
                entries[i].destroy();
                entries[i] = null;
            }
        }
    }



    @Override
    public String getName() {
        return "test";
    }


    @Override
    public void refresh() {

    }

    public class ObjectLineEntry {

        private final int MAX_HEIGHT = 5;
        private final float PARTICLE_DISTANCE = 0.02f;
        private final float PARTICLE_SIZE = 0.11f;
        private final float SPEED = 0.05f;
        private final int GROW_SPEED = 2;
        private final Color color;

        private final double initialY;
        private float tailSize = 0;
        @Getter
        private boolean stopGrowing = false;
        private Vector3f position;
        private ItemDisplay itemDisplay;
        public ObjectLineEntry(Color color, Location location) {
            this.color = color;
            initialY = location.getY();
            itemDisplay= location.getWorld().spawn(location,ItemDisplay.class);
            var scale = new Vector3f(3.5f,2.5f,2.5f);
            position = new Vector3f(0,0,0);
            var transform = new Transformation(position,
                    new AxisAngle4f(0,0,0,0),
                    scale,
                    new AxisAngle4f(0,0,0,0));
            itemDisplay.setTransformation(transform);
            itemDisplay.setItemStack(PluginModels.FLYINGNOTE.toItemStack(color));
            itemDisplay.setBrightness(new Display.Brightness(14,14));

        }

        public void moveLine() {
            position.add(0, SPEED, 0);
            render();
        }

        public void growTail() {
            tailSize += 0.2f;
            position.y = tailSize/2;
            render();
        }

        private void destroy()
        {
            itemDisplay.remove();
        }

        private void render() {

            Vector3f scale = null;
            if(tailSize <1)
            {
                scale = new Vector3f(10.5f,tailSize,10.5f);
            }

            scale = new Vector3f(0.1f,tailSize,0.1f);
            var transform = new Transformation(position,
                    new AxisAngle4f(0,0,0,0),
                    scale,
                    new AxisAngle4f(0,0,0,0));


            itemDisplay.setInterpolationDelay(0);
            itemDisplay.setTransformation(transform);

            if(stopGrowing)
            {
                itemDisplay.setInterpolationDuration(1);
            }
            else
            {
                itemDisplay.setInterpolationDuration(1);
            }

        }

        public void endLine() {
            stopGrowing = true;
        }

        public boolean isDone() {
            return stopGrowing && position.y()  >= MAX_HEIGHT;
        }
    }
}
