/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveoper.spigot.piano.core.workers;

import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveoper.spigot.piano.core.midi_player.MidiFile;
import io.github.jwdeveoper.spigot.piano.core.midi_player.NoteFrame;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class MidiPlayerWorker {
    private final Piano piano;

    private final FluentTaskFactory taskManager;

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

    public MidiPlayerWorker(Piano piano, FluentTaskFactory taskManager) {
        this.piano = piano;
        this.taskManager = taskManager;
    }


    private List<NoteFrame> framesToPlay;

    public void start(MidiFile track) {
        stop();
        frames = track.getNotes();
        isStarted = true;
        frameIndex = 0;

        task = taskManager.taskTimer(00, (iteration, task) ->
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
            framesToPlay = new LinkedList<>();
            framesToPlay.add(currentFrame);
            long offset = speed;
            while (offset > 0) {
                if (frameIndex > frames.length - 1) {
                    break;
                }

                if (frames[frameIndex].getWait() > offset) {
                    break;
                }
                var frame = frames[frameIndex];
                offset = offset - frame.getWait();
                framesToPlay.add(frame);
                frameIndex++;
            }

            for (var noteFrame : framesToPlay) {
                for (var note : noteFrame.getNotes()) {
                    var volume = (note.getVelocity() * 100);
                    switch (note.getEvent()) {
                        case 0 ->
                                piano.triggerNote((int) (note.getVelocity() * 100), note.getIndex(), (int) volume, note.getTrack());
                        case 1 ->
                                piano.triggerPedal((int) note.getVelocity(), note.getIndex(), (int) note.getVelocity());
                        case 2 -> piano.getKeyboard().reset();
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
