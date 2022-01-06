package jw.piano.management;

import jw.dependency_injection.Injectable;
import jw.dependency_injection.InjectionManager;
import jw.events.EventBase;
import jw.piano.autoplayer.Chord;
import jw.piano.autoplayer.ChordFactory;
import jw.piano.autoplayer.Sound;
import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;
import jw.piano.model.PianoModel;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Injectable(autoInit = true)
public class PianoEvents extends EventBase {

    private final PianoManager pianoManager;
    private final Settings settings;
    private final List<PianoPlayingEvent> pianoPlayingEventList;

    public PianoEvents(PianoManager pianoManager, Settings settings)
    {
        super();
        this.pianoManager = pianoManager;
        this.settings = settings;
        this.pianoPlayingEventList = new ArrayList<>();
    }

    @Override
    public void onPluginStop(PluginDisableEvent pluginEnableEvent) {
        pianoManager.destroyPianos();
    }

    @EventHandler
    public void onChangeSlotEvent(PlayerSwapHandItemsEvent event)
    {
        final Optional<PianoPlayingEvent> pianoPlayingEvent = pianoPlayingEventList.stream()
                .filter(e -> e.getPlayer()
                        .equals(event.getPlayer()))
                .findAny();


        if(pianoPlayingEvent.isPresent())
        {
            pianoPlayingEvent.get().onPressPedal();
            event.setCancelled(true);
        }
    }

    PianoMelodyPlayer pianoMelodyPlayer;
    @EventHandler
    public void onChangeHandSlotEvent(PlayerItemHeldEvent event)
    {
        final Optional<PianoPlayingEvent> pianoPlayingEvent = pianoPlayingEventList.stream()
                .filter(e -> e.getPlayer()
                        .equals(event.getPlayer()))
                .findAny();

        if(!pianoPlayingEvent.isPresent())
           return;

        List<Chord> melody = new ArrayList<>();
        melody.add(ChordFactory.majnor(Sound.C));
        melody.add(ChordFactory.majnor(Sound.D));
        melody.add(ChordFactory.majnor(Sound.E));
        melody.add(ChordFactory.majnor(Sound.F));
        melody.add(ChordFactory.majnor(Sound.G));
        melody.add(ChordFactory.majnor(Sound.A));
        melody.add(ChordFactory.majnor(Sound.B));

           if(pianoMelodyPlayer == null)
           {
               pianoMelodyPlayer = new PianoMelodyPlayer(melody.get(event.getNewSlot()),pianoPlayingEvent.get().getPianoModel());
               pianoMelodyPlayer.Play();
           }
           else
               pianoMelodyPlayer.setMelody(melody.get(event.getNewSlot()));

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEvent(PlayerInteractEvent event)
    {
        if(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
        {
           final Player player = event.getPlayer();
           final Optional<PianoPlayingEvent> pianoPlayingEvent = pianoPlayingEventList.stream()
                                                 .filter(e -> e.getPlayer()
                                                 .equals(event.getPlayer()))
                                                 .findAny();




            if(pianoPlayingEvent.isPresent())
            {
                if(pianoPlayingEvent.get().getPianoModel().openViewHitBox.isCollider(player.getEyeLocation(),5))
                {
                    MenuGUI gui = InjectionManager.getObjectByPlayer(MenuGUI.class,player.getUniqueId());
                    gui.openPianoView(player,pianoPlayingEvent.get().getPianoModel().getPianoData());
                }

              pianoPlayingEvent.get().onPlayerClick(player.getEyeLocation());
              event.setCancelled(true);
            }
            else
            {
                PianoModel pianoModel = getClosestPiano(event.getPlayer().getLocation());
                if(pianoModel == null)
                    return;

              /*  for (PianoKey p:pianoModel.getPianoKeys())
                {
                    p.getHitBox().showHitBox();
                }*/
                this.pianoPlayingEventList.add(new PianoPlayingEvent(
                                                    player,
                                                    pianoModel,
                                                    pianoPlayingEventList::remove));
            }
        }
    }

    public PianoModel getClosestPiano(Location playerLocation)
    {
        final float minDistance  = Float.MAX_VALUE;
        final float minRange = settings.maxDistanceFromPiano;
        PianoModel result  = null;

        for(PianoModel pianoModel:this.pianoManager.getPianos())
        {
            final double distance = playerLocation.distance(pianoModel.getLocation());

             if(distance < minRange && distance <= minDistance)
             {
                 result = pianoModel;
             }
        }
        return result;
    }
}
