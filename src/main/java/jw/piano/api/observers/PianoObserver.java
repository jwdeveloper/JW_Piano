package jw.piano.api.observers;

import jw.piano.api.data.models.BenchSettings;
import jw.piano.api.data.models.PedalsSettings;
import jw.piano.api.data.models.PianoData;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import lombok.Getter;
import org.bukkit.Location;

@Getter
public class PianoObserver
{
    private PianoData pianoData;

    private final Observer<Location> locationBind = new Observer<>();

    private final Observer<Boolean> pedalsInteractionBind = new Observer<>();

    private final Observer<Boolean> desktopClientAllowedBind  = new Observer<>();

    private final Observer<Boolean> keyboardInteraction = new Observer<>();

    private final Observer<Boolean> benchActiveBind  = new Observer<>();

    private final Observer<Boolean> activeBind = new Observer<>();

    private final Observer<Integer> volumeBind  = new Observer<>();
    public final Observer<String> skinNameBind = new Observer<>();

    public final Observer<String> effectNameBind = new Observer<>();

    private final Observer<Boolean> showGuiHitBoxBind = new Observer<>();

    private final MidiPlayerSettingsObserver midiPlayerSettingsObserver;

    public PianoObserver()
    {
        locationBind.bind(PianoData.class,"location");
        volumeBind.bind(PianoData.class,"volume");
        desktopClientAllowedBind.bind(PianoData.class,"desktopClientAllowed");
        keyboardInteraction.bind(PianoData.class,"interactiveKeyboard");
        activeBind.bind(PianoData.class,"active");
        skinNameBind.bind(PianoData.class,"skinName");
        effectNameBind.bind(PianoData.class,"effectName");



        pedalsInteractionBind.bind(PedalsSettings.class,"pedalInteraction");
        benchActiveBind.bind(BenchSettings.class,"active");


        midiPlayerSettingsObserver = new MidiPlayerSettingsObserver();
    }

    public void subscribe(PianoData pianoData)
    {
        this.pianoData = pianoData;
        pedalsInteractionBind.setObject(pianoData);
        locationBind.setObject(pianoData);
        activeBind.setObject(pianoData);
        volumeBind.setObject(pianoData);

        desktopClientAllowedBind.setObject(pianoData);
        keyboardInteraction.setObject(pianoData);
        showGuiHitBoxBind.setObject(pianoData);

        skinNameBind.setObject(pianoData);
        effectNameBind.setObject(pianoData);

        benchActiveBind.setObject(pianoData.getBenchSettings());
        pedalsInteractionBind.setObject(pianoData.getPedalsSettings());
        midiPlayerSettingsObserver.setObject(pianoData.getMidiPlayerSettings());
    }

    public void unsubscribe()
    {
        pedalsInteractionBind.setObject(null);
        locationBind.setObject(null);
        activeBind.setObject(null);
        volumeBind.setObject(null);
        benchActiveBind.setObject(null);
        desktopClientAllowedBind.setObject(null);
        keyboardInteraction.setObject(null);
        showGuiHitBoxBind.setObject(null);
        midiPlayerSettingsObserver.setObject(null);
    }
}
