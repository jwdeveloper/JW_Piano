package jw.piano.handlers.piano_details;

import lombok.Data;

@Data
public class PianoDetailsResponse
{
    private String name;

    private String id;

    private String type;

    private String location;

    private int volume;
}
