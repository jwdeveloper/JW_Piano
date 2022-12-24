package jw.piano.core.mediator.midi.reader;

import jw.piano.api.data.midi.reader.MidiData;

public class MidiReader
{
    public record Request(String path){}

    public record Response(MidiData midiData, String fileName, boolean created){};
}
