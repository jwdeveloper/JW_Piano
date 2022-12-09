package jw.piano.spigot.gameobjects.factories;

import jw.piano.api.data.PluginConfig;
import jw.piano.api.data.PluginConsts;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;

public class ArmorStandFactory
{

    public static ArmorStand create(Location location, String id)
    {
        ArmorStand armorStand;
        armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCollidable(false);
        armorStand.setGravity(false);
        armorStand.setAI(false);
        armorStand.setMarker(true);
        armorStand.setNoDamageTicks(0);
        armorStand.setInvulnerable(true);
        armorStand.setInvisible(true);
        armorStand.setRemoveWhenFarAway(true);

        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.setRotation(0, 0);

        var metadata= armorStand.getPersistentDataContainer();
        metadata.set(PluginConsts.PIANO_NAMESPACE, PersistentDataType.STRING,id);
        return armorStand;
    }

}
