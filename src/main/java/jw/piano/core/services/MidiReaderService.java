package jw.piano.core.services;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.midi.reader.MidiData;
import jw.piano.api.data.midi.reader.MidiRawData;
import jw.piano.api.data.midi.reader.MidiCraftEvent;
import org.bukkit.ChatColor;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;

import static javax.sound.midi.ShortMessage.*;

@Injection
public class MidiReaderService {

    public MidiData readMidiData(String path) throws InvalidMidiDataException, IOException {
        var midiData = readFile(path);
        var midiCraftData = createMidiData(midiData);
        return midiCraftData;
    }

    private MidiData createMidiData(MidiRawData midiData) {
        var craftMidiData = new MidiData();
        var tempo = midiData.getResoultion();
        float mspq = 165;
        for (var timeLine : midiData.getTimeLine().entrySet()) {
            var tick = timeLine.getKey();
            var events = timeLine.getValue();
            for (var event : events) {
                if (event.eventType() == 5)
                {
                  //  tempo = event.noteId();
                   // mspq = tempo;
                    tempo = 240;
                    mspq = 240;
               //     FluentLogger.LOGGER.success("TICK",tick,"TEMPO CHANGED",tempo);
                   continue;
                }
                var time = tick * (60000f / (tempo * mspq));
                var second = (time / 1000);
                var minecraftTick = second * 20;
                int roundedMinecraftTick = (int) Math.floor(minecraftTick);
                var msg = FluentApi.messages()
                        .chat().text("Key "+event.noteId(),ChatColor.AQUA).space();
                if(event.velocity() > 0)
                {
                    msg .text("ON ",ChatColor.GREEN).space();
                }
                else
                {
                    msg .text("OFF ",ChatColor.RED).space();
                }
                msg.text(event.velocity(),ChatColor.WHITE);

             //   FluentLogger.LOGGER.success( "TICK:",tick, "MINCEAFR TICK:",roundedMinecraftTick,"SECONDS:",second,msg.toString());
                craftMidiData.addEvent(roundedMinecraftTick, event);
            }
        }
        return craftMidiData;
    }

    private MidiRawData readFile(String path) throws InvalidMidiDataException, IOException {


        var sequence = MidiSystem.getSequence(new File(path));
        System.out.println("Tick length: " + sequence.getTickLength());
        System.out.println("Tempo " + sequence.getResolution());
        System.out.println("Ticks per measure: " + (4 * sequence.getResolution()));

        var midiData = new MidiRawData();
        midiData.setFileName(path);
        midiData.setTicks(sequence.getTickLength());
        midiData.setResoultion(sequence.getResolution());
        midiData.setTickPerBar(4 * sequence.getResolution());
        for (var track : sequence.getTracks()) {
            for (int i = 0; i < track.size(); i++) {
                var event = track.get(i);
                var message = event.getMessage();
                var tick = event.getTick();

                if (message instanceof MetaMessage metaMessage) {

                    byte[] data = metaMessage.getData();
                    if (metaMessage.getType() != 81 || data.length != 3) {
                        continue;
                    }
                    int midiTempo = ((data[0] & 0xff) << 16) | ((data[1] & 0xff) << 8) | (data[2] & 0xff);
                    int tempo = Math.round(60000000f  / midiTempo);
                    midiData.addEvent(tick, new MidiCraftEvent(tempo, midiTempo, 0, 5));
                }

                if (message instanceof ShortMessage shortmessage) {
                    var channel = shortmessage.getChannel();


                    var eventType = switch (shortmessage.getCommand()) {
                        case NOTE_ON, NOTE_OFF -> 0;
                        case STOP, START -> 2;
                        default -> -1;
                    };
                    int key = shortmessage.getData1();
                    int velocity = shortmessage.getData2();
                    if (shortmessage.getCommand() == NOTE_OFF) {
                        velocity = 0;
                    }
                    var craftMidiEvent = new MidiCraftEvent(key, channel, velocity, eventType);
                    midiData.addEvent(tick, craftMidiEvent);
                    midiData.setTicks(tick);
                }
            }
        }
        return midiData;
    }
}
