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

package jw.piano.spigot.gui.piano;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.player_context.api.PlayerContext;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.observers.PianoDataObserver;
import jw.piano.api.piano.Piano;
import jw.piano.spigot.gui.bench.BenchViewGui;
import jw.piano.spigot.gui.keyboard.KeyboardGui;
import jw.piano.spigot.gui.midi.MidiPlayerGui;
import jw.piano.spigot.piano.managers.effects.EmptyEffect;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.ArrayList;

@PlayerContext
@Injection
public class PianoViewGUI extends ChestUI {

    private final ColorPickerGui colorPickerGui;
    private final FluentTranslator lang;
    private final PianoViewButtonsFactory pianoViewButtons;
    private final BenchViewGui benchViewGui;
    private final MidiPlayerGui midiPlayerGui;
    private final KeyboardGui keyboardGui;
    private final ObserverBag<PianoSkin> skinObserver;
    private final ObserverBag<EffectInvoker> effectObserver;
    private final ObserverBag<PianoSound> soundObserver;
    @Getter
    private final List<CheckBox> checkBoxes;
    private PianoDataObserver pianoDataObserver;
    private Piano piano;

    @Inject
    public PianoViewGUI(BenchViewGui benchViewGui,
                        MidiPlayerGui midiPlayerGui,
                        KeyboardGui keyboardGui,
                        PianoViewButtonsFactory buttons,
                        ColorPickerGui colorPickerGui,
                        FluentTranslator lang) {
        super("Piano", 5);
        this.midiPlayerGui = midiPlayerGui;
        this.benchViewGui = benchViewGui;
        this.pianoViewButtons = buttons;
        this.lang = lang;

        skinObserver = new ObserverBag<>(PianoSkin.defaultSkin());
        effectObserver = new ObserverBag<>(new EmptyEffect());
        soundObserver = new ObserverBag<>(new PianoSound());
        checkBoxes = new ArrayList<>();
        this.colorPickerGui = colorPickerGui;
        this.keyboardGui = keyboardGui;
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoObserver();
        this.piano = piano;

        checkBoxes.clear();
        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.PEDAL_ACTIVE.TITLE),
                pianoDataObserver.getPedalsSettings().getPedalInteraction(),
                PluginPermissions.GUI.PIANO.SETTINGS.PEDAL_PRESSING_ACTIVE));

        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.DESKTOP_CLIENT_ACTIVE.TITLE),
                pianoDataObserver.getDesktopClientAllowed(),
                PluginPermissions.GUI.PIANO.SETTINGS.DESKTOP_APP_ACTIVE));

        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.DETECT_KEY_ACTIVE.TITLE),
                pianoDataObserver.getInteractiveKeyboard(),
                PluginPermissions.GUI.PIANO.SETTINGS.KEYBOARD_PRESSING_ACTIVE));

        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.PIANIST_ACTIVE.TITLE),
                pianoDataObserver.getShowPianist(),
                PluginPermissions.GUI.PIANO.SETTINGS.PIANIST_ACTIVE));
        setTitlePrimary(pianoDataObserver.getPianoData().getName());
        open(player);
    }

    @Override
    protected void onOpen(Player player) {
        skinObserver.set(piano.getSkinManager().getCurrent());
        effectObserver.set(piano.getEffectManager().getCurrent());
        soundObserver.set(piano.getSoundsManager().getCurrent());
    }

    @Override
    protected void onInitialize() {
        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        benchViewGui.setParent(this);
        midiPlayerGui.setParent(this);
        keyboardGui.setParent(this);
        colorPickerGui.setParent(this);

        pianoViewButtons.keyboardButton()
                .setOnLeftClick((p,b)->
                {
                    keyboardGui.open(p, piano.getPianoObserver().getPianoData().getKeyboardSettings());
                })
                .build(this);

        pianoViewButtons.teleportButton()
                .setOnLeftClick(this::onTeleport)
                .build(this);

        pianoViewButtons.tokenButton()
                .setOnLeftClick(this::onTokenGeneration)
                .build(this);

        pianoViewButtons.renameButton()
                .setOnLeftClick(this::onRename)
                .build(this);

        pianoViewButtons.pianoClearButton()
                .setOnLeftClick(this::onPianoClear)
                .build(this);

        pianoViewButtons.benchButton()
                .setOnLeftClick((player, button) ->
                {
                    benchViewGui.open(player, piano);
                }).build(this);

        pianoViewButtons.midiPlayerButton()
                .setOnLeftClick((player, button) ->
                {
                    midiPlayerGui.open(player, piano);
                }).build(this);


        pianoViewButtons
                .pianoVolumeButton(() -> pianoDataObserver.getVolume())
                .build(this);

        pianoViewButtons.pianoOptionsButton(this, this::getCheckBoxes)
                .build(this);

        pianoViewButtons.pianoSkinSelectButton(
                        () -> pianoDataObserver.getSkinName(),
                        () -> piano.getSkinManager().getItems(),
                        skinObserver.getObserver())

                .setOnShiftClick(this::onSkinColor)
                .build(this);

        pianoViewButtons.pianoParticleEffectSelectButton(
                        () -> pianoDataObserver.getEffectName(),
                        () -> piano.getEffectManager().getItems(),
                        effectObserver.getObserver())
                .build(this);

        pianoViewButtons.pianoSoundsSelectButton(
                        () -> pianoDataObserver.getSoundName(),
                        () -> piano.getSoundsManager().getItems(),
                        soundObserver.getObserver())
                .build(this);


        pianoViewButtons.backButton(this).build(this);
    }

    private void onRename(Player player, ButtonUI buttonUI) {
        this.close();
        FluentMessage.message()
                .color(org.bukkit.ChatColor.AQUA)
                .bold()
                .inBrackets(lang.get(PluginTranslations.GENERAL.INFO))
                .space()
                .reset()
                .text(lang.get(PluginTranslations.GUI.PIANO.RENAME.MESSAGE_1)).send(player);
        EventsListenerInventoryUI.registerTextInput(player, s ->
        {
            pianoDataObserver.getPianoData().setName(s);
            setTitlePrimary(s);
            this.open(player);
        });
    }

    private void onTeleport(Player player, ButtonUI buttonUI) {
        piano.teleportPlayer(player);
    }

    private void onPianoClear(Player player, ButtonUI buttonUI) {
        piano.reset();
        FluentApi.messages()
                .chat()
                .info()
                .text(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_CLEAR))
                .send(player);
    }

    private void onSkinColor(Player player,ButtonUI buttonUI)
    {
        var skin = piano.getSkinManager().getCurrent();
        if(skin.getItemStack().getType() == Material.AIR)
        {
            return;
        }
        colorPickerGui.setItemStack(skin.getItemStack());
        colorPickerGui.onContentPicked((player1, button1) ->
        {
            var data = button1.<ColorInfo>getDataContext();
            piano.setColor(data.getColor());
            open(player,piano);
        });
        colorPickerGui.open(player);
    }

    private void onTokenGeneration(Player player, ButtonUI buttonUI) {
        piano.getTokenGenerator().generateAndSend(player);
        close();
    }


}


