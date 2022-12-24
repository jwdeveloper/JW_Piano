package jw.piano.api.midiplayer.midiparser.jw;

import jw.piano.api.midiplayer.midiparser.NoteFrame;
import jw.piano.api.midiplayer.midiparser.TrackEntry;

import java.util.Set;

public class PianoNodeFrame extends NoteFrame
{
    public PianoNodeFrame(long delta, Set<TrackEntry> notes) {
        super(delta, notes);
    }
}
