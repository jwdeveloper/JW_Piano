package jw.piano.api.managers.sounds;

import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.AbstractManager;
import org.bukkit.Location;

public interface SoundsManager extends AbstractManager<PianoSound> {
    void play(final Location location, final int midiSoundIndex, final float volume, final boolean isPedalPressed);

}
