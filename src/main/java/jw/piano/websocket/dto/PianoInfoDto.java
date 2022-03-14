package jw.piano.websocket.dto;

import jw.piano.enums.PianoType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PianoInfoDto
{
    private UUID pianoId;

    private PianoType pianoType;

    private String name;

    private Location pianoLocation;
}
