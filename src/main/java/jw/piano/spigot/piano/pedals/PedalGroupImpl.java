package jw.piano.spigot.piano.pedals;

import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.pedals.Pedal;
import jw.piano.api.piano.pedals.PedalGroup;
import org.bukkit.Location;

public class PedalGroupImpl extends GameObject implements PedalGroup {

    private final Pedal[] pedals;
    private final PianoData pianoData;

    public PedalGroupImpl(PianoData pianoData) {
        pedals = new Pedal[3];
        this.pianoData = pianoData;
    }


    @Override
    public void onCreate() {
        for (int i = 0; i < 3; i++) {
            final var pedalLocation = location.clone().add(-0.4 + (i * 0.20), -0.1, 0.1f);
            final var pedal = new PedalImpl();
            pedal.setLocation(pedalLocation);
            pedals[i] = addGameComponent(pedal);
        }
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public Pedal[] getPedals() {
        return new Pedal[0];
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        return false;
    }


    public boolean triggerSustainPedal()
    {
        if(!pianoData.getPedalsSettings().getPedalInteraction())
        {
            return false;
        }

        if(isSustainPressed())
        {
            triggerPedal(1, 64,100);
        }
        else
        {
            triggerPedal(0, 64,0);
        }
        return true;
    }

    @Override
    public void triggerPedal(int isPressed, int midiIndex, int velocity) {
        final var pedal = switch (midiIndex) {
            case 64 -> pedals[2];
            case 65 -> pedals[1];
            case 67 -> pedals[0];
            default -> null;
        };
        if (pedal == null)
            return;
        if (isPressed == PluginConsts.PRESSED_CODE)
            pedal.press(velocity);
        else
            pedal.release();

        pianoData.getPedalsSettings().setSustainPressed(isSustainPressed());
    }

    public boolean isSustainPressed() {
        return pedals[2].isPressed();
    }
}
