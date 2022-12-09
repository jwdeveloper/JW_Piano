package jw.piano.spigot.sounds;

import jw.piano.spigot.gameobjects.sounds.SoundsMapper;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class DefaultPlayer implements NmsSoundPlayer
{

    @Override
    public void play(Location location, int note, float volume, boolean pressed)
    {
        location.getWorld().playSound(location,
                SoundsMapper.getSound(note,pressed),
                SoundCategory.VOICE,
                volume,
                1);
    }

    @Override
    public void test() {

    }
}
