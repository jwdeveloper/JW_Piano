package jw.piano.spigot.gameobjects;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.data.PluginConfig;
import jw.piano.spigot.gameobjects.models.PianoGameObject;
import jw.piano.spigot.gui.MenuGUI;
import jw.piano.services.PianoSkinService;
import jw.piano.spigot.listeners.PianoInitializeListener;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;


@Getter
public class Piano {
    private final PianoGameObject pianoModel;
    private final PianoDataObserver pianoDataObserver;
    private final PluginConfig settings;
    private final PianoSkinService pianoSkinService;
    private boolean isCreated;

    public Piano(PianoData pianoData)
    {
        pianoModel = new PianoGameObject(pianoData.getUuid().toString());
        pianoDataObserver = configurePianoObserver(pianoData, pianoModel);
        settings = FluentApi.container().findInjection(PluginConfig.class);
        pianoSkinService = FluentApi.container().findInjection(PianoSkinService.class);
    }

    public PianoData getPianoData() {
        return pianoDataObserver.getPianoData();
    }

    public void openGUIPanel(Player player) {
        final var gui = FluentApi.playerContext().find(MenuGUI.class, player.getUniqueId());
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
        pianoModel.showGuiHitBox(pianoDataObserver.getShowGuiHitBoxBind().get());
        isCreated = true;
    }


    public void clear() {
        destroy();
        PianoInitializeListener.removeOldArmorstands(
                pianoDataObserver.getPianoData().getLocation(),
                pianoDataObserver.getPianoData().getUuid().toString());
        create();
    }

    public void destroy() {
        pianoModel.onDestroy();
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


    private PianoDataObserver configurePianoObserver(PianoData pianoData, PianoGameObject pianoModel) {
        var observer = new PianoDataObserver();
        observer.setObservationModel(pianoData);
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
        observer.getVolumeBind().onChange(pianoModel::setVolume);
        observer.getEffectBind().onChange(pianoModel::setEffect);
        observer.getShowGuiHitBoxBind().onChange(pianoModel::showGuiHitBox);
        return observer;
    }

}
