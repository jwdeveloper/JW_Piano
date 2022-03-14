package jw.piano.data;

import jw.data.repositories.Saveable;
import jw.dependency_injection.Injectable;

@Injectable
public class Settings implements Saveable
{
    public float maxDistanceFromPiano = 3;
    public String texturepackURL = "https://drive.google.com/file/d/1HClLdDwtPe7EaZjIL8vtGhoHvvipWjDj/view?usp=sharing";
    public float maxDistanceFromKeys = 2;
    public boolean midiPlayer = false;

    @Override
    public boolean load() {
        return false;
    }

    @Override
    public boolean save() {
        return false;
    }
}
