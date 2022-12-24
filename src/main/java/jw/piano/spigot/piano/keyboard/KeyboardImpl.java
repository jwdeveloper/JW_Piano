package jw.piano.spigot.piano.keyboard;

import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.managers.effects.EffectManager;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.api.piano.keyboard.Keyboard;
import jw.piano.api.piano.keyboard.PianoKey;

import lombok.Getter;
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
    public void onCreate() {
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
                    pianoKeyLocation = startKeysLocation.clone().add(0.025f, 0.02f, -0.05f);
                }
                default -> {
                    pianoKey = new PianoKeyImpl(
                            pianoData,
                            soundsManager,
                            effectManager,
                            false,
                            i + PluginConsts.MIDI_KEY_OFFSET - 1);
                    pianoKeyLocation = startKeysLocation.clone().add(0.05f, 0, 0);
                    startKeysLocation = startKeysLocation.clone().add(0.05f, 0, 0);
                }
            }
            pianoKey.setLocation(pianoKeyLocation);
            pianoKeys[i - 1] = addGameComponent(pianoKey);
        }
        sortedByColor = getSortedByColor();
    }


    public void triggerNote(int pressed, int midiIndex, int velocity) {
        if (midiIndex < PluginConsts.MIDI_KEY_OFFSET)
            return;
        if (midiIndex - PluginConsts.MIDI_KEY_OFFSET > pianoKeys.length - 1)
            return;

        if (pressed != 0) {
            pianoKeys[midiIndex - PluginConsts.MIDI_KEY_OFFSET].press(velocity);

        } else
            pianoKeys[midiIndex - PluginConsts.MIDI_KEY_OFFSET].release();
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        if (!pianoData.getInteractiveKeyboard()) {
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
