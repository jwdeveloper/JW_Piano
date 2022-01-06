package jw.piano.autoplayer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Melody
{
    private HashMap<Integer, List<Sound>> timeLine = new HashMap<>();
    private String name;
    private int tempo;

    public Melody(String name)
    {
        this.name = name;
    }

    public void setTempo(int tempo)
    {
        this.tempo = tempo;
    }
    public void addToTimeLine(int tick, Sound... notes)
    {
        if(timeLine.containsKey(tick))
        {
            timeLine.get(tick).addAll(Arrays.asList(notes));
        }
        else
        {
            timeLine.put(tick,Arrays.asList(notes));
        }
    }

    public HashMap<Integer, List<Sound>> getSounds()
    {
        return timeLine;
    }

}
