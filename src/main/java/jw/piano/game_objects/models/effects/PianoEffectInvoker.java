package jw.piano.game_objects.models.effects;

import org.bukkit.Location;

public interface PianoEffectInvoker
{
    public void invoke(Location location, int noteIndex, int sensivity);

    void destory();

    void create();
}
