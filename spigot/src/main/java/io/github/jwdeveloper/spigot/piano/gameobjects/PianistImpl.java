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


import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.ArmorStandModel;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObject;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginModels;
import io.github.jwdeveloper.spigot.piano.api.data.PianoData;

public class PianistImpl extends GameObject {

    private final PianoData pianoData;

    private ArmorStandModel pianist;
    private ArmorStandModel pianistHands;
    private ArmorStandModel pianistHead;

    private SimpleTaskTimer handsTask;
    private SimpleTaskTimer headTask;

    public PianistImpl(PianoData pianoData) {
        this.pianoData = pianoData;
    }


    @Override
    public void onCreate() {
        location = location.clone().add(0,0,0.1);
        pianist = addGameComponent(ArmorStandModel.class);
        pianist.setOnCreated(armorStandModel ->
        {
            armorStandModel.setItemStack(PianoPluginModels.PIANIST_BODY.toItemStack());
            armorStandModel.setCustomModelId(PianoPluginModels.PIANIST_BODY.id());
            armorStandModel.setId(PianoPluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
            armorStandModel.setLocation(location.clone());
        });

        pianistHands = addGameComponent(ArmorStandModel.class);
        pianistHands.setOnCreated(armorStandModel ->
        {
            armorStandModel.setItemStack(PianoPluginModels.PIANIST_HANDS.toItemStack());
            armorStandModel.setCustomModelId(PianoPluginModels.PIANIST_HANDS.id());
            armorStandModel.setId(PianoPluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
            armorStandModel.setLocation(location.clone());

            handsTask = FluentApi.tasks().taskTimer(1, (iteration, task) ->
            {
                var value = Math.sin(Math.toRadians(iteration * 10));
                value = value * 0.1f;
                var pose = armorStandModel.getArmorStand().getHeadPose().setX(value);
                armorStandModel.getArmorStand().setHeadPose(pose);
                armorStandModel.updateModel();
            });
        });


        pianistHead = addGameComponent(ArmorStandModel.class);
        pianistHead.setOnCreated(armorStandModel ->
        {
            armorStandModel.setItemStack(PianoPluginModels.PIANIST_HEAD.toItemStack());
            armorStandModel.setCustomModelId(PianoPluginModels.PIANIST_HEAD.id());
            armorStandModel.setId(PianoPluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
            armorStandModel.setLocation(location.clone());

            headTask = FluentApi.tasks().taskTimer(1, (iteration, task) ->
            {
                var value = Math.sin(Math.toRadians(iteration * 6));
                value = value * 0.2f;
                var pose = armorStandModel.getArmorStand().getHeadPose().setY(value);
                armorStandModel.getArmorStand().setHeadPose(pose);
                armorStandModel.updateModel();
            });
        });
    }

    @Override
    public void setVisible(boolean visible) {
        handsTask.stop();
        headTask.stop();
        if(visible)
        {
            handsTask.run();
            headTask.run();
        }
        super.setVisible(visible);
    }

    public void refresh() {
        pianist.refresh();
        pianistHands.refresh();
        pianistHead.refresh();

        setVisible(pianoData.getShowPianist());
    }
}