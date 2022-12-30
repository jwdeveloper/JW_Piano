package jw.piano.api.midiplayer.jw;

import jw.piano.api.midiplayer.NoteFrame;
import jw.piano.api.midiplayer.TrackEntry;

import java.util.Set;

public class PianoNodeFrame extends NoteFrame
{
    public PianoNodeFrame(long delta, Set<TrackEntry> notes) {
        super(delta, notes);
    }
}
