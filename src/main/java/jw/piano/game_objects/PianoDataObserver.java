package jw.piano.game_objects;

import jw.piano.data.PianoData;
import jw.piano.enums.PianoEffect;
import jw.piano.enums.PianoType;
import jw.spigot_fluent_api.desing_patterns.observer.Observer;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Optional;

@Getter
public class PianoDataObserver
{
    private PianoData pianoData;

    private Observer<Location> locationBind = new Observer();

    private Observer<PianoType> pianoTypeBind = new Observer();

    private Observer<PianoEffect> effectBind = new Observer();

    private Observer<Boolean> enableBind  = new Observer();

    private Observer<Integer> volumeBind  = new Observer();

    public PianoDataObserver()
    {
        locationBind.bind(PianoData.class,"location");
        pianoTypeBind.bind(PianoData.class,"pianoType");
        enableBind.bind(PianoData.class,"enable");
        volumeBind.bind(PianoData.class,"volume");
        effectBind.bind(PianoData.class,"effect");
    }

    public Optional<PianoData> getObservedPianoData()
    {
       return Optional.of(pianoData);
    }

    public void observePianoData(PianoData pianoData)
    {
        this.pianoData = pianoData;
        locationBind.setObject(pianoData);
        pianoTypeBind.setObject(pianoData);
        enableBind.setObject(pianoData);
        volumeBind.setObject(pianoData);
        effectBind.setObject(pianoData);
    }

    @Override
    public String toString() {
        return "PianoDataObserver{" +
                "pianoData=" + pianoData +
                ", locationBind=" + locationBind +
                ", pianoTypeBind=" + pianoTypeBind +
                ", effectBind=" + effectBind +
                ", enableBind=" + enableBind +
                ", volumeBind=" + volumeBind +
                '}';
    }
}
