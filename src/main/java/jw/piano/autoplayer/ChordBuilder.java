package jw.piano.autoplayer;

import java.util.ArrayList;
import java.util.List;

public class ChordBuilder
{
    String name = "chord";
    List<Sound> notes = new ArrayList<>();

    public ChordBuilder setName(String name)
    {
        this.name = name;
        return this;
    }
    public ChordBuilder setNote(Sound note)
    {
        notes.add(note);
        return this;
    }

}
