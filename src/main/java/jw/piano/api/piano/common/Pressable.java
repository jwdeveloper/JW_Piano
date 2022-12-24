package jw.piano.api.piano.common;

public interface Pressable extends Interactable
{
    void press(int velocity);
    void press();

    void release();
}
