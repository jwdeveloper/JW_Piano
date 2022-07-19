package jw.piano.websocket.models;

import jw.piano.game_objects.models.PianoModel;

import java.util.UUID;

public record PianoAction(PianoModel model,
                          int vel,
                          int note,
                          int type) {
}
