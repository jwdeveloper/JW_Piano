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
    private MidiPlayingType midiPlayingType = MidiPlayingType.IN_ORDER;

    @Getter
    private List<PianoMidiFile> midiFiles = new ArrayList<>();

    public void addMidiFile(PianoMidiFile midiFile)
    {
        midiFiles.add(midiFile);
    }

    public void moveLeft(PianoMidiFile pianoMidiFile)
    {
        midiFiles = getMidiFilesSortedByIndex();
        for(var i =0;i<midiFiles.size();i++)
        {
            var temp = midiFiles.get(i);
            var oldIndex = temp.getIndex();
            if(!temp.equals(pianoMidiFile))
            {
                continue;
            }
            temp.setIndex(oldIndex-1);
            if(i-1 >= 0)
            {
                var before = midiFiles.get(i-1);
                before.setIndex(oldIndex);
            }

        }
    }

    public void moveRight(PianoMidiFile pianoMidiFile)
    {
        midiFiles = getMidiFilesSortedByIndex();
        for(var i =0;i<midiFiles.size();i++)
        {
             var temp = midiFiles.get(i);
             var oldIndex = temp.getIndex();
             if(!temp.equals(pianoMidiFile))
             {
                continue;
             }
            temp.setIndex(oldIndex+1);
             if(i +1 < midiFiles.size())
             {
                 FluentLogger.LOGGER.info("RIGHT");
                 var next = midiFiles.get(i+1);
                 next.setIndex(oldIndex);
             }

        }
    }

    public List<PianoMidiFile> getMidiFilesSortedByIndex()
    {
       return midiFiles.stream().sorted(Comparator.comparingInt(PianoMidiFile::getIndex)).toList();
    }
}
