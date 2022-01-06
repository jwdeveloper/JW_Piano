package jw.piano.utility;

public enum PedalType
{
    SOFT(64),SOSTENUTO(65),SUSTAIN(67);
    private final int id;
    PedalType(int id) {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
}
