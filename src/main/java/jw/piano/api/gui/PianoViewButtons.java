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

package jw.piano.api.gui;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.spigot.gui.fluent_ui.FluentButtonUIBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox.CheckBox;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.effects.EffectInvoker;

import java.util.List;
import java.util.function.Supplier;

public interface PianoViewButtons {
    FluentButtonUIBuilder backButton(InventoryUI pianoUI);

    FluentButtonUIBuilder keyboardButton();

    FluentButtonUIBuilder midiPlayerButton();

    FluentButtonUIBuilder pianoClearButton();

    FluentButtonUIBuilder benchButton();

    FluentButtonUIBuilder renameButton();

    FluentButtonUIBuilder teleportButton();

    FluentButtonUIBuilder tokenButton();

    FluentButtonUIBuilder pianoVolumeButton(Supplier<Observer<Integer>> observerSupplier);

    FluentButtonUIBuilder pianoParticleEffectSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<EffectInvoker>> effectSupplier,
            Observer<EffectInvoker> effectIndexSupplier);

    FluentButtonUIBuilder pianoSkinSelectButton(Supplier<Observer<String>> observerSupplier,
                                                Supplier<List<PianoSkin>> skinsSupplier,
                                                Observer<PianoSkin> skinObserver);

    FluentButtonUIBuilder pianoSoundsSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<PianoSound>> effectSupplier,
            Observer<PianoSound> effectIndexSupplier);

    FluentButtonUIBuilder pianoOptionsButton(InventoryUI inventoryUI, Supplier<List<CheckBox>> values);
}
