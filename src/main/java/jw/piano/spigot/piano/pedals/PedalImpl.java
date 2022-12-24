package jw.piano.spigot.piano.pedals;

import jw.fluent.api.spigot.gameobjects.implementation.ArmorStandModel;
import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.enums.PianoKeysConst;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.pedals.Pedal;

public class PedalImpl extends GameObject implements Pedal
{
    private boolean isPressed;
    private ArmorStandModel armorStandModel;

    private PianoData pianoData;

    public PedalImpl(PianoData data)
    {
        this.pianoData = data;
    }

    @Override
    public void onCreate() {
        armorStandModel = addGameComponent(ArmorStandModel.class);
        armorStandModel.setOnCreated(model ->
        {
            armorStandModel.setItemStack(PluginConsts.ITEMSTACK());
            armorStandModel.getArmorStand().setSmall(true);
            armorStandModel.setId(PluginConsts.PIANO_NAMESPACE, pianoData.getUuid());
            release();
        });
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event)
    {
        return false;
    }


    @Override
    public void press(int velocity) {
        armorStandModel.setCustomModelId(PianoKeysConst.PEDAL_DOWN.getId());
        isPressed = true;
    }

    @Override
    public void press() {
        press(100);
    }

    @Override
    public void release() {
        armorStandModel.setCustomModelId(PianoKeysConst.PEDAL.getId());
        isPressed = false;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public void refresh() {
        armorStandModel.refresh();
    }
}
