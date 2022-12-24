package jw.piano.api.piano.common;

import jw.piano.api.data.events.PianoInteractEvent;

public interface Interactable
{
    boolean triggerPlayerClick(PianoInteractEvent event);
}
