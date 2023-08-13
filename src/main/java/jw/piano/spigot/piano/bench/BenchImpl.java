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

package jw.piano.spigot.piano.bench;

import io.github.jwdeveloper.ff.core.hitbox.HitBoxDisplay;
import io.github.jwdeveloper.ff.core.hitbox.InteractiveHitBox;
import io.github.jwdeveloper.ff.core.spigot.permissions.implementation.PermissionsUtility;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.ArmorStandModel;
import io.github.jwdeveloper.ff.extension.gameobject.implementation.GameObject;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.piano.Bench;
import jw.piano.api.piano.Piano;
import jw.piano.spigot.gui.bench.BenchViewGui;
import jw.piano.spigot.piano.PianistImpl;
import lombok.Getter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class BenchImpl extends GameObject implements Bench {

    private final Piano piano;
    private ArmorStandModel model;
    private InteractiveHitBox hitBox;
    private Location origin;
    private BenchMovingHandler benchMovingHandler;

    @Getter
    private PianistImpl pianist;

    public BenchImpl(Piano piano) {
        this.piano = piano;
    }

    @Override
    public void onCreate() {
        origin = location.clone();
        benchMovingHandler = new BenchMovingHandler(origin);
        benchMovingHandler.setOnLocationUpdated(this::teleport);
        location = benchMovingHandler.reset();
        model = addGameComponent(ArmorStandModel.class);
        model.setOnCreated(armorStandModel ->
        {
            armorStandModel.getArmorStand().setSmall(true);
            armorStandModel.setItemStack(PluginModels.BENCH.toItemStack());
            armorStandModel.setCustomModelId(PluginModels.BENCH.id());
            armorStandModel.setId(PluginConsts.PIANO_NAMESPACE, piano.getPianoObserver().getPianoData().getUuid());
            armorStandModel.setLocation(location);
        });


        var xBox = 0.8;
        var zBox = 0.4;
        var min = new Vector(-xBox, -0.61, -zBox);
        var max = new Vector(xBox, 0.3, zBox);
        hitBox = new InteractiveHitBox(location, min, max);
        display = new HitBoxDisplay(hitBox);
        pianist = addGameComponent(new PianistImpl(piano.getPianoObserver().getPianoData()));
    }

    private HitBoxDisplay display;

    @Override
    public void onCreated() {
        model.refresh();
        pianist.setLocation(model.getLocation());
        pianist.refresh();
    }

    @Override
    public void refresh() {
        model.refresh();
        pianist.refresh();
    }

    @Override
    public void onDestroy() {
        benchMovingHandler.destroy();
    }

    public void move(BenchMove benchMove) {
        benchMovingHandler.move(benchMove, location.clone());
    }


    @Override
    public boolean sitPlayer(Player player) {
        if (model.getArmorStand().getPassengers().size() > 0) {
            return false;
        }
        model.getArmorStand().addPassenger(player);
        return true;
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {

        if (!active) {
            return false;
        }
        if (!hitBox.isCollider(event.getEyeLocation(), 10)) {
            return false;
        }
        if(!PermissionsUtility.hasOnePermission(event.getPlayer(), PluginPermissions.PIANO.BENCH.USE))
        {
            return false;
        }

        return event.isLeftClick() ? openGui(event.getPlayer()) : sitPlayer(event.getPlayer());
    }


    @Override
    public void setVisible(boolean visible) {
        active = visible;
        if (visible) {
            model.setItemStack(PluginModels.BENCH.toItemStack());
            model.setCustomModelId(PluginModels.BENCH.id());
        } else {
            model.setItemStack(null);
        }

    }

    @Override
    public void teleport(Location location) {
        this.location = location;
        var passengers = new ArrayList<Entity>();
        var armorStand = model.getArmorStand();
        for (var Passengers : armorStand.getPassengers()) {
            armorStand.removePassenger(Passengers);
            passengers.add(Passengers);
        }
        armorStand.teleport(location);
        FluentApi.tasks().taskLater(() ->
        {
            for (var Passengers : passengers) {
                armorStand.addPassenger(Passengers);
            }
        }, 2);
        hitBox.updateOrigin(location);
        display.show(0.4f);
    }

    public boolean openGui(Player player) {
        var gui = FluentApi.playerContext().find(BenchViewGui.class, player);
        gui.open(player, piano);
        return true;
    }

    @Override
    public void reset() {
        teleport(benchMovingHandler.reset());
    }

    @Override
    public void setColor(Color color) {
        model.setColor(color);
    }
}
