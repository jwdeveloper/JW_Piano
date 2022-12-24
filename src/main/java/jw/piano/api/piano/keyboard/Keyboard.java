package jw.piano.api.piano.keyboard;

import jw.piano.api.piano.common.Interactable;
import jw.piano.api.piano.common.Resetable;
import jw.piano.api.piano.common.Teleportable;

public interface Keyboard extends Teleportable, Interactable, Resetable
{
     PianoKey[] getPianoKeys();
     void triggerNote(int pressed, int midiIndex, int velocity);
}
