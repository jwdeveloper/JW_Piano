package jw.piano.api.midiplayer.midiparser.jw;

import jw.piano.api.midiplayer.midiparser.TrackEntry;
import jw.piano.api.midiplayer.midiparser.instruments.Instrument;
import lombok.Getter;
import lombok.Setter;

public class PianoTrackEntry extends TrackEntry {
    @Getter
    @Setter
    public int eventId;

    @Getter
    @Setter
    public int keyIndex;


    public PianoTrackEntry(long millis, Instrument instrument, int octave, int note, float volume) {
        super(millis, instrument, octave, note, volume);
    }


    public void updateNoteEntry() {
        this.m_note = new PianoNodeEntry(m_note.getM_instrumentPatch(),
                m_note.getM_frq(),
                m_note.getVelocity(),
                keyIndex,
                eventId);
    }

}
