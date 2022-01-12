package jw.piano.listeners;

import jw.piano.game_objects.Piano;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.*;

@SpigotBean(lazyLoad = false)
public class PlayerInteractionListener extends EventBase {

    private final PianoService service;
    private final HashMap<Player, Piano> pianoPlayers;
    private final FluentTaskTimer checkPlayerToPianoDistanceTask;

    public PlayerInteractionListener(PianoService service) {
        super();
        this.service = service;
        this.pianoPlayers = new HashMap<>();
        this.checkPlayerToPianoDistanceTask = checkDistanceTask();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        final var player = event.getPlayer();
        final var isPlayerUsingPiano = pianoPlayers.containsKey(player);
        if (!isPlayerUsingPiano) {
            var pianoOptional = service.getNearestPiano(player.getLocation());
            if(pianoOptional.isEmpty())
            {
                //there is no piano is player the nearest location;
                return;
            }
            pianoPlayers.put(player, pianoOptional.get());
        }
        var piano = pianoPlayers.get(player);
        piano.handlePlayerInteraction(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event)
    {
        if(pianoPlayers.containsKey(event.getPlayer()))
        {
            pianoPlayers.remove(event.getPlayer());
        }
    }

    @Override
    public void onPluginStart(PluginEnableEvent event) {
        checkPlayerToPianoDistanceTask.run();
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        checkPlayerToPianoDistanceTask.stop();
    }


    private FluentTaskTimer checkDistanceTask()
    {
        return new FluentTaskTimer(5,(iteration, task) ->
        {
            var playersToRemove = new ArrayList<>();
            for(var pianoPlayer : pianoPlayers.entrySet())
            {
                if(!pianoPlayer.getValue().isLocationInPianoRage(pianoPlayer.getKey().getLocation()))
                {
                   playersToRemove.add(pianoPlayer.getKey());
                }
            }

            for(var toRemove:playersToRemove)
            {
              pianoPlayers.remove(toRemove);
            }
        });
    }


    //to do
   /* private PianoMelodyPlayer pianoMelodyPlayer;
    //  @EventHandler
    public void onChangeSlotEvent(PlayerSwapHandItemsEvent event) {
        final Optional<PianoPlayingEvent> pianoPlayingEvent = pianoPlayingEventList.stream()
                .filter(e -> e.getPlayer()
                        .equals(event.getPlayer()))
                .findAny();


        if (pianoPlayingEvent.isPresent()) {
            pianoPlayingEvent.get().onPressPedal();
            event.setCancelled(true);
        }
    }

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
