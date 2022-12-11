package jw.piano.mediator.midi.files;

import jw.piano.data.midi.MidiFile;

import java.util.List;

public class MidiFiles
{
    public record Request(){}

    public record Response(List<MidiFile> files){}

}
