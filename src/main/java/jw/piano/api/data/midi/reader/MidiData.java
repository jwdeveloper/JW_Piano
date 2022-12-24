package jw.piano.api.data.midi.reader;

import java.util.*;

public class MidiData
{
    private final UUID id = UUID.randomUUID();
    private final Map<Integer, Set<MidiCraftEvent>> timeLine = new LinkedHashMap<>();
    private String fileName;

    public void addEvent(Integer tick, MidiCraftEvent event) {
        if (!timeLine.containsKey(tick)) {
            timeLine.put(tick, new HashSet<>());
        }
        var events = timeLine.get(tick);
        events.add(event);
    }

    public void addEvents(Integer tick, Set<MidiCraftEvent> events) {
       for(var event: events)
       {
           addEvent(tick,event);
       }
    }

    public Set<MidiCraftEvent> getEvents(Integer tick)
    {
        return timeLine.get(tick);
    }
}
