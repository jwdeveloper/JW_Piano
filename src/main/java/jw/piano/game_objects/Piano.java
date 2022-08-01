package jw.piano.game_objects;

import jw.piano.data.PianoData;
import jw.piano.data.PluginConfig;
import jw.piano.gui.MenuGUI;
import jw.piano.game_objects.models.PianoModel;
import jw.piano.service.PianoSkinService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;


@Getter
public class Piano {
    private final PianoModel pianoModel;
    private final PianoDataObserver pianoDataObserver;
    private final PluginConfig settings;
    private PianoSkinService pianoSkinService;

    private boolean isCreated;

    public Piano(PianoData pianoData) {

        pianoModel = new PianoModel();
        pianoDataObserver = configurePianoObserver(pianoData, pianoModel);

        settings = FluentInjection.getInjection(PluginConfig.class);
        pianoSkinService = FluentInjection.getInjection(PianoSkinService.class);
    }

    public PianoData getPianoData() {
        return pianoDataObserver.getPianoData();
    }


    public void openGUIPanel(Player player) {
        final var gui = FluentInjection.getPlayerInjection(MenuGUI.class, player.getUniqueId());
        gui.openPianoView(player, this);
    }

    public void create() {
        pianoModel.create(pianoDataObserver.getLocationBind().get());
        pianoModel.setVolume(pianoDataObserver.getVolumeBind().get());
        pianoDataObserver.getSkinIdBind().set(pianoDataObserver.getSkinIdBind().get());
        pianoDataObserver.getDesktopClientAllowedBind().set(pianoDataObserver.getDesktopClientAllowedBind().get());
        pianoDataObserver.getDetectPressInMinecraftBind().set(pianoDataObserver.getDetectPressInMinecraftBind().get());
        pianoModel.setEffect(pianoDataObserver.getEffectBind().get());
        pianoModel.getPianoBench().setState(pianoDataObserver.getBenchActiveBind().get());
        isCreated = true;
    }

    public void destroy() {
        pianoModel.destroy();
        isCreated = false;
    }

    public Location getLocation() {
        return pianoDataObserver.getLocationBind().get();
    }

    public void handlePlayerInteraction(Player player, Action action) {
        if (!isCreated)
            return;

        if (pianoModel.getOpenViewHitBox().isCollider(player.getEyeLocation(), 3)) {
            openGUIPanel(player);
            return;
        }
        if(!pianoDataObserver.getDetectPressInMinecraftBind().get())
        {
            return;
        }
        pianoModel.handlePlayerClick(player,action);
    }

    public void handlePlayerPedalPress() {
        if (!isCreated)
            return;

        if (!pianoDataObserver.getInteractivePedalBind().get()) {
            return;
        }

        var sustain = pianoModel.getSustainPedal();
        if (sustain.isPressed()) {
            sustain.release();
        } else {
            sustain.press();
        }

    }

    public boolean isLocationInPianoRage(Location location) {
        if (!isCreated)
            return false;

        if (location.getWorld() != getLocation().getWorld()) {
            return false;
        }

        final var minDistance = settings.getMinDistanceToPiano();
        final var distance = location.distance(getLocation());
        return distance <= minDistance;
    }


    private PianoDataObserver configurePianoObserver(PianoData pianoData, PianoModel pianoModel) {
        var observer = new PianoDataObserver();
        observer.observePianoData(pianoData);
        observer.getSkinIdBind().onChange(value ->
        {
            var skin = pianoSkinService.getSkinById(value);
            if (skin.isEmpty()) {
                return;
            }
            pianoModel.setPianoSkin(skin.get());
        });
        observer.getLocationBind().onChange(value ->
        {
            destroy();
            create();
        });
        observer.getBenchActiveBind().onChange(value ->
        {
            pianoModel.getPianoBench().setState(value);
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
