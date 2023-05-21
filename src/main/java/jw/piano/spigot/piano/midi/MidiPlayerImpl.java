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

package jw.piano.spigot.piano.midi;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.enums.MidiPlayingType;
import jw.piano.api.data.models.midi.PianoMidiFile;
import jw.piano.api.observers.MidiPlayerSettingsObserver;
import jw.piano.api.piano.MidiPlayer;
import jw.piano.api.piano.Piano;
import jw.piano.core.services.MidiLoaderService;
import jw.piano.core.workers.MidiPlayerWorker;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class MidiPlayerImpl implements MidiPlayer {

    private final MidiLoaderService loaderService;

    private final Piano piano;

    private final FluentTaskManager taskManager;

    @Getter
    private final MidiPlayerSettingsObserver observer;

    private PianoMidiFile current;

    private MidiPlayerWorker worker;

    public MidiPlayerImpl(Piano piano, FluentTaskManager taskManager, MidiLoaderService midiLoaderService) {
        this.loaderService = midiLoaderService;
        this.observer = piano.getPianoObserver().getMidiPlayerSettings();
        this.piano = piano;
        this.taskManager = taskManager;
    }


    public void enable() {
        worker = new MidiPlayerWorker(piano, taskManager);
        observer.getSpeed().subscribe(worker::setSpeed);
        observer.getIsPlaying().subscribe(aBoolean ->
        {
            if (aBoolean) {
                if (current == null) {
                    return;
                }
                try {
                    var data = loaderService.load(current.getPath());
                    worker.setSpeed(observer.getSpeed().get());
                    worker.start(data);
                } catch (Exception e) {
                    FluentLogger.LOGGER.warning("Unable to load midi for path", current.getPath(), e.getMessage());
                }

            } else {
                worker.stop();
                piano.getKeyboard().reset();
            }
        });
        worker.setOnStopPlaying(unused ->
        {
            var mode = observer.getMidiPlayerSettings().getPlayingType();
            switch (mode) {
                case IN_ORDER -> next();
                case LOOP -> play();
                case RANDOM -> {
                    var random = new Random();
                    var bound = observer.getMidiPlayerSettings().getMidiFiles().size() - 1;
                    var index =0;
                    if(bound > 0)
                    {
                       index = random.nextInt(0, bound);
                    }
                    setCurrentSong(observer.getMidiPlayerSettings().getMidiFiles().get(index));
                }
            }

        });


        if (observer.getPlayingType().get() != MidiPlayingType.NONE) {
            if (!observer.getMidiPlayerSettings().hasMidiFiles()) {
                return;
            }
            setCurrentSong(observer.getMidiPlayerSettings().getMidiFiles().get(0));
            play();
        }
    }

    @Override
    public void play() {
        observer.getIsPlaying().set(true);
    }


    @Override
    public void stop() {
        observer.getIsPlaying().set(false);
    }

    @Override
    public void setPlayMode(MidiPlayingType type) {
        observer.getPlayingType().set(type);
    }

    @Override
    public void next() {
        var songs = observer.getMidiPlayerSettings().getMidiFiles();
        if (songs.isEmpty()) {
            return;
        }
        var sorted = songs.stream()
                .sorted(Comparator.comparing(PianoMidiFile::getIndex)).toList();

        if (current == null) {
            current = songs.get(0);
        }
        for (var i = 0; i < sorted.size(); i++) {
            if (sorted.get(i) != current) {
                continue;
            }
            var nextIndex = i + 1;
            if (nextIndex > sorted.size() - 1) {
                nextIndex = 0;
            }
            setCurrentSong(sorted.get(nextIndex));
            break;
        }
    }

    @Override
    public void previous() {
        var songs = observer.getMidiPlayerSettings().getMidiFiles();
        if (songs.isEmpty()) {
            return;
        }
        var sorted = songs.stream()
                .sorted(Comparator.comparing(PianoMidiFile::getIndex)).toList();

        if (current == null) {
            current = songs.get(0);
        }
        for (var i = 0; i < sorted.size(); i++) {
            if (sorted.get(i) != current) {
                continue;
            }
            var nextIndex = i - 1;
            if (nextIndex < 0) {
                nextIndex = sorted.size() - 1;
            }
            setCurrentSong(sorted.get(nextIndex));
            break;
        }
    }


    @Override
    public void setCurrentSong(PianoMidiFile midiFile) {

        if (observer.getIsPlaying().get()) {
            stop();
            this.current = midiFile;
            play();
            return;
        }
        this.current = midiFile;
    }

    @Override
    public PianoMidiFile getCurrentSong() {
        return current;
    }

    @Override
    public void addSong(PianoMidiFile midiFile) {
        var wasPlaying = observer.getIsPlaying().get();
        stop();
        var songs = observer.getMidiPlayerSettings().getMidiFiles();


        var isSlotOccupied = songs.stream().filter(c -> c.getIndex() == midiFile.getIndex()).findFirst();
        if (isSlotOccupied.isPresent()) {
            removeSong(isSlotOccupied.get());
        }
        observer.getMidiPlayerSettings().addMidiFile(midiFile);
        if (songs.isEmpty() || songs.size() == 1) {
            setCurrentSong(midiFile);
        }
        if(wasPlaying)
        {
            play();
        }
    }

    @Override
    public void removeSong(PianoMidiFile midiFile) {
        stop();
        observer.getMidiPlayerSettings().removeMidiFile(midiFile);
        if (current == midiFile) {
            current = null;
        }
    }


    @Override
    public List<PianoMidiFile> getSongs() {
        return observer.getMidiPlayerSettings().getMidiFiles();
    }


}
