package jw.piano.websocket.models;

import jw.piano.spigot.gameobjects.models.PianoGameObject;

public record PianoAction(PianoGameObject model, int vel, int note, int type) {
}
