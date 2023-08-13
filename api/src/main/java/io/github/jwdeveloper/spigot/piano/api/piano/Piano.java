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

package io.github.jwdeveloper.spigot.piano.api.piano;


import io.github.jwdeveloper.spigot.piano.api.data.PianoData;
import io.github.jwdeveloper.spigot.piano.api.managers.effects.EffectManager;
import io.github.jwdeveloper.spigot.piano.api.managers.skins.SkinManager;
import io.github.jwdeveloper.spigot.piano.api.managers.sounds.SoundsManager;
import io.github.jwdeveloper.spigot.piano.api.piano.common.*;
import io.github.jwdeveloper.spigot.piano.api.piano.keyboard.Keyboard;
import io.github.jwdeveloper.spigot.piano.api.piano.pedals.PedalGroup;
import io.github.jwdeveloper.spigot.piano.api.piano.token.TokenGenerator;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Piano extends Teleportable, Interactable, Visiable, GuiViewer, Resetable, Colorable {


    TokenGenerator getTokenGenerator();
    SoundsManager getSoundsManager();
    SkinManager getSkinManager();
    EffectManager getEffectManager();

    Bench getBench();

    PedalGroup getPedals();

    Keyboard getKeyboard();

    MidiPlayer getMidiPlayer();

    void setColor(Color color);

    //PianoDataObserver getPianoObserver();
    PianoData getPianoData();

    void setVisible(boolean isVisible);

    void setVolume(int volume);

    void teleportPlayer(Player player);

    boolean isLocationAtPianoRange(Location location);

    boolean openGui(Player player);

    void triggerNote(int pressed, int midiIndex, int velocity, int trackId);

    void triggerPedal(int isPressed, int midiIndex, int velocity);
}
