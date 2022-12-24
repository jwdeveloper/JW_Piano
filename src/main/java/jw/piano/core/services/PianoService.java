package jw.piano.core.services;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.gameobjects.implementation.GameObjectManager;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.config.PluginConfig;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.Piano;
import jw.piano.core.repositories.PianoDataRepository;
import jw.piano.spigot.piano.PianoImpl;
import org.bukkit.Location;

import java.util.*;

@Injection
public class PianoService {
    private final HashMap<UUID, Piano> pianos = new HashMap<>();
    private final PluginConfig config;
    private final PianoDataRepository pianoDataService;
    private final FluentInjection injection;

    @Inject
    public PianoService(PluginConfig config,PianoDataRepository pianoDataService)
    {
        this.config = config;
        this.pianoDataService = pianoDataService;
        injection = FluentApi.container();
    }

    public Optional<Piano> initalize(PianoData pianoData) {
        var piano = new PianoImpl(pianoData, injection);
        GameObjectManager.register(piano, pianoData.getLocation());
        pianos.put(pianoData.getUuid(), piano);
        return Optional.of(piano);
    }


    public Optional<Piano> create(PianoData pianoData) {
        var model = find(pianoData.getUuid());

        if (model.isPresent()) {
            return Optional.empty();
        }

        if (!pianoDataService.insert(pianoData)) {
            return Optional.empty();
        }
        var piano = new PianoImpl(pianoData, injection);
        GameObjectManager.register(piano, pianoData.getLocation());
        pianos.put(pianoData.getUuid(), piano);
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

        var piano = (PianoImpl)model.get();
        piano.destroy();
        GameObjectManager.unregister(piano);
        pianos.remove(pianoID);
        return true;
    }

    public Optional<Piano> find(UUID pianoID) {
        if (!pianos.containsKey(pianoID)) {
            return Optional.empty();
        }
        return Optional.of(pianos.get(pianoID));
    }

    public List<Piano> findAll() {
        return pianos.values().stream().toList();
    }


    public Optional<Piano> getNearestPiano(Location location) {
        for (Piano piano : pianos.values()) {
            if (piano.isLocationAtPianoRange(location)) {
                return Optional.of(piano);
            }
        }
        return Optional.empty();
    }

    public void reset()
    {
        for(var piano : pianos.values())
        {
            piano.reset();
        }
    }


    public boolean canCreate() {
        final var limit = config.getPianoConfig().getPianoInstancesLimit();
        return pianoDataService.findAll().size() + 1 < limit;
    }
}
