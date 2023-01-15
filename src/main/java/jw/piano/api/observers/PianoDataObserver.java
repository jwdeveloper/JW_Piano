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

package jw.piano.api.observers;

import lombok.Getter;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.piano.api.data.models.PianoData;
import org.bukkit.Location;
import java.lang.Integer;
import java.lang.Boolean;
import java.lang.Boolean;
import java.lang.Boolean;
import java.lang.Boolean;
import java.lang.String;
import java.lang.String;
import java.lang.String;
import org.bukkit.Color;
import jw.piano.api.data.models.keyboard.KeyboardSettings;
import jw.piano.api.data.models.PedalsSettings;
import jw.piano.api.data.models.BenchSettings;
import jw.piano.api.data.models.midi.MidiPlayerSettings;

//@JW generated code source don't modify it

@Getter
public class PianoDataObserver{
    private final PianoData pianoData;

    private final Observer<Location> location;

    private final Observer<Integer> volume;

    private final Observer<Boolean> desktopClientAllowed;

    private final Observer<Boolean> interactiveKeyboard;

    private final Observer<Boolean> showPianist;

    private final Observer<Boolean> active;

    private final Observer<String> skinName;

    private final Observer<String> effectName;

    private final Observer<String> soundName;

    private final Observer<Color> color;

    private final KeyboardSettingsObserver keyboardSettings;

    private final PedalsSettingsObserver pedalsSettings;

    private final BenchSettingsObserver benchSettings;

    private final MidiPlayerSettingsObserver midiPlayerSettings;


    public  PianoDataObserver(PianoData pianoData)
    {
        this.pianoData = pianoData;
        location = new Observer<>(pianoData,"location");
        volume = new Observer<>(pianoData,"volume");
        desktopClientAllowed = new Observer<>(pianoData,"desktopClientAllowed");
        interactiveKeyboard = new Observer<>(pianoData,"interactiveKeyboard");
        showPianist = new Observer<>(pianoData,"showPianist");
        active = new Observer<>(pianoData,"active");
        skinName = new Observer<>(pianoData,"skinName");
        effectName = new Observer<>(pianoData,"effectName");
        soundName = new Observer<>(pianoData,"soundName");
        color = new Observer<>(pianoData,"color");
        keyboardSettings = new KeyboardSettingsObserver(pianoData.getKeyboardSettings());
        pedalsSettings = new PedalsSettingsObserver(pianoData.getPedalsSettings());
        benchSettings = new BenchSettingsObserver(pianoData.getBenchSettings());
        midiPlayerSettings = new MidiPlayerSettingsObserver(pianoData.getMidiPlayerSettings());
    }

}
