package jw.piano.spigot.gameobjects.models;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.models.PianoSkin;
import jw.piano.data.enums.PianoKeysConst;
import jw.piano.factory.ArmorStandFactory;
import jw.fluent.api.spigot.gameobjects.api.GameObject;
import jw.fluent.api.utilites.math.collistions.HitBox;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class BenchGameObject extends GameObject {

    private ArmorStand armorStand;
    private HitBox hitBox;
    private boolean active;
    private PianoSkin benchModel;
    private final String guid;
    private final ArmorStandFactory armorStandFactory;

    private Location startLocation;

    //y 0.800000011920929
    public BenchGameObject(String guid, ArmorStandFactory armorStandFactory) {
        this.guid = guid;
        this.armorStandFactory = armorStandFactory;
    }

    public void updateHitBox() {
        var xBox = 0.8;
        var zBox = 0.4;

        var min = location.clone().add(-xBox, -0.61, -zBox);
        var max = location.clone().add(xBox, 0.3, zBox);
        hitBox.update(min, max);
    }

    public void resetLocation()
    {
        location =  startLocation.clone().add(-0.2, 0.61, 1.2);
        onLocationUpdated();
    }

    @Override
    public void onCreated() {
        startLocation = location;
        location =  location.clone().add(-0.2, 0.61, 1.2);
        benchModel = new PianoSkin(PianoKeysConst.BENCH.getId(), "bench");
        hitBox = new HitBox(location, location);
        updateHitBox();

        active = true;
        armorStand = armorStandFactory.create(location, guid);
        armorStand.setSmall(true);
        armorStand.setHelmet(benchModel.getItemStack());
        onLocationUpdated();
    }

    @Override
    public void onDestroy() {
        active = false;
        armorStand.remove();
    }

    @Override
    public void onLocationUpdated() {
        var passagers = new ArrayList<Entity>();
        for (var p : this.armorStand.getPassengers()) {
            this.armorStand.removePassenger(p);
            passagers.add(p);
        }
        this.armorStand.teleport(this.location);
        FluentApi.tasks().taskLater((a, b) ->
        {
            for (var p : passagers) {
                this.armorStand.addPassenger(p);
            }
        }, 2);
    }

    public boolean onPlayerClick(Player player) {
        if (!active) {
            return false;
        }

        if (armorStand.getPassengers().size() > 0) {
            return false;
        }
        if (!hitBox.isCollider(player.getEyeLocation(), 10)) {
            return false;
        }
        armorStand.addPassenger(player);
        return true;
    }

    public void setState(boolean state) {
        if (active == state)
            return;

        if (state) {
            create(location);
        } else {
            destroy();
        }

    }


}
