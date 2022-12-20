package jw.midiplayer.midiparser.jw;

import jw.midiplayer.midiparser.NoteEntry;
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
