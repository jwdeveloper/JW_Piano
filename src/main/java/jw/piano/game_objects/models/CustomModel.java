package jw.piano.game_objects.models;

import jw.piano.game_objects.factories.ArmorStandFactory;
import jw.spigot_fluent_api.fluent_game_object.GameObject;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class CustomModel extends GameObject
{
     private ArmorStand armorStand;
     private ItemStack itemStack;
     private Location location;

     protected boolean isPressed;

     public CustomModel(Location location)
     {
          this.armorStand = ArmorStandFactory.createInvisibleArmorStand(location);
          this.getArmorStand().setSmall(true);
          this.itemStack  = new ItemStack(Material.WOODEN_HOE,1);
          this.location = location;
     }

     public boolean isPressed()
     {
          return isPressed;
     }

     public abstract void  press(int id, int velocity);

     public abstract void release(int id, int velocity);

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

     public Location getLocation(){return location;}

}
