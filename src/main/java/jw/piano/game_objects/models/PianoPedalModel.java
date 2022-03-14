package jw.piano.game_objects.models;

import org.bukkit.Location;

public class PianoPedalModel extends CustomModel
{
    public PianoPedalModel(Location location)
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

    public void destroy()
    {
        getArmorStand().remove();
    }
}
