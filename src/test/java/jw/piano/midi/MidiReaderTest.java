/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.piano.midi;

import jw.piano.api.data.midi.reader.ChangeTempoEvent;
import jw.piano.api.data.midi.reader.MidiCraftEvent;
import jw.piano.api.data.midi.reader.MidiRawData;
import jw.piano.core.services.MidiLoaderService;
import org.junit.Test;

public class MidiReaderTest {

    @Test
    public void createMinecraftSections()
    {
        var sut = new MidiLoaderService();
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