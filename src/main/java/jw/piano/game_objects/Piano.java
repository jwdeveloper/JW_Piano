package jw.piano.game_objects;

import jw.piano.data.PianoData;
import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;
import jw.piano.game_objects.models.PianoModel;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;


@Getter
public class Piano {
    private final PianoModel pianoModel;
    private final PianoDataObserver pianoDataObserver;
    private final Settings settings;
    private PianoInteractionHandler pianoInteractionHandler;

    private boolean isCreated;

    public Piano(PianoData pianoData) {

        pianoModel = new PianoModel();
        pianoDataObserver = configurePianoObserver(pianoData,pianoModel);

        settings = FluentInjection.getInjection(Settings.class);
    }

    public PianoData getPianoData()
    {
        return pianoDataObserver.getPianoData();
    }


    public void openGUIPanel(Player player) {
        final var gui = FluentInjection.getPlayerInjection(MenuGUI.class, player.getUniqueId());
        gui.openPianoView(player, this);
    }

    public void create() {
        pianoModel.create(pianoDataObserver.getLocationBind().get());
        pianoModel.setVolume(pianoDataObserver.getVolumeBind().get());
        pianoModel.setPianoType(pianoDataObserver.getPianoTypeBind().get());
        pianoModel.setEffect(pianoDataObserver.getEffectBind().get());
        pianoInteractionHandler = new PianoInteractionHandler(pianoModel);
        isCreated = true;
    }

    public void destroy() {
        pianoModel.destroy();
        isCreated = false;
    }

    public Location getLocation() {
        return pianoDataObserver.getLocationBind().get();
    }

    public void handlePlayerInteraction(Player player) {
        if (!isCreated)
            return;
        if (pianoModel.getOpenViewHitBox().isCollider(player.getEyeLocation(), 3)) {
            openGUIPanel(player);
            return;
        }
        pianoInteractionHandler.onPlayerClick(player.getEyeLocation());
    }

    public void handlePlayerPedalPress()
    {
        if (!isCreated)
            return;

        var sustain = pianoModel.getSustainPedal();
        if(sustain.isPressed())
        {
            sustain.release();
        }
        else
        {
            sustain.press();
        }

    }

    public boolean isLocationInPianoRage(Location location) {
        if (!isCreated)
            return false;

        if(location.getWorld() != location.getWorld())
        {
            return false;
        }

        final var minDistance = settings.getMinDistanceToPiano();
        final var distance = location.distance(getLocation());
        return distance <= minDistance;
    }


    private PianoDataObserver configurePianoObserver(PianoData pianoData, PianoModel pianoModel)
    {
        var observer = new PianoDataObserver();
        observer.observePianoData(pianoData);
        observer.getPianoTypeBind().onChange(pianoModel::setPianoType);
        observer.getLocationBind().onChange(value ->
        {
            destroy();
            create();
        });
        observer.getEnableBind().onChange(value ->
        {
            if (value)
                create();
            else
                destroy();
        });
        observer.getVolumeBind().onChange(value ->
        {
            pianoModel.setVolume(value);
        });
        observer.getEffectBind().onChange(value ->
        {
            pianoModel.setEffect(value);
        });
        return observer;
    }

}
