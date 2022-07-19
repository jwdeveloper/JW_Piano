package jw.piano.sounds;

import jw.piano.game_objects.utils.MappedSounds;
import jw.piano.sounds.NmsSoundPlayer;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class DefaultPlayer implements NmsSoundPlayer
{

    @Override
    public void play(Location location, int note, float volume, boolean pressed)
    {
        location.getWorld().playSound(location,
                MappedSounds.getSound(note,pressed),
                SoundCategory.VOICE,
                volume,
                1);
    }

    @Override
    public void test() {

    }
}
