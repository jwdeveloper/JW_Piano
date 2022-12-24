package jw.piano.api.piano;

public interface MidiPlayer
{
    void play();

    void stop();

    void reset();

    void setPlayMode();

    void setSong();

    void getSongs();
}
