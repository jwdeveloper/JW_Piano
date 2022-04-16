package jw.piano.data;


import jw.piano.enums.PianoType;
import jw.spigot_fluent_api.data.implementation.DataModel;
import lombok.Data;

import org.bukkit.Location;

@Data
public class PianoData extends DataModel
{
    private PianoType pianoType = PianoType.NONE;
    private Location location;
    private Integer volume = 100;
    private Boolean enable = false;
}
