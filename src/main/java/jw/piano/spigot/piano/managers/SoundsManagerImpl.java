package jw.piano.spigot.piano.managers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.core.services.SoundLoaderService;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class SoundsManagerImpl implements SoundsManager {
    private final HashMap<String, PianoSound> values;
    private PianoSound current;

    @Inject
    public SoundsManagerImpl(SoundLoaderService factory)
    {
        values = new LinkedHashMap<>();
        current = factory.getDefaultSound();
        for(var sounds : factory.getSounds())
        {
            register(sounds);
        }
    }

    public void play(final Location location,
                     final int midiSoundIndex,
                     final float volume,
                     final boolean isPedalPressed) {

        final var world = location.getWorld();
        if (world == null) {
            return;
        }

        world.playSound(location,
                current.getSound(midiSoundIndex, isPedalPressed),
                current.getSoundCategory(),
                volume,
                1);
    }

    @Override
    public PianoSound getCurrent() {
        return current;
    }

    @Override
    public List<PianoSound> getItems() {
        return values.values().stream().toList();
    }

    @Override
    public void setCurrent(String name) {
        if (!values.containsKey(name)) {
            return;
        }
        current = values.get(name);
    }

    @Override
    public void setCurrent(PianoSound value) {
        if (!values.containsValue(value)) {
            register(value);
        }
        current = value;
    }

    @Override
    public void register(PianoSound value) {
        if (values.containsValue(value)) {
            return;
        }
        values.put(value.getName(), value);
    }

    @Override
    public void unregister(PianoSound value) {
        if (!values.containsValue(value)) {
            return;
        }
        values.remove(value.getName());
    }
}
