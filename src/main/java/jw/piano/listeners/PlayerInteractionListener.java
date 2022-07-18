package jw.piano.listeners;

import jw.piano.game_objects.Piano;
import jw.piano.management.PianoPlayingEvent;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
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
    private final FluentTaskTimer checkPlayerToPianoDistanceTask;

    @Inject
    public PlayerInteractionListener(PianoService service) {
        this.pianoService = service;
        this.pianoUsers = new HashMap<>();
        this.checkPlayerToPianoDistanceTask = checkDistanceTask();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        final var player = event.getPlayer();
        final var isPlayerUsingPiano = pianoUsers.containsKey(player);
        if (!isPlayerUsingPiano) {
            var pianoOptional = pianoService.getNearestPiano(player.getLocation());
            if(pianoOptional.isEmpty())
            {
                //there is no piano in player the nearest location;
                return;
            }
            pianoUsers.put(player, pianoOptional.get());
        }
        var piano = pianoUsers.get(player);
        piano.handlePlayerInteraction(player);
        event.setCancelled(true);
    }

    private FluentTaskTimer checkDistanceTask()
    {
        var playersToRemove = new ArrayList<>();
        return new FluentTaskTimer(5,(iteration, task) ->
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

    @EventHandler
    public void onChangeSlotEvent(PlayerSwapHandItemsEvent event) {

        final var piano = pianoUsers.get(event.getPlayer());
        if(piano == null)
            return;

        piano.handlePlayerPedalPress();
        event.setCancelled(true);
    }


    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event)
    {
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



   /*
    //@EventHandler
    public void onChangeHandSlotEvent(PlayerItemHeldEvent event) {
        final Optional<PianoPlayingEvent> pianoPlayingEvent = pianoPlayingEventList.stream()
                .filter(e -> e.getPlayer()
                        .equals(event.getPlayer()))
                .findAny();

        if (!pianoPlayingEvent.isPresent())
            return;

        List<Chord> melody = new ArrayList<>();
        melody.add(ChordFactory.majnor(Sound.C));
        melody.add(ChordFactory.majnor(Sound.D));
        melody.add(ChordFactory.majnor(Sound.E));
        melody.add(ChordFactory.majnor(Sound.F));
        melody.add(ChordFactory.majnor(Sound.G));
        melody.add(ChordFactory.majnor(Sound.A));
        melody.add(ChordFactory.majnor(Sound.B));

        if (pianoMelodyPlayer == null) {
            pianoMelodyPlayer = new PianoMelodyPlayer(melody.get(event.getNewSlot()), pianoPlayingEvent.get().getPianoModel());
            pianoMelodyPlayer.Play();
        } else
            pianoMelodyPlayer.setMelody(melody.get(event.getNewSlot()));

    }*/
}
