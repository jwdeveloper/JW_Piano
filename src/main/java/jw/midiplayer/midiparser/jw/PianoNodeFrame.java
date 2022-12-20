package jw.midiplayer.midiparser.jw;

import jw.midiplayer.midiparser.NoteFrame;
import jw.midiplayer.midiparser.TrackEntry;

import java.util.Set;

public class PianoNodeFrame extends NoteFrame
{
    public PianoNodeFrame(long delta, Set<TrackEntry> notes) {
        super(delta, notes);
    }
}
