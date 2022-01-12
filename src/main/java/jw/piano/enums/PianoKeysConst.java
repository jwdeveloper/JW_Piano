package jw.piano.enums;

public enum PianoKeysConst
{
     WHITE_KEY_SELECTED(0),
     BLACK_KEY_SELECTED(1),
     WHITE_KEY(2),
     BLACK_KEY(4),
     WHITE_KEY_PRESSED(3),
     BLACK_KEY_PRESSED(5);

    private final int id;
    PianoKeysConst(int id) {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
}
