package jw.piano.spigot.gameobjects;

import jw.piano.api.data.models.PianoData;
import jw.piano.api.enums.PianoEffect;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import lombok.Getter;
import org.bukkit.Location;

import java.util.Optional;

@Getter
public class PianoDataObserver
{
    private PianoData pianoData;

    private final Observer<Location> locationBind = new Observer();

    private final Observer<PianoEffect> effectBind = new Observer();

    private final Observer<Boolean> interactivePedalBind  = new Observer();

    private final Observer<Boolean> desktopClientAllowedBind  = new Observer();

    private final Observer<Boolean> detectPressInMinecraftBind  = new Observer();

    private final Observer<Boolean> benchActiveBind  = new Observer();

    private final Observer<Boolean> enableBind  = new Observer();

    private final Observer<Integer> volumeBind  = new Observer();

    private final Observer<Integer> skinIdBind  = new Observer();

    private final Observer<Boolean> showGuiHitBoxBind = new Observer();

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
        showGuiHitBoxBind.bind(PianoData.class,"showGuiHitBox");
    }

    public Optional<PianoData> getObservedPianoData()
    {
       return Optional.of(pianoData);
    }

    public void setObservationModel(PianoData pianoData)
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
        showGuiHitBoxBind.setObject(pianoData);
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
