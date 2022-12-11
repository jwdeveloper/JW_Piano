package jw.piano.spigot.gameobjects.models.pedals;

import jw.fluent.api.spigot.gameobjects.api.GameObject;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.PluginConsts;
import jw.piano.factory.ArmorStandFactory;

public class PedalGroupGameObject extends GameObject
{
    private final PedalGameObject[] pianoPedals = new PedalGameObject[3];

    private final String uuid;

    private final ArmorStandFactory armorStandFactory;

    public PedalGroupGameObject(String uuid, ArmorStandFactory armorStandFactory)
    {
        this.uuid = uuid;
        this.armorStandFactory = armorStandFactory;
    }

    @Override
    public void onCreated()
    {
        for (int i = 0; i < 3; i++) {
            final var pedalLocation = location.clone().add(-0.4 + (i * 0.20), -0.1, 0.1f);
            pianoPedals[i] = new PedalGameObject(uuid, pedalLocation, armorStandFactory);
            addGameComponent(pianoPedals[i]);
        }
    }

    public void invokePedal(int isPressed, int index) {
        final var pedal = switch (index) {
            case 64 -> pianoPedals[2];
            case 65 -> pianoPedals[1];
            case 67 -> pianoPedals[0];
            default -> null;
        };
        if (pedal == null)
            return;
        if (isPressed == PluginConsts.PRESSED_CODE)
            pedal.press();
        else
            pedal.release();
    }

    public boolean isSustainPressed()
    {
        return pianoPedals[2].isPressed();
    }

    public void updateSustain()
    {
        var sustain = pianoPedals[2];
        if (sustain.isPressed()) {
            sustain.release();
        } else {
            sustain.press();
        }
    }
}
