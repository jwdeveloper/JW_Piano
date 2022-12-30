package jw.piano.api.midiplayer.jw;

import jw.piano.api.midiplayer.NoteEntry;
import lombok.Getter;


public class PianoNodeEntry extends NoteEntry {

    @Getter
    private int index;

    @Getter
    private int event;


    public PianoNodeEntry( float frq, float volume, int index, int event)
    {
        super(frq, volume);
        this.index = index;
        this.event = event;
    }
}
