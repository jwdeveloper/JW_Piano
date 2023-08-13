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
package io.github.jwdeveoper.spigot.piano.core.midi_player;

import io.github.jwdeveoper.spigot.piano.core.midi_player.jw.PianoNodeEntry;
import lombok.Getter;

import java.util.Set;


public class NoteFrame {
    private final long wait;


    public long getWait() {
        return wait;
    }

    @Getter
    private final PianoNodeEntry[] notes;

    public NoteFrame(long delta, Set<TrackEntry> notes) {
        wait = delta;

        if (notes == null) {
            this.notes = new PianoNodeEntry[0];
        } else {
            this.notes = new PianoNodeEntry[notes.size()];
            int i = 0;
            for (TrackEntry entry : notes) {
                this.notes[i] = entry.getNote();
                i++;
            }
        }
    }

}
