package jw.piano.api.midiplayer.jw;

import jw.piano.api.midiplayer.TrackEntry;
import lombok.Getter;
import lombok.Setter;

public class PianoTrackEntry extends TrackEntry {
    @Getter
    @Setter
    public int eventId;

    @Getter
    @Setter
    public int keyIndex;


    public PianoTrackEntry(long millis, int octave, int note, float volume) {
        super(millis,  octave, note, volume);
    }


    public void updateNoteEntry() {
        this.m_note = new PianoNodeEntry(
                m_note.getM_frq(),
                m_note.getVelocity(),
                keyIndex,
                eventId);
    }

}
