package jw.piano.spigot.piano.bench;

import jw.fluent.api.spigot.gameobjects.implementation.ArmorStandModel;
import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.fluent.api.spigot.gui.armorstand_gui.implementation.gui.interactive.HitBoxDisplay;
import jw.fluent.api.utilites.math.InteractiveHitBox;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.data.enums.PianoKeysConst;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.piano.Bench;
import jw.piano.api.piano.Piano;
import jw.piano.spigot.gui.bench.BenchViewGui;
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
            armorStandModel.setItemStack(PluginConsts.ITEMSTACK());
            armorStandModel.setCustomModelId(PianoKeysConst.BENCH.getId());
            armorStandModel.setLocation(location);
        });
        var xBox = 0.8;
        var zBox = 0.4;
        var min = new Vector(-xBox, -0.61, -zBox);
        var max = new Vector(xBox, 0.3, zBox);
        hitBox = new InteractiveHitBox(location, min, max);
        display= new HitBoxDisplay(hitBox);
        display.show(0.4f);
    }

    private HitBoxDisplay display;

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

        return event.isLeftClick() ? openGui(event.getPlayer()) : sitPlayer(event.getPlayer());
    }


    @Override
    public void setVisible(boolean visible) {
        active = visible;
        model.setVisible(visible);
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
        FluentApi.tasks().taskLater((a, b) ->
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
}
