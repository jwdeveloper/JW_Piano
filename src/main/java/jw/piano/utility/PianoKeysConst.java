package jw.piano.utility;

public enum PianoKeysConst
{

     White_Key_Selected(0),
     Black_Key_Selected(1),
     White_Key(2),
     Black_Key(4),
     White_Key_Pressed(3),
     Black_Key_Pressed(5);

    private final int id;
    PianoKeysConst(int id) {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }
}
