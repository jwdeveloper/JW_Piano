package jw.piano.core.workers;

import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.midiplayer.NoteFrame;
import jw.piano.api.midiplayer.NoteTrack;
import jw.piano.api.midiplayer.jw.PianoNodeEntry;
import jw.piano.api.piano.Piano;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class MidiPlayerWorker {
    private Piano piano;
    private NoteFrame[] frames;
    private int frameIndex = 0;

    private long delay = 0;
    private boolean isStarted = false;

    @Setter
    private long speed = 100;
    private SimpleTaskTimer task;
    private NoteFrame currentFrame;

    @Setter
    private Consumer<Void> onStopPlaying = (e) -> {
    };

    public MidiPlayerWorker(Piano piano) {
        this.piano = piano;
    }

    public void start(NoteTrack track) {
        stop();
        frames = track.getNotes();
        isStarted = true;
        frameIndex = 0;
        task = FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {
            if (frameIndex > frames.length - 1) {
                task.cancel();
                return;
            }
            currentFrame = frames[frameIndex];
            if (!isStarted) {
                delay = currentFrame.getWait();
                isStarted = true;
            }
            if (delay > 0) {
                delay = delay - speed;
                return;
            }
            isStarted = false;
            frameIndex++;
            List<NoteFrame> framesToPlayer = new LinkedList<>();
            framesToPlayer.add(currentFrame);
            long offset = speed;
            while (offset > 0) {
                if (frameIndex > frames.length - 1) {
                    break;
                }

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
                        switch (entry.getEvent()) {
                            case 0 ->
                                    piano.triggerNote((int) (entry.getVelocity() * 100), entry.getIndex(), (int) sounds);
                            case 1 -> piano.triggerPedal(1, entry.getIndex(), 100);
                            case 2 -> piano.getKeyboard().reset();
                        }
                    }
                }
            }
        }).onStop(simpleTaskTimer ->
        {
            piano.getKeyboard().reset();
            onStopPlaying.accept(null);
        }).run();
    }

    public void stop() {
        isStarted = false;
        if (task == null) {
            return;
        }
        task.stop();
    }
}
