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
package jw.piano.core.midi;


import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.core.midi.jw.PianoTrackEntry;
import jw.piano.core.midi.utils.InOutParam;
import jw.piano.core.midi.utils.Pair;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static javax.sound.midi.ShortMessage.*;


public class MidiParser {


    public static MidiFile loadFile(File midiFile) {
        try {
            return parseFile(midiFile);
        } catch (InvalidMidiDataException ex) {
            return new MidiFile("Invalid or corrupted MIDI file");
        } catch (IOException ex) {
            FluentLogger.LOGGER.error("Unable to read midi " + midiFile.getName(), ex);
            return new MidiFile("Unable to read the MIDI file");
        }
    }


    private static MidiFile parseFile(File midiFile) throws IOException, InvalidMidiDataException {
        if (midiFile == null || !midiFile.canRead()) {
            return null;
        }

        Sequence sequence = MidiSystem.getSequence(midiFile);
        float divType = sequence.getDivisionType();

        if (divType != Sequence.PPQ) {
            return new MidiFile("Unsupported DivisionType " + DivisionFormater.getDivisionName(divType));
        }

        int resolution = sequence.getResolution();
        InOutParam<Double> tempo = InOutParam.Ref(0.0);

        List<TrackEntry> result = new ArrayList<TrackEntry>();

        Map<Integer, Instrument> instruments = new HashMap<Integer, Instrument>();
        Map<Integer, Integer> masterVolume = new HashMap<Integer, Integer>();


        for(var i =0;i<sequence.getTracks().length;i++)
        {
            var track = sequence.getTracks()[i];
            result.addAll(parseTrack(track,
                    tempo,
                    resolution,
                    instruments,
                    masterVolume,
                    i+1));
        }

        var agregated = aggregate(result);
        List<NoteFrame> frames = convertToNoteFrames(agregated);

        return new MidiFile(frames.toArray(new NoteFrame[0]));
    }

    private static List<PianoTrackEntry> parseTrack(Track track,
                                                    InOutParam<Double> tempo,
                                                    int resolution,
                                                    Map<Integer, Instrument> instruments,
                                                    Map<Integer, Integer> masterVolume,
                                                    int trackId) {
        double lTempo = tempo.getValue();
        List<PianoTrackEntry> result = new ArrayList<PianoTrackEntry>();
        for (int i = 0; i < track.size(); i++) {
            MidiEvent event = track.get(i);
            MidiMessage message = event.getMessage();

            long tick = event.getTick();
            long milis;

            if (lTempo > 0 && resolution > 0) {
                milis = (long) (tick * 60000 / resolution / lTempo);
            } else {
                milis = -1;
            }

            if (message instanceof MetaMessage) {
                MetaMessage mm = (MetaMessage) message;
                byte[] data = mm.getData();

                if ((mm.getType() & 0xff) == 0x51
                        && data != null && data.length > 2) {
                    int nTempo = ((data[0] & 0xFF) << 16)
                            | ((data[1] & 0xFF) << 8)
                            | (data[2] & 0xFF);
                    if (nTempo <= 0) {
                        lTempo = 0;
                    } else {
                        lTempo = 60000000.0 / nTempo;
                    }
                }
                continue;
            }
            if (message instanceof ShortMessage shortmessage) {
                var channel = shortmessage.getChannel();
                switch (shortmessage.getCommand()) {
                    case PROGRAM_CHANGE -> {
                        continue;
                    }
                    case CONTROL_CHANGE -> {
                        if (shortmessage.getData1() == 0x7) {
                            setVolume(masterVolume, channel, shortmessage.getData2());
                            continue;
                        }

                    }

                }


                var eventType = switch (shortmessage.getCommand() & 0xff) {
                    case NOTE_ON, NOTE_OFF -> 0;
                    case STOP, START -> 2;
                    default -> -1;
                };

                if(shortmessage.getCommand() == CONTROL_CHANGE)
                {
                    //pedal
                    eventType = 1;
                }


                int key = shortmessage.getData1();
                int velocity = shortmessage.getData2();
                if (shortmessage.getCommand() == NOTE_OFF) {
                    velocity = 0;
                }
                var volume = getVolume(masterVolume, channel, velocity);
                int octave = (key / 12) - 1;
                int note = key % 12;

                var entry = new PianoTrackEntry(milis,
                        octave,
                        note,
                        volume
                );

                entry.setKeyIndex(key);
                entry.setEventId(eventType);
                entry.setTrack(trackId);

                if(eventType == 1)
                {
                    entry.updateNoteEntry(shortmessage.getData2() > 0?1:0);
                }
                else
                {
                    entry.updateNoteEntry();
                }


                result.add(entry);
            }
        }
        tempo.setValue(lTempo);
        return result;
    }


    private static List<NoteFrame> convertToNoteFrames(List<Pair<Long, Set<TrackEntry>>> notes) {
        List<NoteFrame> result = new ArrayList<NoteFrame>();

        long last = notes.get(0).getX1();
        for (Pair<Long, Set<TrackEntry>> entry : notes) {
            long milis = entry.getX1();

            result.add(new NoteFrame(milis - last, entry.getX2()));

            last = milis;
        }

        return result;
    }


    private static List<Pair<Long, Set<TrackEntry>>> aggregate(List<TrackEntry> notes) {
        Map<Long, Set<TrackEntry>> tmp = new HashMap<Long, Set<TrackEntry>>();

        for (TrackEntry entry : notes) {
            final long millis = entry.getMillis();

            final Set<TrackEntry> set = tmp.computeIfAbsent(millis, k -> new HashSet<TrackEntry>());

            set.add(entry);
        }

        List<Long> keys = new ArrayList<Long>(tmp.keySet());
        Collections.sort(keys);

        List<Pair<Long, Set<TrackEntry>>> result = new ArrayList<Pair<Long, Set<TrackEntry>>>();
        for (Long time : keys) {
            result.add(new Pair<Long, Set<TrackEntry>>(time, tmp.get(time)));
        }
        return result;
    }

    private static void setVolume(Map<Integer, Integer> masterVolume, int channel, int volume) {
        masterVolume.remove(channel);
        masterVolume.put(channel, volume);
    }

    private static float getVolume(Map<Integer, Integer> masterVolume, int channel, int velocity) {
        int volume = masterVolume.getOrDefault(channel, 127);
        return volume / 127.0f * velocity / 127.0f;
    }
}
