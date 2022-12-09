package jw.piano.spigot.gameobjects.models;

import jw.piano.api.enums.PianoKeysConst;
import org.bukkit.Location;

public class PianoPedalModel extends CustomModel
{
    public PianoPedalModel(String guid, Location location)
    {
        super(location, guid);
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

    public void onDestroy()
    {
        getArmorStand().remove();
    }
}
