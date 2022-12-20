package jw.piano.spigot.gameobjects.models.key;

import jw.piano.data.enums.PianoKeysConst;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.spigot.effects.EffectManager;
import jw.piano.spigot.gameobjects.models.PressableGameObject;
import jw.piano.factory.sounds.SoundPlayerFactory;
import jw.fluent.api.utilites.math.collistions.HitBox;
import lombok.Getter;
import org.bukkit.*;

@Getter
public class PianoKeyGameObject extends PressableGameObject implements Comparable {
    private final boolean isBlack;
    private final int index;
    private final int radious;
    private final SoundPlayerFactory soundPlayerFactory;
    private final EffectManager effectManager;
    private final Location keyLocation;

    private float volume;
    private HitBox hitBox;
    private boolean isPressed;

    public PianoKeyGameObject(
            String guid,
            SoundPlayerFactory soundPlayerFactory,
            EffectManager effectManager,
            ArmorStandFactory armorStandFactory,
            Location location,
            boolean isBlack,
            int index) {
        super(location,armorStandFactory,  guid);
        this.keyLocation = location;
        this.soundPlayerFactory = soundPlayerFactory;
        this.effectManager = effectManager;
        this.isBlack = isBlack;
        this.index = index;
        this.radious = 10;

    }

    @Override
    public void onCreate() {
        this.hitBox = new HitBox(keyLocation.clone().add(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08),
                keyLocation.clone().add(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08)
        );
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY.getId());
    }

    @Override
    public void onDestroy() {
        getArmorStand().remove();
    }

    public boolean hasCollision(Location rayOrigin, float length) {
        return getHitBox().isCollider(rayOrigin, length);
    }


    public void setVolume(int volume) {
        this.volume = volume / 100.0f;
    }


    public void press(int id, int velocity, boolean isPedalPressed) {
        final var soundLevel = volume * (velocity) / 50.0f;
        soundPlayerFactory.play(location,id,soundLevel,isPedalPressed);
        effectManager.invoke(keyLocation, index, velocity);
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY_PRESSED.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY_PRESSED.getId());


        isPressed = true;
    }

    public void press(boolean isPedalPressed) {
        this.press(index, 100, isPedalPressed);
    }

    public void release() {
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY.getId());

        isPressed = false;
    }




    public boolean isWhite() {
        return !isBlack;
    }

    @Override
    public int compareTo(Object o) {
        return isBlack ? 0 : 1;
    }
}
