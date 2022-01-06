package jw.piano.autoplayer;

public class Note
{
    private Sound sound;
    private int octave;
    private int lenght;
    private int index;
    public Note(Sound sound,int octave)
    {
       this.sound = sound;
       this.octave = octave;

       this.index = octave*12 +sound.getId();
    }
    public Note(Sound sound,int octave,int lenght)
    {
        this(sound,octave);
        this.lenght =lenght;
    }

    public int getOctave()
    {
        return octave;
    }
    public int getLenght()
    {
        return lenght;
    }
    public Sound getSound()
    {
        return sound;
    }

    public int getIndex()
    {
        return index;
    }



}
