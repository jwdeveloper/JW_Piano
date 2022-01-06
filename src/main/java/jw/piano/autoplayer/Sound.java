package jw.piano.autoplayer;

public enum Sound
{
 NONE(-1), C(0),C_UP(1),D(2),D_UP(3),E(4),F(5),F_UP(6),G(7),G_UP(8),A(9),A_UP(10),B(11);

    private final int id;
    Sound(int id) {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public static Sound getById(int id)
    {
        Sound[] notes = Sound.values();
        for(int i=0;i<notes.length;i++)
        {
            if(id == notes[i].getId())
            {
                return notes[i];
            }
        }
        return Sound.NONE;
    }
}
