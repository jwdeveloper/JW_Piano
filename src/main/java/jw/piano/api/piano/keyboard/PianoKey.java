package jw.piano.api.piano.keyboard;

import jw.piano.api.piano.common.Pressable;

public interface PianoKey extends Pressable, Comparable
{
     boolean isWhite();

     void refresh();
}
