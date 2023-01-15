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
import jw.fluent.api.spigot.gui.fluent_ui.FluentButtonUIBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox.CheckBox;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.gui.PianoViewButtons;
import jw.piano.api.managers.effects.EffectInvoker;

import java.util.List;
import java.util.function.Supplier;

@Injection
public class PianoViewIconsFactory implements PianoViewButtons {

    private final PianoViewButtonsFactory factory;


    @Inject
    public PianoViewIconsFactory(PianoViewButtonsFactory factory) {
        this.factory = factory;
    }


    @Override
    public FluentButtonUIBuilder backButton(InventoryUI pianoUI) {
        return factory.backButton(pianoUI).setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder keyboardButton() {
        return factory.keyboardButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder midiPlayerButton() {
        return factory.midiPlayerButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder pianoClearButton() {
        return factory.pianoClearButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder benchButton() {
        return factory.benchButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder renameButton() {
        return factory.renameButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder teleportButton() {
        return factory.teleportButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder tokenButton() {
        return factory.tokenButton().setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder pianoVolumeButton(Supplier<Observer<Integer>> observerSupplier) {
        return factory.pianoVolumeButton(observerSupplier).setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder pianoParticleEffectSelectButton(Supplier<Observer<String>> observerSupplier, Supplier<List<EffectInvoker>> effectSupplier, Observer<EffectInvoker> effectIndexSupplier) {
        return factory.pianoParticleEffectSelectButton(
                        observerSupplier,
                        effectSupplier,
                        effectIndexSupplier)
                .setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder pianoSkinSelectButton(Supplier<Observer<String>> observerSupplier,
                                                       Supplier<List<PianoSkin>> skinsSupplier,
                                                       Observer<PianoSkin> skinObserver) {
        return factory.pianoSkinSelectButton(observerSupplier, skinsSupplier, skinObserver).setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder pianoSoundsSelectButton(Supplier<Observer<String>> observerSupplier,
                                                         Supplier<List<PianoSound>> effectSupplier,
                                                         Observer<PianoSound> effectIndexSupplier) {
        return factory.pianoSoundsSelectButton(
                observerSupplier,
                effectSupplier,
                effectIndexSupplier).setMaterial(PluginConsts.MATERIAL, 1);
    }

    @Override
    public FluentButtonUIBuilder pianoOptionsButton(InventoryUI inventoryUI, Supplier<List<CheckBox>> values) {
        return factory.pianoOptionsButton(inventoryUI, values).setMaterial(PluginConsts.MATERIAL, 1);
    }
}
