package jw.piano.api.data.midi.reader;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MidiFile
{
    private String path;
    private String name;
}
