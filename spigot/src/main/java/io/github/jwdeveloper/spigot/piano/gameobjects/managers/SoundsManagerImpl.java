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

package io.github.jwdeveloper.spigot.piano.gameobjects.managers;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.core.services.SoundLoaderService;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class SoundsManagerImpl implements SoundsManager {
    private final HashMap<String, PianoSound> values;
    private PianoSound current;

    @Inject
    public SoundsManagerImpl(SoundLoaderService factory)
    {
        values = new LinkedHashMap<>();
        current = factory.getDefaultSound();
        for(var sounds : factory.getSounds())
        {
            register(sounds);
        }
    }

    public void play(final Location location,
                     final int midiSoundIndex,
                     final float volume,
                     final boolean isPedalPressed) {

        final var world = location.getWorld();
        if (world == null) {
            return;
        }
        world.playSound(location,
                current.getSound(midiSoundIndex, isPedalPressed),
                current.getSoundCategory(),
                volume,
                1);
    }

    @Override
    public PianoSound getCurrent() {
        return current;
    }

    @Override
    public List<PianoSound> getItems() {
        return values.values().stream().toList();
    }

    @Override
    public void setCurrent(String name) {
        if (!values.containsKey(name)) {
            return;
        }
        current = values.get(name);
    }

    @Override
    public void setCurrent(PianoSound value) {
        if (!values.containsValue(value)) {
            register(value);
        }
        current = value;
    }

    @Override
    public void register(PianoSound value) {
        if (values.containsValue(value)) {
            return;
        }
        values.put(value.getName(), value);
    }

    @Override
    public void unregister(PianoSound value) {
        if (!values.containsValue(value)) {
            return;
        }
        values.remove(value.getName());
    }
}
