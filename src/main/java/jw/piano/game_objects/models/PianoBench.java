package jw.piano.game_objects.models;

import jw.piano.data.PianoSkin;
import jw.piano.enums.PianoKeysConst;
import jw.piano.game_objects.factories.ArmorStandFactory;
import jw.spigot_fluent_api.fluent_game_object.GameObject;
import jw.spigot_fluent_api.fluent_gui.scroll_selector.ScrollSelector;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.utilites.math.collistions.HitBox;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PianoBench extends GameObject {

    private ArmorStand armorStand;
    private HitBox hitBox;
    private Location location;
    private boolean active;
    private PianoSkin benchModel;

    //y 0.800000011920929
    public PianoBench(Location loc) {
        location = loc.clone().add(-0.2, 0.61, 1.2);
        var min = location.clone().add(-0.7, -0.61, -0.3);
        var max = location.clone().add(0.7, 0.3, 0.3);
        benchModel = new PianoSkin(PianoKeysConst.BENCH.getId(), "bench");
        hitBox = new HitBox(min, max);
    }

    public void create() {
        active = true;
        armorStand = ArmorStandFactory.create(location);
        armorStand.setSmall(true);
        armorStand.setHelmet(benchModel.getItemStack());
    }

    public boolean handleClick(Player player) {
        if (!active) {
            return false;
        }

        if (armorStand.getPassengers().size() > 0) {
            return false;
        }
        if (!hitBox.isCollider(player.getEyeLocation(), 10)) {
            return false;
        }
        armorStand.setPassenger(player);
        return true;
    }

    public void setState(boolean state)
    {
        if(active == state)
            return;

        if(state)
        {
            create();
        }
        else
        {
            destroy();
        }

    }

    @Override
    public void destroy() {
        active = false;
        armorStand.remove();
    }
}
