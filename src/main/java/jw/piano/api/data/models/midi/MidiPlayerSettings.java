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

package jw.piano.api.data.models.midi;
import jw.piano.api.data.enums.MidiPlayingType;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class MidiPlayerSettings
{
    private Boolean isPlayingInLoop = false;
    private Boolean isPlaying = false;
    private Integer speed = 50;
    private MidiPlayingType playingType = MidiPlayingType.IN_ORDER;

    @Getter
    private List<PianoMidiFile> midiFiles = new ArrayList<>();

    public void addMidiFile(PianoMidiFile midiFile)
    {
        if(midiFiles.contains(midiFile))
        {
            return;
        }

        midiFiles.add(midiFile);
    }

    public void removeMidiFile(PianoMidiFile midiFile)
    {
        midiFiles.remove(midiFile);
    }


    public boolean hasMidiFiles()
    {
        return midiFiles.size() !=0;
    }

}
