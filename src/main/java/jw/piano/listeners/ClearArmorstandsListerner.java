package jw.piano.listeners;

import jw.piano.service.PianoDataService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;

@Injection(lazyLoad = false)
public class ClearArmorstandsListerner extends EventBase
{

    private PianoDataService dataService;

    @Inject
    public ClearArmorstandsListerner(PianoDataService service)
    {
        this.dataService = service;
        FluentLogger.log("Siema 2222");
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent chunkLoadEvent)
    {

        for(var a : dataService.get())
        {
            if(a.getLocation().getChunk().equals(chunkLoadEvent.getChunk()))
            {
                var e = chunkLoadEvent.getChunk().getEntities();
                FluentLogger.log("Siema "+e.length);
            }
        }
    }


}
