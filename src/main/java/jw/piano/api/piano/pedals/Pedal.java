package jw.piano.api.piano.pedals;

import jw.piano.api.piano.common.Pressable;

public interface Pedal extends Pressable
{
    boolean isPressed();
}
