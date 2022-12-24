package jw.piano.api.data.models.midi;

import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.enums.MidiPlayingType;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class MidiPlayerSettings
{
    private boolean isPlayingInLoop = false;
    private boolean isPlaying = false;
    private Integer speed = 100;
    private MidiPlayingType playingType = MidiPlayingType.IN_ORDER;

    @Getter
    private List<PianoMidiFile> midiFiles = new ArrayList<>();

    public void addMidiFile(PianoMidiFile midiFile)
    {
        if(midiFiles.contains(midiFile))
        {
            return;
        }

        midiFiles.add(midiFile);
    }

    public void removeMidiFile(PianoMidiFile midiFile)
    {
        midiFiles.remove(midiFile);
    }


}
