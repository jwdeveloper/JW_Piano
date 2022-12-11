package jw.piano.mediator.midi.reader;

import jw.piano.data.midi.MidiData;

public class MidiReader
{
    public record Request(String path){}

    public record Response(MidiData midiData, String fileName, boolean created){};
}
