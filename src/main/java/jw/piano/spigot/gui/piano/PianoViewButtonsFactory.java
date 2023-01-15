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

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentButtonUIBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox.CheckBox;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.plugin.api.FluentTranslations;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.gui.PianoViewButtons;
import jw.piano.api.managers.effects.EffectInvoker;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Supplier;

@PlayerContext
@Injection()
public class PianoViewButtonsFactory implements PianoViewButtons {
    private final FluentTranslator lang;
    private final FluentChestUI fluentUi;

    @Inject
    public PianoViewButtonsFactory(FluentTranslator translator,
                                   FluentChestUI buttonUIBuilder) {
        this.lang = translator;
        this.fluentUi = buttonUIBuilder;
    }

    @Override
    public FluentButtonUIBuilder backButton(InventoryUI pianoUI) {
        return fluentUi.buttonFactory()
                .back(pianoUI, null);
    }

    @Override
    public FluentButtonUIBuilder keyboardButton() {

        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.KEYBOARD.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.KEYBOARD.DESC));
                })
                .setMaterial(PluginConsts.MATERIAL, PluginModels.PIANO_KEY.id(), Color.WHITE)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.BASE)
                .setLocation(2, 6);
    }

    @Override
    public FluentButtonUIBuilder midiPlayerButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.MIDI_PLAYER.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.MIDI_PLAYER.DESC));
                })
                .setMaterial(Material.JUKEBOX)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.BASE)
                .setLocation(1, 2);
    }

    @Override
    public FluentButtonUIBuilder pianoClearButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.CLEAR.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_1));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_2));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_3));
                })
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermissions.GUI.PIANO.CLEAR)
                .setLocation(0, 8);
    }


    @Override
    public FluentButtonUIBuilder benchButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.BENCH.TITLE));
                    buttonDescriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.BENCH.TITLE));
                })
                .setMaterial(PluginConsts.MATERIAL, PluginModels.BENCH.id())
                .setPermissions(PluginPermissions.GUI.BENCH.BASE)
                .setLocation(2, 4);
    }

    @Override
    public FluentButtonUIBuilder renameButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.RENAME.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.RENAME.DESC));
                })
                .setMaterial(Material.NAME_TAG)
                .setPermissions(PluginPermissions.GUI.PIANO.RENAME)
                .setLocation(0, 2);
    }

    @Override
    public FluentButtonUIBuilder teleportButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.TELEPORT.TITLE));
                })
                .setMaterial(Material.ENDER_PEARL)
                .setPermissions(PluginPermissions.GUI.PIANO.TELEPORT)
                .setLocation(2, 2);
    }

    @Override
    public FluentButtonUIBuilder tokenButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.TOKEN.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.TOKEN.DESC));
                })
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setPermissions(PluginPermissions.GUI.PIANO.GENERATE_TOKEN)
                .setLocation(1, 4);
    }

    @Override
    public FluentButtonUIBuilder pianoVolumeButton(Supplier<Observer<Integer>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeBarInt(observerSupplier, options ->
                {
                    options.setYield(5);
                    options.setMaximum(100);
                    options.setMinimum(0);
                })
                .setMaterial(Material.BELL)
                .setPermissions(PluginPermissions.GUI.PIANO.VOLUME)
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO.VOLUME.TITLE));
                })
                .setLocation(1, 6);
    }


    @Override
    public FluentButtonUIBuilder pianoParticleEffectSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<EffectInvoker>> effectSupplier,
            Observer<EffectInvoker> effectIndexSupplier) {

        return fluentUi.buttonFactory()
                .observeList(() -> effectIndexSupplier,
                        effectSupplier,
                        options ->
                        {
                            options.setOnNameMapping(EffectInvoker::getName);
                            options.setOnSelectionChanged(event ->
                            {
                                final var value = event.data().getName();
                                final var observer = observerSupplier.get();
                                observer.set(value);
                                event.buttonUI().setMaterial(event.data().getIcon());
                            });
                        }
                )
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PluginPermissions.GUI.PIANO.EFFECT)
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO.EFFECT.TITLE));
                })
                .setLocation(3, 2);
    }

    @Override
    public FluentButtonUIBuilder pianoSkinSelectButton(Supplier<Observer<String>> observerSupplier,
                                                       Supplier<List<PianoSkin>> skinsSupplier,
                                                       Observer<PianoSkin> skinObserver) {
        return fluentUi.buttonFactory()
                .observeList(() -> skinObserver, skinsSupplier, options ->
                {
                    options.setOnNameMapping(PianoSkin::getName);
                    options.setOnSelectionChanged(event ->
                    {
                        final var value = event.data().getName();
                        final var observer = observerSupplier.get();
                        observer.set(value);
                        if (value.equals("none")) {
                            event.buttonUI().setMaterial(Material.NOTE_BLOCK);
                            return;
                        }
                        event.buttonUI().setCustomMaterial(PluginConsts.MATERIAL, event.data().getCustomModelId());
                    });
                })
                .setDescription(config ->
                {
                    config.setTitle(lang.get(PluginTranslations.GUI.PIANO.SKIN.TITLE));
                    config.setOnShiftClick(lang.get(FluentTranslations.COLOR_PICKER.CHANGE_COLOR));
                })
                .setPermissions(PluginPermissions.GUI.PIANO.SKIN)
                .setLocation(3, 4);
    }


    @Override
    public FluentButtonUIBuilder pianoSoundsSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<PianoSound>> effectSupplier,
            Observer<PianoSound> effectIndexSupplier) {

        return fluentUi.buttonFactory()
                .observeList(() -> effectIndexSupplier,
                        effectSupplier,
                        options ->
                        {
                            options.setOnNameMapping(PianoSound::getName);
                            options.setOnSelectionChanged(event ->
                            {
                                final var value = event.data().getName();
                                final var observer = observerSupplier.get();
                                observer.set(value);
                            });
                        }
                )
                .setMaterial(Material.MUSIC_DISC_WARD)
                .setPermissions(PluginPermissions.GUI.PIANO.SOUND)
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO.SOUNDS.TITLE));
                })
                .setLocation(3, 6);
    }

    @Override
    public FluentButtonUIBuilder pianoOptionsButton(InventoryUI inventoryUI, Supplier<List<CheckBox>> values) {
        return fluentUi.buttonFactory()
                .observeCheckBoxList(inventoryUI, values, checkBoxListNotifierOptions ->
                {

                })
                .setPermissions(PluginPermissions.GUI.PIANO.SETTINGS.BASE)
                .setMaterial(Material.REPEATER)
                .setLocation(0, 1)
                .setDescription(descriptionInfoBuilder ->
                {
                    descriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.PIANO_OPTIONS.TITLE));
                });
    }
}
