package jw.piano.api.piano;

import jw.piano.api.data.enums.MidiPlayingType;
import jw.piano.api.data.models.midi.PianoMidiFile;
import jw.piano.api.observers.MidiPlayerSettingsObserver;

import java.util.List;

public interface MidiPlayer
{
    void next();
    void previous();
    void play();

    void stop();

    void setPlayMode(MidiPlayingType type);

    void setCurrentSong(PianoMidiFile midiFile);

    void addSong(PianoMidiFile midiFile);
    void removeSong(PianoMidiFile midiFile);

    PianoMidiFile getCurrentSong();

    List<PianoMidiFile> getSongs();

    public MidiPlayerSettingsObserver getObserver();
}
