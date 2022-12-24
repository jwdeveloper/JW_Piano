package jw.piano.core.mediator.piano.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

public class PianoInfo
{

    public record Request(UUID pianoId){}

    @Data
    public static class Response
    {
        private String name;

        private String id;

        private String type;

        private String location;

        private int volume;
    }


    @Data
    @AllArgsConstructor
    public static class Dto
    {
        private UUID pianoId;

        private String name;

        private Location pianoLocation;
    }

}
