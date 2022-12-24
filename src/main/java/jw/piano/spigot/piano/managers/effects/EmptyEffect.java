package jw.piano.spigot.piano.managers.effects;

import jw.piano.api.managers.effects.EffectInvoker;
import org.bukkit.Location;

public class EmptyEffect implements EffectInvoker {
    @Override
    public String getName() {
        return "none";
    }

    @Override
    public void onNote(Location location, int noteIndex, int velocity) {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {

    }
}
