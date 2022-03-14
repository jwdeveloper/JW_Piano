package jw.piano.utility;

import java.util.HashMap;

public enum PianoTypes
{
    None(0),Upright_Piano(8),Grand_Piano(9),Electric_Piano(10),Organs(11);
    private final int id;
    PianoTypes(int id) {
      this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

}
