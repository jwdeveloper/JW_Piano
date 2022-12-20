package jw.piano.workers;

import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.midiplayer.midiparser.NoteFrame;
import jw.midiplayer.midiparser.NoteTrack;
import jw.midiplayer.midiparser.jw.PianoNodeEntry;
import jw.piano.spigot.gameobjects.models.PianoGameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MidiPlayerWorker2 {
    private NoteTrack midiData;
    private PianoGameObject piano;

    private NoteFrame[] frames;

    public MidiPlayerWorker2(NoteTrack midiData, PianoGameObject piano) {
        this.midiData = midiData;
        this.piano = piano;
        frames = midiData.getNotes();
    }

    private int frameIndex = 0;

    private long delay = 0;

    private long delayAdd = 0;
    private boolean isStarted = false;


    private static final long DELATY = 100;
    public void start() {
        FluentLogger.LOGGER.info("STARTED PLAYING");
        FluentApi.tasks().taskTimer(1, (iteration, task) ->
        {

            if (frameIndex > frames.length-1) {
                task.cancel();
                return;
            }

            var frame = frames[frameIndex];
            if (!isStarted) {
                delay = frame.getWait();
                isStarted = true;
            }

            if (delay > 0) {
                delay = delay - DELATY;
                return;
            }


            delayAdd = Math.abs(delay);

            isStarted = false;
            frameIndex++;
            List<NoteFrame> framesToPlayer = new LinkedList<>();
            framesToPlayer.add(frame);
            long offset = DELATY;
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
                        switch (entry.getEvent()) {
                            case 0 ->
                                    piano.invokeNote((int) (entry.getVelocity() * 100), entry.getIndex(), (int) sounds);
                            case 1 -> piano.invokePedal(1, entry.getIndex());
                            case 2 -> piano.refreshKeys();
                        }
                    }
                }
            }


        }).run();
    }


    public void test() {

    }

    public void stop() {
        FluentLogger.LOGGER.info("STOPED PLAYING");
    }
}
