package jw.piano.api.piano.pedals;

import jw.piano.api.piano.common.Interactable;
import jw.piano.api.piano.common.Teleportable;

public interface PedalGroup extends Teleportable, Interactable {
    Pedal[] getPedals();

    void triggerPedal(int isPressed, int midiIndex, int velocity);

    boolean isSustainPressed();

     boolean triggerSustainPedal();

    void refresh();
}
