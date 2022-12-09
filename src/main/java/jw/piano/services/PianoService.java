package jw.piano.services;

import jw.piano.api.data.PluginConfig;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.enums.PianoEffect;
import jw.piano.spigot.gameobjects.Piano;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Injection
public class PianoService {
    private final HashMap<UUID, Piano> pianos = new HashMap<>();
    private final PluginConfig config;
    private final PianoDataService pianoDataService;

    public PianoService(PluginConfig config, PianoDataService pianoDataService) {
        this.config = config;
        this.pianoDataService = pianoDataService;
    }


    public Optional<Piano> find(UUID pianoID) {
        if (!pianos.containsKey(pianoID)) {
            return Optional.empty();
        }
        return Optional.of(pianos.get(pianoID));
    }

    public Collection<Piano> findAll() {
        return pianos.values();
    }

    public int clear()
    {
        for(var piano : pianos.values())
        {
            piano.clear();
        }
        return pianos.size();
    }

    public Optional<Piano> getNearestPiano(Location location) {
        for (Piano piano : pianos.values()) {
            if (piano.isLocationInPianoRage(location)) {
                return Optional.of(piano);
            }
        }
        return Optional.empty();
    }


    public boolean canCreate() {
        final var limit = config.getPianoInstancesLimit();
        return pianoDataService.findAll().size() + 1 < limit;
    }

    public Optional<Piano> create(PianoData pianoData) {
        var model = find(pianoData.getUuid());
        if (model.isPresent()) {
            return Optional.empty();
        }

        if (!pianoDataService.insert(pianoData)) {
            return Optional.empty();
        }

        var piano = new Piano(pianoData);
        pianos.put(pianoData.getUuid(), piano);
        piano.create();
        return Optional.of(piano);
    }

    public Optional<Piano> initialize(PianoData pianoData) {
        var model = find(pianoData.getUuid());
        if (model.isPresent()) {
            return Optional.empty();
        }
        if (pianoData.getEffect() == null) {
            pianoData.setEffect(PianoEffect.SIMPLE_PARTICLE);
        }


        var piano = new Piano(pianoData);
        pianos.put(pianoData.getUuid(), piano);
        piano.create();
        return Optional.of(piano);
    }


    public boolean delete(UUID pianoID) {
        var model = find(pianoID);
        if (model.isEmpty()) {
            return false;
        }

        if (!pianoDataService.delete(pianoID)) {
            return false;
        }

        var piano = model.get();
        piano.destroy();

        pianos.remove(pianoID);
        return true;
    }
}
