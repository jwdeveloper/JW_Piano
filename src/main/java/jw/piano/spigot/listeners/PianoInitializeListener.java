package jw.piano.spigot.listeners;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.events.EventBase;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginConsts;
import jw.piano.core.repositories.PianoDataRepository;
import jw.piano.core.services.PianoService;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.persistence.PersistentDataType;

@Injection(lazyLoad = false)
public class PianoInitializeListener extends EventBase {

    private final PianoService pianoService;
    private final PianoDataRepository pianoDataRepository;

    public PianoInitializeListener(PianoService pianoService, PianoDataRepository pianoDataRepository) {
        this.pianoService = pianoService;
        this.pianoDataRepository = pianoDataRepository;
    }


    @EventHandler
    public void onServerLoad(ServerLoadEvent event) {
        for (var pianoData : pianoDataRepository.findAll()) {
            pianoService.create(pianoData);
        }

        FluentApi.tasks().taskTimer(20, (iteration, task) ->
                {
                  // pianoService.clear();
                })
                .startAfterTicks(20 * 5)
                .stopAfterIterations(1)
                .run();
    }

    public static void removeOldArmorstands(Location location, String guid) {
        var entities = location.getWorld().getNearbyEntities(location, 4, 6, 4);
        for (var entity : entities) {
            var container = entity.getPersistentDataContainer();
            if (!container.has(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING)) {
                continue;
            }
            var id = container.get(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING);
            if (!guid.equals(id)) {
                continue;
            }
            entity.remove();
        }
    }


}
