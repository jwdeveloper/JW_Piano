package jw.piano.websocket.models;

import jw.piano.gameobjects.models.PianoModel;

public record PianoAction(PianoModel model,
                          int vel,
                          int note,
                          int type) {
}
