package jw.piano.data.enums;


public enum PianoType
{
    NONE(0), UPRIGHT_PIANO(8), GRAND_PIANO(9);
    private final int id;
    PianoType(int id) {
      this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

}
