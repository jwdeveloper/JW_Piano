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

package jw.piano.spigot.piano.keyboard;

import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.hitbox.InteractiveHitBox;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.ArmorStandModel;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObject;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.data.models.keyboard.KeyboardSettings;
import jw.piano.api.events.PianoKeyPressEvent;
import jw.piano.api.managers.effects.EffectManager;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.api.piano.keyboard.PianoKey;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;


public class PianoKeyImpl extends GameObject implements PianoKey {
    private final int CLICK_TICKS = 10;
    private final int RADIOUS = 10;
    private final boolean isBlack;
    @Getter
    private final int midiIndex;
    private final SoundsManager soundsManager;
    private final EffectManager effectManager;
    private final PianoData pianoData;
    private final KeyboardSettings keyboardSettings;
    private ArmorStandModel armorStandModel;
    private InteractiveHitBox hitBox;
    private final String name;
    private Location keyModelLocation;

    private Color lastColor = Color.WHITE;

    public PianoKeyImpl(
            PianoData pianoData,
            SoundsManager soundPlayerFactory,
            EffectManager effectManager,
            boolean isBlack,
            int index) {
        this.soundsManager = soundPlayerFactory;
        this.effectManager = effectManager;
        this.isBlack = isBlack;
        this.midiIndex = index;
        this.pianoData = pianoData;
        this.name = getNoteName(index);
        keyboardSettings = pianoData.getKeyboardSettings();
    }


    @Override
    public void onCreate() {
        var min = new Vector(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08);
        var max = new Vector(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08);
        keyModelLocation = location.clone().add(0,-1.5,0);
        hitBox = new InteractiveHitBox(keyModelLocation, min, max);
        armorStandModel = addGameComponent(ArmorStandModel.class);
        armorStandModel.setOnCreated(model ->
        {
            armorStandModel.setItemStack(PluginModels.PIANO_KEY.toItemStack());
            armorStandModel.getArmorStand().setSmall(true);
            armorStandModel.setId(PluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
            release();
        });

    }


    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        if (!hitBox.isCollider(event.getEyeLocation(), RADIOUS)) {
            return false;
        }
        var keyPressEvent = new PianoKeyPressEvent(
                event.getPlayer(),
                this,
                event.getPiano(),
                event.isLeftClick());
        Bukkit.getPluginManager().callEvent(keyPressEvent);
        if(keyPressEvent.isCancelled())
        {
            return true;
        }
        FluentApi.tasks().taskTimer(CLICK_TICKS, (iteration, task) ->
                {
                    press();
                }).stopAfterIterations(1)
                .onStop(task ->
                {
                    release();
                }).run();
        return true;
    }

    @Override
    public void press() {
        press(100);
    }

    @Override
    public void press(int velocity) {
        press(velocity, 0);
    }

    @Override
    public void press(int velocity, int trackId) {
        final var soundLevel = ((pianoData.getVolume() / 100.0f) * (velocity)) / 50.0f;
        soundsManager.play(keyModelLocation, midiIndex, soundLevel, pianoData.getPedalsSettings().getSustainPressed());

        lastColor = getColorByTrackId(trackId);
        effectManager.getCurrent().onNote(this,keyModelLocation, midiIndex, velocity, lastColor, true);
        armorStandModel.setColor(lastColor);

        if (isBlack) {

            armorStandModel.setCustomModelId(PluginModels.PIANO_BLACK_KEY_DOWN.id());
        } else {
            armorStandModel.setCustomModelId(PluginModels.PIANO_KEY_DOWN.id());
        }
    }


    @Override
    public void release() {


        effectManager.getCurrent().onNote(this,keyModelLocation, midiIndex, 0, lastColor, false);
        if (isBlack) {
            armorStandModel.setColor(black());
            armorStandModel.setCustomModelId(PluginModels.PIANO_BLACK_KEY.id());
        } else {
            armorStandModel.setColor(Color.WHITE);
            armorStandModel.setCustomModelId(PluginModels.PIANO_KEY.id());
        }

    }


    public Color getColorByTrackId(int trackId)
    {
        final var midiTracks = keyboardSettings.getMidiTrackColors();

        for(var i =0;i<midiTracks.size();i++)
        {
            if(midiTracks.get(i).getTrackId() == trackId)
            {
                return midiTracks.get(i).getColor();
            }
        }
        return keyboardSettings.getDefaultColor();
    }

    public boolean isWhite() {
        return !isBlack;
    }


    @Override
    public void refresh() {
        armorStandModel.refresh();
        if (isBlack) {
            armorStandModel.setColor(black());
            armorStandModel.setCustomModelId(PluginModels.PIANO_BLACK_KEY.id());
        } else {
            armorStandModel.setColor(Color.WHITE);
            armorStandModel.setCustomModelId(PluginModels.PIANO_KEY.id());
        }
    }

    @Override
    public Location getkeyLocation() {
        return keyModelLocation;
    }

    @Override
    public int compareTo(Object o) {
        return isBlack ? 0 : 1;
    }


    public static Color black()
    {
        return Color.fromRGB(22,22,22);
    }

    private static String getNoteName(int noteNumber){
        noteNumber -= 21;
        //String[] notes = new String[] {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
        var notes = new String[]{"\u1000", "\u1001", "\u1002", "\u1003", "\u1004", "\u1005", "\u1006", "\u1007", "\u1008", "\u1009", "\u1010", "\u1011"};
        return notes[noteNumber % 12];
    }

    public void nameVisible(boolean nameVisible)
    {
        if(nameVisible)
        {
            armorStandModel.getArmorStand().setCustomNameVisible(true);
            armorStandModel.getArmorStand().setCustomName(name);
        }
        else
        {
            armorStandModel.setNameWithoutUpdate(StringUtils.EMPTY);
            armorStandModel.getArmorStand().setCustomNameVisible(false);
        }

    }
}
