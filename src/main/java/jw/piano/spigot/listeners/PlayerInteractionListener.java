package jw.piano.spigot.listeners;

import jw.piano.spigot.gameobjects.Piano;
import jw.piano.services.PianoService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.events.EventBase;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.*;

@Injection(lazyLoad = false)
public class PlayerInteractionListener extends EventBase {

    private final PianoService pianoService;
    private final HashMap<Player, Piano> pianoUsers;
    private final SimpleTaskTimer checkPlayerToPianoDistanceTask;

    @Inject
    public PlayerInteractionListener(PianoService service) {
        this.pianoUsers = new HashMap<>();
        this.pianoService = service;
        this.checkPlayerToPianoDistanceTask = checkDistanceTask();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEvent(PlayerInteractEvent event) {
        if(isPluginDisabled())
        {
            return;
        }
        final var player = event.getPlayer();
        if (!pianoUsers.containsKey(player)) {
            var pianoOptional = pianoService.getNearestPiano(player.getLocation());
            if(pianoOptional.isEmpty())  //there is no piano in player the nearest location;
            {
                return;
            }
            pianoUsers.put(player, pianoOptional.get());
        }


        pianoUsers.get(player).handlePlayerInteraction(player, event.getAction());
        event.setCancelled(true);
    }


    @EventHandler
    public void onChangeSlotEvent(PlayerSwapHandItemsEvent event) {
        if(isPluginDisabled())
        {
            return;
        }
        final var piano = pianoUsers.get(event.getPlayer());
        if(piano == null)
            return;

        piano.handlePlayerPedalPress();
        event.setCancelled(true);
    }


    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event)
    {
        if(isPluginDisabled())
        {
            return;
        }
        pianoUsers.remove(event.getPlayer());
    }

    @Override
    public void onPluginStart(PluginEnableEvent event) {
        checkPlayerToPianoDistanceTask.run();
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        checkPlayerToPianoDistanceTask.stop();
    }

    private SimpleTaskTimer checkDistanceTask()
    {
        var playersToRemove = new ArrayList<>();
        return new SimpleTaskTimer(5,(iteration, task) ->
        {
            playersToRemove.clear();
            for(var pianoPlayer : pianoUsers.entrySet())
            {
                if(!pianoPlayer.getValue().isLocationInPianoRage(pianoPlayer.getKey().getLocation()))
                {
                    playersToRemove.add(pianoPlayer.getKey());
                }
            }
            for(var toRemove:playersToRemove)
            {
                pianoUsers.remove(toRemove);
            }
        });
    }

}
