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

import jw.fluent.api.spigot.gameobjects.implementation.ArmorStandModel;
import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.pedals.Pedal;
import org.bukkit.Color;

public class PedalImpl extends GameObject implements Pedal
{
    private boolean isPressed;
    private ArmorStandModel armorStandModel;

    private PianoData pianoData;

    public PedalImpl(PianoData data)
    {
        this.pianoData = data;
    }

    @Override
    public void onCreate() {
        armorStandModel = addGameComponent(ArmorStandModel.class);
        armorStandModel.setOnCreated(model ->
        {
            armorStandModel.setItemStack(PluginModels.PIANO_PEDAL.toItemStack());
            armorStandModel.getArmorStand().setSmall(true);
            armorStandModel.setId(PluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
            release();
        });
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event)
    {
        return false;
    }


    @Override
    public void press(int velocity) {
        armorStandModel.setCustomModelId(PluginModels.PIANO_PEDAL_DOWN.id());
        isPressed = true;
    }

    @Override
    public void press() {
        press(100);
    }

    @Override
    public void release() {
        armorStandModel.setCustomModelId(PluginModels.PIANO_PEDAL.id());
        isPressed = false;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public void refresh() {
        armorStandModel.refresh();
    }

    @Override
    public void setColor(Color color) {
        armorStandModel.setColor(color);
    }
}
