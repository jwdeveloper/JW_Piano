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

package io.github.jwdeveloper.spigot.piano.api.observers;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.spigot.piano.api.data.midi.MidiPlayerData;
import io.github.jwdeveloper.spigot.piano.api.enums.MidiPlayerType;
import lombok.Getter;

//@JW generated code source don't modify it

@Getter
public class MidiPlayerSettingsObserver{
    private final MidiPlayerData midiPlayerSettings;

    private final Observer<Boolean> isPlayingInLoop;

    private final Observer<Boolean> isPlaying;

    private final Observer<Integer> speed;

    private final Observer<MidiPlayerType> playingType;


    public  MidiPlayerSettingsObserver(MidiPlayerData midiPlayerSettings)
    {
        this.midiPlayerSettings = midiPlayerSettings;
        isPlayingInLoop = new Observer<>(midiPlayerSettings,"isPlayingInLoop");
        isPlaying = new Observer<>(midiPlayerSettings,"isPlaying");
        speed = new Observer<>(midiPlayerSettings,"speed");
        playingType = new Observer<>(midiPlayerSettings,"playingType");
    }

}
