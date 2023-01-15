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

package jw.piano.api.data.midi.reader;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
public class MidiRawData {

    private final Map<Long, Set<MidiCraftEvent>> timeLine = new TreeMap<>();
    private String fileName;

    @Getter
    @Setter
    private int Resoultion;
    @Getter
    @Setter
    private int tickPerBar;
    private Long ticks;
    private final LinkedList<ChangeTempoEvent> changeTempoEvents = new LinkedList<>();


    public Map<Long, Set<MidiCraftEvent>> getTimeLine()
    {
            return timeLine;
    }

    public void addChangeTempo(ChangeTempoEvent event)
    {
        changeTempoEvents.add(event);
    }

    public void addEvent(long tick, MidiCraftEvent event) {
        if (!timeLine.containsKey(tick)) {
            timeLine.put(tick, new LinkedHashSet<>());
        }
        var events = timeLine.get(tick);
        events.add(event);
    }

    public Set<MidiCraftEvent> getEvents(Long tick)
    {
        return timeLine.get(tick);
    }
}
