package jw.piano.game_objects.models;

import jw.piano.enums.PianoKeysConst;
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
    private HitBox hitBox;
    private Particle.DustOptions particles;
    private Location particleLocation;
    private World world;
    private boolean isPedalPressed;

    public PianoKeyModel(Location location, boolean isBlack, int index) {
        super(location);
        this.isBlack = isBlack;
        this.index = index;

        this.hitBox = new HitBox(location.clone().add(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08),
                location.clone().add(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08)
        );
        this.hitBox.setOrigin(location);
        if (isBlack)
            setCustomModelData(4);
        else
            setCustomModelData(2);

        var color = Color.fromRGB(MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255));
        particles = new Particle.DustOptions(color, 0.3F);
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
        this.getArmorStand()
                .getWorld()
                .playSound
                        (this.getArmorStand().getLocation(),
                                getSound(id),
                                SoundCategory.VOICE,
                                (float) (velocity / 50.0f),
                                1);

        world.spawnParticle(Particle.REDSTONE, particleLocation, 1, particles);

        if (this.isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY_PRESSED.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY_PRESSED.getId());

        isPressed = true;
    }

    @Override
    public void release(int id, int velocity, int channel) {
        if (this.isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY.getId());

        isPressed = false;
    }

    public void setPedalPressed(boolean pedalPressed) {
        isPedalPressed = pedalPressed;
    }

    public String getSound(int id) {
        return isPedalPressed ? "minecraft:1c." + id : "minecraft:1." + id;
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
