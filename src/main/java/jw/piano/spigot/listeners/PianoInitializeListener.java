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


import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginConsts;
import jw.piano.core.repositories.PianoDataRepository;
import jw.piano.core.services.PianoService;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.EntitiesLoadEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

@Injection(lazyLoad = false)
public class PianoInitializeListener extends EventBase {

    private final PianoService pianoService;
    private final PianoDataRepository pianoDataRepository;

    @Inject
    public PianoInitializeListener(Plugin plugin,
                                   PianoService pianoService,
                                   PianoDataRepository pianoDataRepository) {
        super(plugin);
        this.pianoService = pianoService;
        this.pianoDataRepository = pianoDataRepository;


    }
   private final Set<Chunk> chunks= new HashSet<>();

    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        for (var pianoData : pianoDataRepository.findAll()) {
            var chunk = pianoData.getLocation().getChunk();
            chunks.add(chunk);
            chunk.setForceLoaded(true);
            chunk.load();
            //FluentLogger.LOGGER.log("CHUNK TO LOAD",chunk,chunk.isEntitiesLoaded(),chunk.getEntities().length);
            for(var entity : chunk.getEntities())
            {
                var container = entity.getPersistentDataContainer();
                if (!container.has(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING)) {
                    continue;
                }
                var id = container.get(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING);
                if (!id.equals(pianoData.getUuid().toString())) {
                    continue;
                }
                entity.remove();
            }


            pianoService.initialize(pianoData);
            FluentApi.tasks().taskTimer(10, (iteration, task) ->
                    {
                        pianoService.reset();
                    })
                    .startAfterTicks(20 * 5)
                    .stopAfterIterations(1)
                    .run();

        }
    }
    @EventHandler
    public void onChunk(ChunkLoadEvent event)
    {
       // FluentLogger.LOGGER.log("ChunkLoadEvent LOADED ",event.isNewChunk());
    }

    @EventHandler
    public void onEntitis(EntitiesLoadEvent event)
    {
      //  FluentLogger.LOGGER.log("EntitiesLoadEvent LOADED ",event.getChunk(),event.getEntities().size());
    }
}
