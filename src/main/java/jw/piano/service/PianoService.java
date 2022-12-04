package jw.piano.service;

import jw.piano.data.PianoData;
import jw.piano.game_objects.Piano;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Injection
public class PianoService {

    private final HashMap<UUID, Piano> pianos = new HashMap<>();

    public Optional<Piano> get(UUID pianoID) {
        if (!pianos.containsKey(pianoID)) {
            return Optional.empty();
        }
        return Optional.of(pianos.get(pianoID));
    }

    public Optional<Piano> getNearestPiano(Location location) {
        for (Piano piano : pianos.values()) {
            if (piano.isLocationInPianoRage(location)) {
                return Optional.of(piano);
            }
        }
        return Optional.empty();
    }

    public Optional<Piano> create(PianoData pianoData) {
        var model = get(pianoData.getUuid());
        if (model.isPresent())
            return Optional.empty();

        var piano = new Piano(pianoData);
        pianos.put(pianoData.getUuid(), piano);
        piano.create();
        return Optional.of(piano);
    }



    public boolean delete(UUID pianoID) {
        var model = get(pianoID);
        if (model.isEmpty())
            return false;

        var piano = model.get();
        piano.destroy();

        pianos.remove(pianoID);
        return true;
    }
}
