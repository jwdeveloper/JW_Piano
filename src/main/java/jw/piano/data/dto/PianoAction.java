package jw.piano.data.dto;

import jw.piano.spigot.gameobjects.models.PianoGameObject;

import java.util.UUID;

public class PianoAction
{
    public record PianoEvent(UUID pianoId, int velocity, int note, int eventType){}

    public record WorkerInfo(PianoGameObject model, int velocity, int note, int eventType){}
}
