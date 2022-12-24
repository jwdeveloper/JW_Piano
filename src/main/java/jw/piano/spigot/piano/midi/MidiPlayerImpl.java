package jw.piano.spigot.piano.midi;

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
    private final MidiPlayerWorker worker;
    @Getter
    private final MidiPlayerSettingsObserver observer;

    private PianoMidiFile current;

    public MidiPlayerImpl(Piano piano, MidiLoaderService midiLoaderService) {
        this.loaderService = midiLoaderService;
        this.observer = piano.getPianoObserver().getMidiPlayerSettingsObserver();
        worker = new MidiPlayerWorker(piano);
        observer.getSpeedObserver().onChange(integer ->
        {
            var value = integer / 2;
            worker.setSpeed(value);
        });
        observer.getIsPlayingObserver().onChange(aBoolean ->
        {
            if (aBoolean) {
                if (current == null) {
                    return;
                }
                try
                {
                    var data = midiLoaderService.load(current.getPath());
                    worker.setSpeed(observer.getSpeedObserver().get());
                    worker.start(data);
                }
                catch (Exception e)
                {
                    FluentLogger.LOGGER.warning("Unable to load midi for path",current.getPath(),e.getMessage());
                }

            } else {
                worker.stop();
                piano.getKeyboard().reset();
            }
        });
        worker.setOnStopPlaying(unused ->
        {
            var mode = observer.getMidiPlayerSetting().getPlayingType();
            switch (mode) {
                case IN_ORDER -> next();
                case LOOP -> play();
                case RANDOM -> {
                    var random = new Random();
                    var index = random.nextInt(0, observer.getMidiPlayerSetting().getMidiFiles().size() - 1);
                    setCurrentSong(observer.getMidiPlayerSetting().getMidiFiles().get(index));
                }
            }

        });
    }

    @Override
    public void play() {
        observer.getIsPlayingObserver().set(true);

    }

    @Override
    public void stop() {
        observer.getIsPlayingObserver().set(false);
    }

    @Override
    public void setPlayMode(MidiPlayingType type) {
        observer.getPlayingTypeObserver().set(type);
    }

    @Override
    public void next() {
        var songs = observer.getMidiPlayerSetting().getMidiFiles();
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
        var songs = observer.getMidiPlayerSetting().getMidiFiles();
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

        if (observer.getIsPlayingObserver().get()) {
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
        stop();
        var songs = observer.getMidiPlayerSetting().getMidiFiles();
        if (songs.isEmpty()) {
            setCurrentSong(midiFile);
        }

        var isSlotOccupied = songs.stream().filter(c -> c.getIndex() == midiFile.getIndex()).findFirst();
        if (isSlotOccupied.isPresent()) {
            removeSong(isSlotOccupied.get());
        }
        observer.getMidiPlayerSetting().addMidiFile(midiFile);
    }

    @Override
    public void removeSong(PianoMidiFile midiFile) {
        stop();
        observer.getMidiPlayerSetting().removeMidiFile(midiFile);
        if (current == midiFile) {
            current = null;
        }
    }


    @Override
    public List<PianoMidiFile> getSongs() {
        return observer.getMidiPlayerSetting().getMidiFiles();
    }


}
