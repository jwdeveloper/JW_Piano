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

package jw.piano.spigot.listeners;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.events.EventBase;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.core.repositories.PianoDataRepository;
import jw.piano.core.services.PianoService;
import org.bukkit.Chunk;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Injection(lazyLoad = false)
public class PianoInitializeListener extends EventBase {

    private final PianoService pianoService;
    private final PianoDataRepository pianoDataRepository;

    @Inject
    public PianoInitializeListener(PianoService pianoService, PianoDataRepository pianoDataRepository) {
        this.pianoService = pianoService;
        this.pianoDataRepository = pianoDataRepository;


    }
   private final Set<Chunk> chunks= new HashSet<>();

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        for (var pianoData : pianoDataRepository.findAll()) {
            var chunk = pianoData.getLocation().getChunk();
            pianoService.initialize(pianoData);
            chunks.add(chunk);
            chunk.setForceLoaded(true);
            chunk.load();
            FluentApi.tasks().taskTimer(10, (iteration, task) ->
                    {
                        pianoService.reset();
                    })
                    .startAfterTicks(20 * 5)
                    .stopAfterIterations(1)
                    .run();

        }
    }
}
