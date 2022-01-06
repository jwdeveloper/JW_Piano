package jw.piano.data;

import jw.piano.utility.PianoTypes;
import jw.spigot_fluent_api.data.models.DataModel;
import jw.spigot_fluent_api.utilites.binding.Observable;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@Getter
@Setter
public class PianoData extends DataModel
{
    private PianoTypes pianoType;
    private Location location;
    private int volume;
    private Boolean enable;


}
