package jw.piano.game_objects;

import jw.piano.data.PianoData;
import jw.piano.enums.PianoEffect;
import jw.fluent_api.desing_patterns.observer.implementation.Observer;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Optional;

@Getter
public class PianoDataObserver
{
    private PianoData pianoData;

    private Observer<Location> locationBind = new Observer();

    private Observer<PianoEffect> effectBind = new Observer();

    private Observer<Boolean> interactivePedalBind  = new Observer();

    private Observer<Boolean> desktopClientAllowedBind  = new Observer();

    private Observer<Boolean> detectPressInMinecraftBind  = new Observer();

    private Observer<Boolean> benchActiveBind  = new Observer();

    private Observer<Boolean> enableBind  = new Observer();

    private Observer<Integer> volumeBind  = new Observer();

    private Observer<Integer> skinIdBind  = new Observer();

    public PianoDataObserver()
    {
        desktopClientAllowedBind.bind(PianoData.class,"desktopClientAllowed");
        detectPressInMinecraftBind.bind(PianoData.class,"detectKeyPress");
        skinIdBind.bind(PianoData.class,"skinId");
        interactivePedalBind.bind(PianoData.class,"interactivePedal");
        locationBind.bind(PianoData.class,"location");
        enableBind.bind(PianoData.class,"enable");
        volumeBind.bind(PianoData.class,"volume");
        effectBind.bind(PianoData.class,"effect");
        benchActiveBind.bind(PianoData.class,"benchActive");
    }

    public Optional<PianoData> getObservedPianoData()
    {
       return Optional.of(pianoData);
    }

    public void observePianoData(PianoData pianoData)
    {
        this.pianoData = pianoData;
        skinIdBind.setObject(pianoData);
        interactivePedalBind.setObject(pianoData);
        locationBind.setObject(pianoData);
        enableBind.setObject(pianoData);
        volumeBind.setObject(pianoData);
        effectBind.setObject(pianoData);
        benchActiveBind.setObject(pianoData);
        desktopClientAllowedBind.setObject(pianoData);
        detectPressInMinecraftBind.setObject(pianoData);
    }

    @Override
    public String toString() {
        return "PianoDataObserver{" +
                "pianoData=" + pianoData +
                ", locationBind=" + locationBind +
                ", effectBind=" + effectBind +
                ", enableBind=" + enableBind +
                ", volumeBind=" + volumeBind +
                '}';
    }
}
