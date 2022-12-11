package jw.piano.spigot.gameobjects;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.models.PianoData;
import jw.piano.data.PluginConfig;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.spigot.gameobjects.models.BenchGameObject;
import jw.piano.spigot.gameobjects.models.PianoGameObject;
import jw.piano.spigot.gui.MenuGUI;
import jw.piano.services.PianoSkinService;
import jw.piano.spigot.listeners.PianoInitializeListener;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;

import java.util.Optional;


@Getter
public class Piano {
    private final PianoGameObject pianoModel;
    private final PianoDataObserver pianoDataObserver;
    private final PluginConfig settings;
    private final PianoSkinService pianoSkinService;
    private boolean isCreated;

    public Piano(PianoData pianoData, ArmorStandFactory armorStandFactory)
    {
        pianoModel = new PianoGameObject(pianoData.getUuid().toString(), armorStandFactory);
        pianoDataObserver = configurePianoObserver(pianoData, pianoModel);
        settings = FluentApi.container().findInjection(PluginConfig.class);
        pianoSkinService = FluentApi.container().findInjection(PianoSkinService.class);
    }

    public PianoData getPianoData() {
        return pianoDataObserver.getPianoData();
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
        pianoModel.destroy();
        isCreated = false;
    }

    public Location getLocation() {
        return pianoDataObserver.getLocationBind().get();
    }

    public void handlePlayerInteraction(Player player, Action action) {
        if (!isCreated)
            return;

        if (pianoModel.isGuiHitBoxCollider(player)) {
            final var gui = FluentApi.playerContext().find(MenuGUI.class, player.getUniqueId());
            gui.openPianoView(player, this);
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

       pianoModel.updateSustain();
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

    public Optional<BenchGameObject> getBench()
    {
        if(!isCreated)
            return Optional.empty();

        return Optional.of(pianoModel.getPianoBench());
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
