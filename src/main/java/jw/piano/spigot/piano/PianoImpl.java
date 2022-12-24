package jw.piano.spigot.piano;

import jw.fluent.api.spigot.gameobjects.implementation.ArmorStandModel;
import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.fluent.api.utilites.math.InteractiveHitBox;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjection;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.config.PluginConfig;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.observers.PianoObserver;
import jw.piano.api.piano.MidiPlayer;
import jw.piano.api.piano.Piano;
import jw.piano.core.services.MidiLoaderService;
import jw.piano.spigot.piano.bench.BenchImpl;
import jw.piano.spigot.piano.managers.EffectManagerImpl;
import jw.piano.spigot.gui.MenuGUI;
import jw.piano.spigot.piano.keyboard.KeyboardImpl;
import jw.piano.spigot.piano.midi.MidiPlayerImpl;
import jw.piano.spigot.piano.pedals.PedalGroupImpl;
import jw.piano.spigot.piano.managers.SkinManagerImpl;
import jw.piano.spigot.piano.managers.SoundsManagerImpl;
import jw.piano.spigot.piano.token.TokenGeneratorImpl;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

public class PianoImpl extends GameObject implements Piano {

    @Getter
    private BenchImpl bench;
    @Getter
    private PedalGroupImpl pedals;
    @Getter
    private KeyboardImpl keyboard;
    @Getter
    private MidiPlayer midiPlayer;
    @Getter
    private PianoObserver pianoObserver;
    @Getter
    private final EffectManagerImpl effectManager;
    @Getter
    private final SkinManagerImpl skinManager;
    @Getter
    private final SoundsManagerImpl soundsManager;
    @Getter
    private TokenGeneratorImpl tokenGenerator;

    private final PianoData pianoData;
    private final PluginConfig pluginConfig;
    private final FluentTranslator translator;
    private final FluentMediator mediator;
    private final MidiLoaderService midiLoaderService;

    private ArmorStandModel modelRenderer;
    private InteractiveHitBox openGuiHitBox;

    public PianoImpl(PianoData pianoData, FluentInjection container) {
        this.pianoData = pianoData;
        pluginConfig = container.findInjection(PluginConfig.class);
        effectManager = container.findInjection(EffectManagerImpl.class);
        soundsManager = container.findInjection(SoundsManagerImpl.class);
        skinManager = container.findInjection(SkinManagerImpl.class);
        translator = container.findInjection(FluentTranslator.class);
        mediator = container.findInjection(FluentMediator.class);
        midiLoaderService = container.findInjection(MidiLoaderService.class);
    }

    @Override
    public void onCreate() {
        location.setYaw(0);
        location.setPitch(0);


        skinManager.setOnSkinSet(pianoSkin -> modelRenderer.setItemStack(pianoSkin.getItemStack()));
        openGuiHitBox = new InteractiveHitBox(location.clone().add(-0.2, 2, 0), new Vector(1.2, 0.6, 0.3));

        pianoObserver = createPianoObserver();
        pianoObserver.subscribe(pianoData);

        tokenGenerator = new TokenGeneratorImpl(pianoData, translator, mediator);

        modelRenderer = new ArmorStandModel();
        modelRenderer.setOnCreated(armorStandModel ->
        {
            armorStandModel.setItemStack(skinManager.getCurrent().getItemStack());
            armorStandModel.setId(PluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
        });
        bench = new BenchImpl(this);
        keyboard = new KeyboardImpl(pianoData, effectManager, soundsManager);
        pedals = new PedalGroupImpl(pianoData);

        addGameComponent(modelRenderer);
        addGameComponent(bench);
        addGameComponent(keyboard);
        addGameComponent(pedals);
        effectManager.create();

        midiPlayer = new MidiPlayerImpl(this, midiLoaderService);
    }

    @Override
    public void onDestroy() {
        effectManager.destroy();
        pianoObserver.unsubscribe();
    }


    @Override
    public void setVisible(boolean isVisible) {
        this.visible = isVisible;
        modelRenderer.setVisible(isVisible);
    }

    @Override
    public void setVolume(int volume) {
        pianoData.setVolume(volume);
    }

    @Override
    public void teleportPlayer(Player player) {
        var destination = location.clone();
        destination.setDirection(location.getDirection().multiply(-1));
        destination.setZ(destination.getZ() + 2);
        player.teleport(destination);
    }

    @Override
    public void teleport(Location location) {
        FluentLogger.LOGGER.warning("Teleportation not supported yet :/");
    }

    @Override
    public boolean isLocationAtPianoRange(Location location) {
        if (location.getWorld() != getLocation().getWorld()) {
            return false;
        }
        final var minDistance = pluginConfig.getPianoConfig().getPianoRange();
        final var distance = location.distance(getLocation());
        return distance <= minDistance;
    }

    @Override
    public boolean openGui(Player player) {
        FluentApi.playerContext()
                .find(MenuGUI.class, player)
                .openPianoGui(player, this);
        return true;
    }


    public void triggerPedal(int isPressed, int midiIndex, int velocity) {
        pedals.triggerPedal(isPressed, midiIndex, velocity);
    }


    public void triggerNote(int pressed, int midiIndex, int velocity) {
        keyboard.triggerNote(pressed, midiIndex, velocity);
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        final var player = event.getPlayer();
        if (openGuiHitBox.isCollider(player.getEyeLocation(), 3)) {
            openGui(player);
            return true;
        }
        if (bench.triggerPlayerClick(event)) {
            return true;
        }
        if (keyboard.triggerPlayerClick(event)) {
            return true;
        }
        if (pedals.triggerPlayerClick(event)) {
            return true;
        }
        return false;
    }

    private PianoObserver createPianoObserver() {
        var observer = new PianoObserver();
        observer.getSkinNameBind().onChange(skinManager::setCurrent);
        observer.getEffectNameBind().onChange(effectManager::setCurrent);
        observer.getBenchActiveBind().onChange(value -> getBench().setVisible(value));
        observer.getActiveBind().onChange(this::setVisible);
        observer.getVolumeBind().onChange(this::setVolume);
        return observer;
    }

    @Override
    public void reset()
    {
        var entities = location.getWorld().getNearbyEntities(location, 4, 6, 4);
        var pianoid = pianoData.getUuid().toString();
        for (var entity : entities) {
            var container = entity.getPersistentDataContainer();
            if (!container.has(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING)) {
                continue;
            }
            var id = container.get(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING);
            if (!id.equals(pianoid)) {
                continue;
            }
            entity.remove();
        }
        modelRenderer.refresh();

        keyboard.refresh();
        bench.refresh();
        pedals.refresh();
    }
}
