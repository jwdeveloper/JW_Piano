package io.github.jwdeveloper.spigot.piano.api.data.midi;

import io.github.jwdeveloper.spigot.piano.api.enums.MidiPlayerType;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class MidiPlayerData
{
    private Boolean isPlayingInLoop = false;
    private Boolean isPlaying = false;
    private Integer speed = 50;
    private MidiPlayerType playingType = MidiPlayerType.IN_ORDER;

    @Getter
    private List<MidiFileData> midiFiles = new ArrayList<>();

    public void addMidiFile(MidiFileData midiFile)
    {
        if(midiFiles.contains(midiFile))
        {
            return;
        }

        midiFiles.add(midiFile);
    }

    public void removeMidiFile(MidiFileData midiFile)
    {
        midiFiles.remove(midiFile);
    }


    public boolean hasMidiFiles()
    {
        return midiFiles.size() !=0;
    }
}
