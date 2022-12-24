package jw.piano.api.midiplayer.midiparser.jw;

import jw.piano.api.midiplayer.midiparser.NoteEntry;
import lombok.Getter;


public class PianoNodeEntry extends NoteEntry {

    @Getter
    private int index;

    @Getter
    private int event;


    public PianoNodeEntry(String instrumentPatch, float frq, float volume, int index, int event)
    {
        super(instrumentPatch, frq, volume);
        this.index = index;
        this.event = event;
    }
}
