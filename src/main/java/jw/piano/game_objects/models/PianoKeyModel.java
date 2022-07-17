package jw.piano.game_objects.models;

import jw.piano.enums.PianoKeysConst;
import jw.piano.game_objects.utils.MappedSounds;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.utilites.math.MathUtility;
import jw.spigot_fluent_api.utilites.math.collistions.HitBox;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.*;

@Getter
@Setter
public class PianoKeyModel extends CustomModel implements Comparable {
    private boolean isBlack;
    private int index;
    private boolean isPressed;
    private int volume;
    private HitBox hitBox;
    private Particle.DustOptions options;
    private Location particleLocation;
    private World world;
    private boolean isPedalPressed;
    private PianoPedalModel pedalModel;

    public PianoKeyModel(PianoPedalModel pedalModel, Location location, boolean isBlack, int index) {
        super(location);
        this.isBlack = isBlack;
        this.index = index;
        this.pedalModel = pedalModel;

        this.hitBox = new HitBox(location.clone().add(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08),
                location.clone().add(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08)
        );
        this.hitBox.setOrigin(location);
        if (isBlack)
            setCustomModelData(4);
        else
            setCustomModelData(2);

        var color = Color.fromRGB(MathUtility.getRandom(200, 255), MathUtility.getRandom(200, 255), MathUtility.getRandom(200, 255));
        options = new Particle.DustOptions(color, 0.3F);
        particleLocation = location.clone().add(0, 1.8f, 0);
        world = location.getWorld();
    }

    public void setHighlighted(boolean isHighlight) {
        int id;
        if (isHighlight) {
            id = isBlack ? PianoKeysConst.BLACK_KEY_SELECTED.getId() : PianoKeysConst.WHITE_KEY_SELECTED.getId();
        } else {
            id = isBlack ? PianoKeysConst.BLACK_KEY.getId() : PianoKeysConst.WHITE_KEY.getId();
        }
        setCustomModelData(id);
    }

    @Override
    public void press(int id, int velocity, int channel) {
        final var sounds =    volume/100.0f * (velocity) / 50.0f;
        world.playSound(particleLocation,
                                MappedSounds.getSound(id,pedalModel.isPressed()),
                                SoundCategory.VOICE,
                                sounds,
                                1);
        world.spawnParticle(Particle.REDSTONE, particleLocation, 1, options);

        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY_PRESSED.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY_PRESSED.getId());

        isPressed = true;
    }

    public void press() {
        this.press(index,100,1);
    }

    @Override
    public void release(int id, int velocity, int channel) {
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY.getId());

        isPressed = false;
    }

    public void release() {
       this.release(1,10,10);
    }

    public void destroy()
    {
        getArmorStand().remove();
    }


    public boolean isBlack() {
        return isBlack;
    }

    public boolean isWhite() {
        return !isBlack;
    }

    @Override
    public int compareTo(Object o) {
        return isBlack ? 0 : 1;
    }
}
