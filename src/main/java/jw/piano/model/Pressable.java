package jw.piano.model;

import jw.piano.utility.ArmorStandFactory;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public abstract class Pressable
{

     private ArmorStand armorStand;
     private ItemStack itemStack;
     private Location location;

     protected boolean isPressed;

     public Pressable(Location location)
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

     public abstract void  press(int id, int velocity, int channel);

     public abstract void release(int id, int velocity, int channel);

     public ArmorStand getArmorStand()
     {
          return  armorStand;
     }

     protected void setCustomModelData(int id)
     {
          ItemMeta meta = itemStack.getItemMeta();
          meta.setCustomModelData(id);
          itemStack.setItemMeta(meta);
          armorStand.setHelmet(itemStack);
     }

     public Location getLocation(){return location;}

}
