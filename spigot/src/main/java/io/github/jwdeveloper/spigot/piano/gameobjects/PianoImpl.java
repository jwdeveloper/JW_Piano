/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveloper.spigot.piano.gameobjects;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.hitbox.InteractiveHitBox;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskManager;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.ArmorStandModel;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObject;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.container.FluentInjection;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.dependecy_injection.FluentInjection;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import io.github.jwdeveloper.spigot.piano.api.config.PianoPluginConfig;
import io.github.jwdeveloper.spigot.piano.api.data.PianoData;
import io.github.jwdeveloper.spigot.piano.api.events.PianoInteractEvent;
import io.github.jwdeveloper.spigot.piano.api.observers.PianoDataObserver;
import io.github.jwdeveloper.spigot.piano.api.piano.MidiPlayer;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveloper.spigot.piano.gameobjects.bench.BenchImpl;
import io.github.jwdeveloper.spigot.piano.gameobjects.keyboard.KeyboardImpl;
import io.github.jwdeveloper.spigot.piano.gameobjects.managers.EffectManagerImpl;
import io.github.jwdeveloper.spigot.piano.gameobjects.managers.SkinManagerImpl;
import io.github.jwdeveloper.spigot.piano.gameobjects.managers.SoundsManagerImpl;
import io.github.jwdeveloper.spigot.piano.gameobjects.pedals.PedalGroupImpl;
import io.github.jwdeveloper.spigot.piano.gameobjects.token.TokenGeneratorImpl;
import io.github.jwdeveoper.spigot.piano.core.services.MidiLoaderService;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.config.PluginConfig;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.observers.PianoDataObserver;
import jw.piano.api.piano.MidiPlayer;
import jw.piano.api.piano.Piano;
import jw.piano.core.services.MidiLoaderService;
import jw.piano.spigot.gui.PianoListGUI;
import jw.piano.spigot.piano.bench.BenchImpl;
import jw.piano.spigot.piano.keyboard.KeyboardImpl;
import jw.piano.spigot.piano.managers.EffectManagerImpl;
import jw.piano.spigot.piano.managers.SkinManagerImpl;
import jw.piano.spigot.piano.managers.SoundsManagerImpl;
import jw.piano.spigot.piano.pedals.PedalGroupImpl;
import jw.piano.spigot.piano.token.TokenGeneratorImpl;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
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
    private PianoDataObserver pianoObserver;
    @Getter
    private final EffectManagerImpl effectManager;
    @Getter
    private final SkinManagerImpl skinManager;
    @Getter
    private final SoundsManagerImpl soundsManager;
    @Getter
    private TokenGeneratorImpl tokenGenerator;

    private final PianoData pianoData;
    private final PianoPluginConfig pluginConfig;
    private final FluentTranslator translator;
    private final FluentMediator mediator;
    private final MidiLoaderService midiLoaderService;
    private final FluentTaskFactory taskManager;

    private ArmorStandModel modelRenderer;
    private InteractiveHitBox pianoHitBox;

    public PianoImpl(PianoData pianoData, FluentInjection container) {
        this.pianoData = pianoData;
        pluginConfig = container.findInjection(PianoPluginConfig.class);
        effectManager = container.findInjection(EffectManagerImpl.class);
        soundsManager = container.findInjection(SoundsManagerImpl.class);
        skinManager = container.findInjection(SkinManagerImpl.class);
        translator = container.findInjection(FluentTranslator.class);
        mediator = container.findInjection(FluentMediator.class);
        taskManager = container.findInjection(FluentTaskFactory.class);
        midiLoaderService = container.findInjection(MidiLoaderService.class);
    }
    @Override
    public void onCreate() {
        location.setYaw(0);
        location.setPitch(0);


        skinManager.setOnSkinSet(pianoSkin ->
        {
            modelRenderer.setItemStack(pianoSkin.getItemStack());
            setColor(pianoData.getColor());
        });
        pianoHitBox = new InteractiveHitBox(location.clone().add(-0.2, 2, 0), new Vector(1.2, 0.6, 0.3));

        pianoObserver = createPianoObserver(pianoData);
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
        effectManager.create(pianoData);

        midiPlayer = new MidiPlayerImpl(this,taskManager, midiLoaderService);

    }

    @Override
    public void onCreated() {
        setColor(pianoData.getColor());
        showPianist(pianoData.getShowPianist());
        skinManager.setCurrent(pianoData.getSkinName());
        effectManager.setCurrent(pianoData.getEffectName());
        soundsManager.setCurrent(pianoData.getSoundName());
        midiPlayer.enable();
    }

    @Override
    public void onDestroy() {
        effectManager.destroy();
        midiPlayer.stop();
    }


    @Override
    public void setColor(Color color) {
        pianoData.setColor(color);
        modelRenderer.setColor(color);
        bench.setColor(color);
        pedals.setColor(color);
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

    public void showPianist(boolean isVisible)
    {
       bench.getPianist().setVisible(isVisible);
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
        /*FluentApi.playerContext()
                .find(PianoListGUI.class, player);
        //TODO
              //  .openPianoGui(player, this);*/
        return true;
    }


    public void triggerPedal(int isPressed, int midiIndex, int velocity) {
        pedals.triggerPedal(isPressed, midiIndex, velocity);
    }


    public void triggerNote(int pressed, int midiIndex, int velocity, int trackId) {
        keyboard.triggerNote(pressed, midiIndex, velocity, trackId);
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        final var player = event.getPlayer();
        if (pianoHitBox.isCollider(player.getEyeLocation(), 3)) {
            openGui(player);
            return true;
        }
        if (bench.triggerPlayerClick(event)) {
            return true;
        }
        if (keyboard.triggerPlayerClick(event))
        {
            return true;
        }
        if (pedals.triggerPlayerClick(event)) {
            return true;
        }
        return false;
    }

    private PianoDataObserver createPianoObserver(PianoData data) {
        var observer = new PianoDataObserver(data);
        observer.getSkinName().subscribe(skinManager::setCurrent);
        observer.getEffectName().subscribe(effectManager::setCurrent);
        observer.getBenchSettings().getActive().subscribe(value -> getBench().setVisible(value));
        observer.getActive().subscribe(this::setVisible);
        observer.getVolume().subscribe(this::setVolume);
        observer.getShowPianist().subscribe(this::showPianist);
        return observer;
    }

    @Override
    public void reset() {
        location.getChunk().setForceLoaded(true);
        var entities = location.getWorld().getNearbyEntities(location, 4, 6, 4);
        var piano = pianoData.getUuid().toString();
        for (var entity : entities) {
            var container = entity.getPersistentDataContainer();
            if (!container.has(PianoPluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING)) {
                continue;
            }
            var id = container.get(PianoPluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING);
            if (!id.equals(piano)) {
                continue;
            }
            entity.remove();
        }
        modelRenderer.refresh();

        keyboard.refresh();
        bench.refresh();
        pedals.refresh();
        effectManager.refresh();
    }
}
