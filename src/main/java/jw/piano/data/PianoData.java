package jw.piano.data;

import jw.data.models.Entity;
import jw.piano.utility.PianoTypes;
import jw.utilites.binding.BindingField;
import org.bukkit.Location;

public class PianoData extends Entity
{
    public PianoTypes pianoType;
    public Location location;
    public int volume;
    public Boolean isEnable;

    public BindingField<Location> locationBind = new BindingField<>("location",this);
    public BindingField<PianoTypes> pianoTypeBind = new BindingField<>("pianoType",this);
    public BindingField<Boolean> isEnableBind = new BindingField<>("isEnable",this);
}
