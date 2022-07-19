package jw.piano.autoplayer;

import jw.piano.game_objects.models.PianoModel;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;

public class PianoMelodyPlayer {
    private  int speed = 7;
    private int offset = 27;
    private int octave = 2;
    private FluentTaskTimer taskTimer;
    private Chord chord;


    private final PianoModel pianoModel;

    public PianoMelodyPlayer(Chord chord, PianoModel pianoModel) {
        this.pianoModel = pianoModel;
        this.chord = chord;
    }

    public void setMelody(Chord chord) {
        this.chord = chord;
        if (taskTimer != null)
            taskTimer.reset();
    }

    public void Play() {
        taskTimer = new FluentTaskTimer(speed, (time, taskTimer) ->
        {
            var index = chord.getNote(time % chord.notesSize()).getIndex();
            var pianoKey = pianoModel.getPianoKeys()[index];
            new FluentTaskTimer(speed, (time1, taskTimer1) ->
            {
                pianoKey.setPedalPressed(pianoModel.getPianoPedals()[2].isPressed());
                pianoKey.press(pianoKey.getIndex(), 50);
            })
                    .stopAfterIterations(1)
                    .onStop(taskTimer1 ->
                    {
                        pianoKey.release(pianoKey.getIndex(), 0);
                    }).run();
        });
        taskTimer.run();
    }


}
