package jw.piano.data.midi;

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
