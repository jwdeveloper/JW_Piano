package jw.piano.game_objects.models;

import jw.piano.enums.PianoKeysConst;
import org.bukkit.Location;

public class PianoPedalModel extends CustomModel
{
    public PianoPedalModel(Location location)
    {
        super(location);
        setCustomModelData(PianoKeysConst.PEDAL.getId());
    }


    public void press()
    {
        press(0,0);
    }

    public void release()
    {
        release(0,0);
    }

    @Override
    public void press(int id, int velocity) {
        setCustomModelData(PianoKeysConst.PEDAL_DOWN.getId());
        isPressed=true;
    }

    @Override
    public void release(int id, int velocity) {
        setCustomModelData(PianoKeysConst.PEDAL.getId());
        isPressed =false;
    }

    public void destroy()
    {
        getArmorStand().remove();
    }
}
