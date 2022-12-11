package jw.piano.spigot.gameobjects.models.pedals;

import jw.piano.data.enums.PianoKeysConst;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.spigot.gameobjects.models.CustomModel;
import lombok.Getter;
import org.bukkit.Location;

public class PedalGameObject extends CustomModel
{
    @Getter
    private boolean isPressed;
    public PedalGameObject(String guid, Location location, ArmorStandFactory armorStandFactory)
    {
        super(location, armorStandFactory,  guid);
    }

    @Override
    public void onCreated() {
        setCustomModelData(PianoKeysConst.PEDAL.getId());
    }

    public void press() {
        setCustomModelData(PianoKeysConst.PEDAL_DOWN.getId());
        isPressed=true;
    }


    public void release() {
        setCustomModelData(PianoKeysConst.PEDAL.getId());
        isPressed =false;
    }

    public void onDestroy()
    {
        getArmorStand().remove();
    }
}
