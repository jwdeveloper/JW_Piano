/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveoper.spigot.piano.core.services;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.extension.gameobject.api.GameObjectManager;
import io.github.jwdeveloper.ff.extension.translator.implementation.config.TranslatorConfig;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjection;
import io.github.jwdeveloper.spigot.piano.api.config.PianoPluginConfig;
import io.github.jwdeveloper.spigot.piano.api.data.PianoData;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveoper.spigot.piano.core.repositories.PianoDataRepository;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Injection
public class PianoService {
    private final HashMap<UUID, Piano> pianos = new HashMap<>();
    private final ConfigOptions<PianoPluginConfig>  config;
    private final PianoDataRepository pianoDataService;
    private final FluentInjection injection;
    private final GameObjectManager gameObjectManager;

    @Inject
    public PianoService(ConfigOptions<PianoPluginConfig> config,
                        PianoDataRepository pianoDataService,
                        GameObjectManager gameObjectManager)
    {
        this.config = config;
        this.pianoDataService = pianoDataService;
        this.gameObjectManager = gameObjectManager;
        injection = FluentApi.container();
    }

    public void initialize(PianoData pianoData) {
        var piano = new PianoImpl(pianoData, injection);
        gameObjectManager.register(piano, pianoData.getLocation());
        pianos.put(pianoData.getUuid(), piano);
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
        gameObjectManager.register(piano, pianoData.getLocation());
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

        var piano = model.get();
        gameObjectManager.unregister(piano);
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
        final var limit = config.get().getPianoConfig().getPianoInstancesLimit();
        return pianoDataService.findAll().size() + 1 < limit;
    }
}
