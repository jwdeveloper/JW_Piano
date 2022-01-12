package jw.piano.data;

import jw.piano.enums.PianoTypes;
import jw.spigot_fluent_api.data.models.DataModel;
import lombok.Data;

import org.bukkit.Location;

@Data
public class PianoData extends DataModel
{
    private PianoTypes pianoType;
    private Location location;
    private int volume;
    private Boolean enable;
}
