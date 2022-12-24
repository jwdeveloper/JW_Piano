package jw.piano.core.workers;

import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.midiplayer.midiparser.NoteFrame;
import jw.piano.api.midiplayer.midiparser.NoteTrack;
import jw.piano.api.midiplayer.midiparser.jw.PianoNodeEntry;
import jw.piano.api.piano.Piano;

import java.util.LinkedList;
import java.util.List;

public class MidiPlayerWorker {
    private NoteTrack midiData;
    private Piano piano;
    private NoteFrame[] frames;

    private int frameIndex = 0;

    private long delay = 0;

    private long delayAdd = 0;
    private boolean isStarted = false;

    private static final long DELAY = 100;
    private SimpleTaskTimer task;

    private NoteFrame currentFrame;

    public MidiPlayerWorker(NoteTrack midiData, Piano piano) {
        this.midiData = midiData;
        this.piano = piano;
        frames = midiData.getNotes();


    }


    public void start() {
        task =  FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {
            if (frameIndex > frames.length-1) {
                task.cancel();
                return;
            }
            currentFrame = frames[frameIndex];
            if (!isStarted) {
                delay = currentFrame.getWait();
                isStarted = true;
            }

            if (delay > 0) {
                delay = delay - DELAY;
                return;
            }


            delayAdd = Math.abs(delay);

            isStarted = false;
            frameIndex++;
            List<NoteFrame> framesToPlayer = new LinkedList<>();
            framesToPlayer.add(currentFrame);
            long offset = DELAY;
            while (offset > 0) {
                if (frames[frameIndex].getWait() > offset) {
                    break;
                }
                var f = frames[frameIndex];
                offset = offset - f.getWait();
                framesToPlayer.add(f);
                frameIndex++;
            }

            for (var noteFrame : framesToPlayer) {
                for (var note : noteFrame.getNotes()) {
                    if (note instanceof PianoNodeEntry entry) {
                        var sounds = (entry.getVelocity() * 100);
                        switch (entry.getEvent())
                        {
                            case 0 -> piano.triggerNote((int) (entry.getVelocity() * 100), entry.getIndex(), (int) sounds);
                            case 1 -> piano.triggerPedal(1, entry.getIndex(),100);
                            case 2 -> piano.getKeyboard().reset();
                        }
                    }
                }
            }
        }).run();
    }

    public void stop()
    {
        task.stop();
    }
}