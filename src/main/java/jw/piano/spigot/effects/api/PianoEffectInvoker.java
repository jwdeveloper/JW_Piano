package jw.piano.spigot.effects.api;

import org.bukkit.Location;

public interface PianoEffectInvoker
{
    public void invoke(Location location, int noteIndex, int sensivity);

    void destroy();

    void create();
}
