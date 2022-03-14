package jw.piano.factory;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;

public class ArmorStandFactory
{

    public static ArmorStand createInvisibleArmorStand(Location location)
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
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);
        armorStand.setRotation(0, 0);
        return armorStand;
    }

}
