package jw.piano.spigot.listeners;

import jw.piano.service.PianoDataService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.events.EventBase;
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
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent chunkLoadEvent)
    {

        for(var a : dataService.get())
        {
            if(a.getLocation().getChunk().equals(chunkLoadEvent.getChunk()))
            {
                var e = chunkLoadEvent.getChunk().getEntities();
            }
        }
    }


}
