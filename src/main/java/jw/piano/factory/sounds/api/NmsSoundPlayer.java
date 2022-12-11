package jw.piano.factory.sounds.api;

import org.bukkit.Location;

public interface NmsSoundPlayer
{
    void play(Location location, int note, float volume, boolean pressed);

    void test();
}
