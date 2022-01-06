package jw.piano.model;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class PianoPedal extends Pressable
{
    public PianoPedal(Location location)
    {
        super(location);
        setCustomModelData(6);
    }


    @Override
    public void press(int id, int velocity, int channel) {
        setCustomModelData(7);
        isPressed=true;
    }

    @Override
    public void release(int id, int velocity, int channel) {
        setCustomModelData(6);
        isPressed =false;
    }
}
