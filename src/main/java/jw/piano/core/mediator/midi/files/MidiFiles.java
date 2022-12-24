package jw.piano.core.mediator.midi.files;

import jw.piano.api.data.midi.reader.MidiFile;

import java.util.List;

public class MidiFiles
{
    public record Request(){}

    public record Response(List<MidiFile> files){}

}
