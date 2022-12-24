package jw.piano.midi;

import jw.piano.api.data.midi.reader.ChangeTempoEvent;
import jw.piano.api.data.midi.reader.MidiCraftEvent;
import jw.piano.api.data.midi.reader.MidiRawData;
import jw.piano.core.services.MidiReaderService;
import org.junit.Test;

public class MidiReaderTest {

    @Test
    public void createMinecraftSections()
    {
        var sut = new MidiReaderService();
        var midiData = new MidiRawData();

        midiData.addEvent(5,new MidiCraftEvent(33,1,1,1));
        midiData.addEvent(10,new MidiCraftEvent(34,1,1,1));

        midiData.addEvent(20,new MidiCraftEvent(30,1,1,1));
        midiData.addEvent(22,new MidiCraftEvent(31,1,1,1));

        midiData.addEvent(27,new MidiCraftEvent(31,1,1,1));


        midiData.addChangeTempo(new ChangeTempoEvent(0,120,4));
        midiData.addChangeTempo(new ChangeTempoEvent(10,100,4));
        midiData.addChangeTempo(new ChangeTempoEvent(20,50,4));
        midiData.addChangeTempo(new ChangeTempoEvent(25,200,4));
        midiData.setTicks(30L);

       // sut.createMinecraftSections(midiData);

    }
}