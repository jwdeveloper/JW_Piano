package jw.piano.spigot.gameobjects.models;

import jw.piano.data.PluginConsts;
import jw.piano.factory.ArmorStandFactory;
import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class PressableGameObject extends GameObject
{
     private final ArmorStand armorStand;
     private final ItemStack itemStack;

     protected final ArmorStandFactory armorStandFactory;

     public PressableGameObject(Location location, ArmorStandFactory armorStandFactory, String guid)
     {
          this.armorStandFactory = armorStandFactory;
          this.armorStand = armorStandFactory.create(location, guid);
          this.getArmorStand().setSmall(true);
          this.itemStack  = new ItemStack(PluginConsts.MATERIAL,1);
          this.location = location;
     }


     public ArmorStand getArmorStand()
     {
          return  armorStand;
     }

     public void setCustomModelData(int id)
     {
          ItemMeta meta = itemStack.getItemMeta();
          meta.setCustomModelData(id);
          itemStack.setItemMeta(meta);
          armorStand.setHelmet(itemStack);
     }
}
