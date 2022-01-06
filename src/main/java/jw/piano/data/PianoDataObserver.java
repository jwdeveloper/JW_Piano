package jw.piano.data;

import jw.piano.model.PianoModel;
import jw.piano.utility.PianoTypes;
import jw.spigot_fluent_api.utilites.binding.Observable;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class PianoDataObserver
{
    private Observable<Location> locationBind = new Observable();

    private Observable<PianoTypes> pianoTypeBind = new Observable();

    private Observable<Boolean> enableBind  = new Observable();

    public PianoDataObserver()
    {
        locationBind.bind(PianoData.class,"location");
        pianoTypeBind.bind(PianoData.class,"pianoType");
        enableBind.bind(PianoData.class,"enable");
    }

    public void observePianoData(PianoData pianoModel)
    {
        locationBind.setObject(pianoModel);
    }
}
