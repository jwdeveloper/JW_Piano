package jw.piano.spigot.piano.keyboard;

import jw.fluent.api.spigot.gameobjects.implementation.ArmorStandModel;
import jw.fluent.api.spigot.gameobjects.implementation.GameObject;
import jw.fluent.api.utilites.math.InteractiveHitBox;
import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.enums.PianoKeysConst;
import jw.piano.api.data.events.PianoInteractEvent;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.managers.effects.EffectManager;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.api.piano.keyboard.PianoKey;
import org.bukkit.util.Vector;


public class PianoKeyImpl extends GameObject implements PianoKey {
    private final int CLICK_TICKS = 10;
    private final int RADIOUS = 10;
    private final boolean isBlack;
    private final int midiIndex;
    private final SoundsManager soundsManager;
    private final EffectManager effectManager;
    private final PianoData pianoData;
    private ArmorStandModel armorStandModel;
    private InteractiveHitBox hitBox;

    public PianoKeyImpl(
            PianoData pianoData,
            SoundsManager soundPlayerFactory,
            EffectManager effectManager,
            boolean isBlack,
            int index) {
        this.soundsManager = soundPlayerFactory;
        this.effectManager = effectManager;
        this.isBlack = isBlack;
        this.midiIndex = index;
        this.pianoData = pianoData;
    }


    @Override
    public void onCreate() {
        var min = new Vector(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08);
        var max = new Vector(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08);
        hitBox = new InteractiveHitBox(location, min, max);

        armorStandModel = addGameComponent(ArmorStandModel.class);
        armorStandModel.setOnCreated(model ->
        {
            armorStandModel.setItemStack(PluginConsts.ITEMSTACK());
            armorStandModel.getArmorStand().setSmall(true);
            release();
        });
    }

    @Override
    public boolean triggerPlayerClick(PianoInteractEvent event) {
        if (!hitBox.isCollider(event.getEyeLocation(), RADIOUS)) {
            return false;
        }
        FluentTasks.taskTimer(CLICK_TICKS, (iteration, task) ->
                {
                    press();
                }).stopAfterIterations(1)
                .onStop(task ->
                {
                    release();
                }).run();
        return true;
    }

    @Override
    public void press() {
        press(100);
    }

    @Override
    public void press(int velocity) {
        final var soundLevel = pianoData.getVolume() * (velocity) / 50.0f;
        soundsManager.play(location, midiIndex, soundLevel, pianoData.getPedalsSettings().getSustainPressed());
        effectManager.getCurrent().onNote(location, midiIndex, velocity);
        if (isBlack)
            armorStandModel.setCustomModelId(PianoKeysConst.BLACK_KEY_PRESSED.getId());
        else
            armorStandModel.setCustomModelId(PianoKeysConst.WHITE_KEY_PRESSED.getId());
    }

    @Override
    public void release() {
        if (isBlack)
            armorStandModel.setCustomModelId(PianoKeysConst.BLACK_KEY.getId());
        else
            armorStandModel.setCustomModelId(PianoKeysConst.WHITE_KEY.getId());
    }

    public boolean isWhite()
    {
        return !isBlack;
    }

    @Override
    public int compareTo(Object o) {
        return isBlack ? 0 : 1;
    }
}
