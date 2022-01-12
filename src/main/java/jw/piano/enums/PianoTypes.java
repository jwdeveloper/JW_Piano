package jw.piano.enums;


public enum PianoTypes
{
    NONE(0), UPRIGHT_PIANO(8), GRAND_PIANO(9), ELECTRIC_PIANO(10), ORGANS(11);
    private final int id;
    PianoTypes(int id) {
      this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

}
