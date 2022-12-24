package jw.piano.api.managers.effects;

import org.bukkit.Location;
import org.bukkit.Material;

public interface EffectInvoker
{
    String getName();

    default Material getIcon()
    {
        return Material.FIREWORK_ROCKET;
    }

    void onNote(Location location, int noteIndex, int velocity);

    void onDestroy();

    void onCreate();
}
