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

public class TrackEntry {

    private long milis;

    protected PianoNodeEntry noteEntry;

    public PianoNodeEntry getNote() {
        return noteEntry;
    }

    public long getMillis() {
        return milis;
    }

    public void setMillis(long milis) {
        this.milis = milis;
    }

    public PianoNodeEntry getEntry() {
        return noteEntry;
    }


    public TrackEntry(long millis, int octave, int note, float volume) {
        milis = millis;

        final float frq = (float) Math.pow(2, (note + 12 * (octave % 2) - 12.0) / 12.0);
        final float vv = Math.max(0, Math.min(1, volume)) * 3.0f;

        noteEntry = new PianoNodeEntry(frq, vv,-1,-1,-1);
    }

    @Override
    public int hashCode() {
        return (noteEntry != null ? noteEntry.hashCode() : 0) ^
                ((Long) milis).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        TrackEntry other = obj instanceof TrackEntry ? (TrackEntry) obj : null;
        if (other == null) {
            return false;
        }

        return milis == other.milis &&
                ((noteEntry == null && other.noteEntry == null) ||
                        (noteEntry != null && noteEntry.equals(other.noteEntry)));
    }


}
