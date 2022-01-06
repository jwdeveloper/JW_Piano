package jw.piano.management;

import jw.piano.autoplayer.Chord;
import jw.piano.model.PianoKey;
import jw.piano.model.PianoModel;
import jw.task.TaskTimer;
import org.bukkit.entity.Zombie;

import java.util.List;

public class PianoMelodyPlayer
{

    int speed = 7;
    PianoModel pianoModel;
    Chord chord;
    int offset =27;
    int octave = 2;
    TaskTimer taskTimer;

    public PianoMelodyPlayer(Chord chord, PianoModel pianoModel)
    {
        this.pianoModel =pianoModel;
        this.chord = chord;
    }

    public void setMelody(Chord chord)
    {
        this.chord = chord;
        if(taskTimer!=null)
            taskTimer.reset();
    }

    public void Play()
    {
        taskTimer =  new TaskTimer(speed,(time, taskTimer) ->
        {
            int index = chord.getNote(time%chord.notesSize()).getIndex();
            PianoKey pianoKey = pianoModel.getPianoKeys()[index];
            new TaskTimer(speed, (time1, taskTimer1) ->
            {
                pianoKey.setPedalPressed(pianoModel.getPianoPedals()[2].isPressed());
                pianoKey.press(pianoKey.getIndex(), 50, 1);
            }).stopAfter(1).onStop(taskTimer1 ->
            {
                pianoKey.release(pianoKey.getIndex(), 0, 1);
            }).run();
        });
        taskTimer.run();
    }


}
