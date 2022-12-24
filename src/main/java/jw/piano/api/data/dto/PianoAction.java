package jw.piano.api.data.dto;

import jw.piano.api.piano.Piano;

import java.util.UUID;

public class PianoAction
{
    public record PianoEvent(UUID pianoId, int velocity, int note, int eventType){}

    public record WorkerInfo(Piano model, int velocity, int note, int eventType){}
}
