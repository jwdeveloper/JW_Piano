package jw.piano.game_objects;

import jw.piano.data.PianoData;
import jw.piano.enums.PianoTypes;
import jw.spigot_fluent_api.desing_patterns.observer.fields.Observable;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Optional;

@Getter
public class PianoDataObserver
{
    private PianoData pianoData;

    private Observable<Location> locationBind = new Observable();

    private Observable<PianoTypes> pianoTypeBind = new Observable();

    private Observable<Boolean> enableBind  = new Observable();

    public PianoDataObserver()
    {
        locationBind.bind(PianoData.class,"location");
        pianoTypeBind.bind(PianoData.class,"pianoType");
        enableBind.bind(PianoData.class,"enable");
    }

    public Optional<PianoData> getObservedPianoData()
    {
       return Optional.of(pianoData);
    }

    public void observePianoData(PianoData pianoData)
    {
        this.pianoData = pianoData;
        locationBind.setObject(pianoData);
    }
}
