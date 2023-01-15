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

package jw.piano.spigot.piano.keyboard;

import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.fluent.api.spigot.permissions.implementation.PermissionsUtility;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.data.models.keyboard.KeyboardTrackColor;
import jw.piano.api.managers.effects.EffectManager;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.api.piano.keyboard.Keyboard;
import jw.piano.api.piano.keyboard.PianoKey;

import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Comparator;

public class KeyboardImpl extends GameObject implements Keyboard {

    @Getter
    private final PianoKey[] pianoKeys;
    private PianoKey[] sortedByColor;
    private final PianoData pianoData;
    private final EffectManager effectManager;
    private final SoundsManager soundsManager;

    public KeyboardImpl(PianoData data, EffectManager effectManager, SoundsManager soundPlayerFactory) {
        pianoKeys = new PianoKey[88];
        this.pianoData = data;
        this.effectManager = effectManager;
        this.soundsManager = soundPlayerFactory;
    }


    @Override
    public void onCreate()
    {
        var colors = pianoData.getKeyboardSettings().getMidiTrackColors();
        if(colors.size() == 0)
        {
            colors.add(new KeyboardTrackColor(1, Color.GREEN, "default"));
            colors.add(new KeyboardTrackColor(2, Color.BLUE, "default"));
            colors.add(new KeyboardTrackColor(3, Color.RED, "default"));
        }

        var startKeysLocation = location.clone().add(-1.5, -0.4, 0.3f);
        startKeysLocation.setDirection(new Vector(0, 1, 0));
        var key = 1;
        PianoKeyImpl pianoKey;
        Location pianoKeyLocation;
        for (int i = 1; i <= 88; i++) {
            if (i > 3 && i < 88) {
                key = (i - 4) % 12;
            }
            if (i <= 3) {
                key = i + 8;
            }
            switch (key) {
                case 1, 3, 6, 8, 10 -> {
                    pianoKey = new PianoKeyImpl(
                            pianoData,
                            soundsManager,
                            effectManager,
                            true,
                            i + PluginConsts.MIDI_KEY_OFFSET - 1);
                    pianoKeyLocation = startKeysLocation.clone().add(0.025f, 1.52f, -0.05f);
                }
                default -> {
                    pianoKey = new PianoKeyImpl(
                            pianoData,
                            soundsManager,
                            effectManager,
                            false,
                            i + PluginConsts.MIDI_KEY_OFFSET - 1);
                     pianoKeyLocation = startKeysLocation.clone().add(0.05f, 1.5, 0);
                     startKeysLocation = startKeysLocation.clone().add(0.05f, 0, 0);
                }
            }
            pianoKey.setLocation(pianoKeyLocation);
            pianoKeys[i - 1] = addGameComponent(pianoKey);
        }
        sortedByColor = getSortedByColor();
    }


    public void triggerNote(int pressed, int midiIndex, int velocity, int trackId) {
        if (midiIndex < PluginConsts.MIDI_KEY_OFFSET)
            return;
        if (midiIndex - PluginConsts.MIDI_KEY_OFFSET > pianoKeys.length - 1)
            return;

        if (pressed != 0) {
            pianoKeys[midiIndex - PluginConsts.MIDI_KEY_OFFSET].press(velocity, trackId);
        } else
            pianoKeys[midiIndex - PluginConsts.MIDI_KEY_OFFSET].release();
    }


    @Override
    public void refresh() {
        for(var key : pianoKeys)
        {
            key.refresh();
        }
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        if (!pianoData.getInteractiveKeyboard()) {
            return false;
        }
        if(!PermissionsUtility.hasOnePermission(event.getPlayer(), PluginPermissions.PIANO.KEYBOARD.USE))
        {
            return false;
        }
        for (var key : sortedByColor) {
            if (key.triggerPlayerClick(event)) {
                return true;
            }
        }
        return false;
    }

    public void reset()
    {
        for(var key : pianoKeys)
        {
            key.release();
        }
    }

    @Override
    public void teleport(Location location) {

    }

    private PianoKey[] getSortedByColor() {
        var keys = pianoKeys.clone();
        Arrays.sort(keys, new Comparator<PianoKey>() {
            @Override
            public int compare(PianoKey o1, PianoKey o2) {
                return Boolean.compare(o1.isWhite(), o2.isWhite());
            }
        });
        return keys;
    }
}
