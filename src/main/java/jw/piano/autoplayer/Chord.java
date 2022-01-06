package jw.piano.autoplayer;

public class Chord
{
    private final Note[] notes;
    private final String name;

    public Chord(String name, Note...notes)
    {
        this.name = name;
        this.notes = notes;
    }

    public Note getNote(int index)
    {
        if(index < notes.length)
            return notes[index];
        return null;
    }

    public int notesSize()
    {
        return notes.length;
    }

    public Note[] getNotes()
    {
        return notes.clone();
    }
}
