package io.github.jwdeveoper.spigot.piano.core.mediator.piano.action;

import io.github.jwdeveloper.spigot.piano.api.piano.Piano;

import java.util.UUID;

public class PianoAction {
    public record PianoEvent(UUID pianoId, int velocity, int note, int eventType, int track){}

    public record WorkerInfo(Piano model, int velocity, int note, int eventType, int track){}
}
