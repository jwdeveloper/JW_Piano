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

package jw.piano.spigot.piano.pedals;

import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.fluent.api.spigot.permissions.implementation.PermissionsUtility;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.pedals.Pedal;
import jw.piano.api.piano.pedals.PedalGroup;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PedalGroupImpl extends GameObject implements PedalGroup {

    private final Pedal[] pedals;
    private final PianoData pianoData;

    public PedalGroupImpl(PianoData pianoData) {
        pedals = new Pedal[3];
        this.pianoData = pianoData;
    }


    @Override
    public void onCreate() {
        for (int i = 0; i < 3; i++) {
            final var pedalLocation = location.clone().add(-0.4 + (i * 0.20), -0.1, 0.1f);
            final var pedal = new PedalImpl(pianoData);
            pedal.setLocation(pedalLocation);
            pedals[i] = addGameComponent(pedal);
        }
    }

    @Override
    public void teleport(Location location) {

    }

    @Override
    public Pedal[] getPedals() {
        return new Pedal[0];
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        return false;
    }


    public boolean triggerSustainPedal() {
        if (!pianoData.getPedalsSettings().getPedalInteraction()) {
            return false;
        }

        if (!pianoData.getPedalsSettings().getSustainPressed()) {

            triggerPedal(1, 64, 100);
        } else {
            triggerPedal(0, 64, 0);
        }
        return true;
    }

    @Override
    public boolean triggerSustainPedal(Player player) {
        if (!PermissionsUtility.hasOnePermission(player, PluginPermissions.PIANO.KEYBOARD.USE)) {
            return false;
        }
        return triggerSustainPedal();
    }

    @Override
    public void refresh() {
        for (var pedal : pedals) {
            pedal.refresh();
        }
    }

    @Override
    public void triggerPedal(int isPressed, int midiIndex, int velocity) {
        final var pedal = switch (midiIndex) {
            case 64 -> pedals[2];
            case 65 -> pedals[1];
            case 67 -> pedals[0];
            default -> null;
        };
        if (pedal == null)
            return;
        if (isPressed == PluginConsts.PRESSED_CODE)
            pedal.press(velocity);
        else
            pedal.release();

        if (midiIndex == 64) {
            pianoData.getPedalsSettings().setSustainPressed(!pianoData.getPedalsSettings().getSustainPressed());
        }
    }

    public boolean isSustainPressed() {
        return pedals[2].isPressed();
    }

    @Override
    public void setColor(Color color) {
        for (var pedal : pedals) {
            if (pedal == null) {
                continue;
            }
            pedal.setColor(color);
        }
    }
}
