package jw.piano.game_objects.models;

import jw.piano.enums.PianoKeysConst;
import jw.piano.game_objects.models.effects.EffectManager;
import jw.piano.game_objects.utils.Consts;
import jw.piano.game_objects.utils.MappedSounds;
import jw.piano.game_objects.utils.PlaySound;
import jw.piano.sounds.SoundPlayerFactory;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
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
    private float volume;
    private HitBox hitBox;
    private Particle.DustOptions options;
    private Location particleLocation;
    private World world;
    private boolean isPedalPressed;
    private PianoPedalModel pedalModel;
    private int radious;
    private SoundPlayerFactory soundPlayerFactory;
    private EffectManager effectManager;

    public PianoKeyModel(Location location, boolean isBlack,int index) {
        super(location);
        world = location.getWorld();
        this.index = index;
        this.isBlack = isBlack;
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
           setCustomModelData(PianoKeysConst.WHITE_KEY.getId());
        setupParticle();
        setupHitbox(location);
        soundPlayerFactory = FluentInjection.getInjection(SoundPlayerFactory.class);
    }

    private void setupHitbox(Location location)
    {
        hitBox = new HitBox(location.clone().add(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08),
                location.clone().add(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08)
        );
        hitBox.setOrigin(location);
    }

    private void setupParticle()
    {
        var color = Color.fromRGB(MathUtility.getRandom(200, 255), MathUtility.getRandom(200, 255), MathUtility.getRandom(200, 255));
        options = new Particle.DustOptions(color, 0.3F);
        particleLocation = getLocation().clone().add(0, 1.8f, 0);
    }

    public PianoKeyModel(PianoPedalModel pedalModel, Location location, boolean isBlack, int index) {
        super(location);
        this.isBlack = isBlack;
        this.index = index;
        this.pedalModel = pedalModel;
        this.radious = 10;
        this.hitBox = new HitBox(location.clone().add(isBlack ? -0.015 : -0.03, isBlack ? 1.61 : 1.6, -0.08),
                location.clone().add(isBlack ? 0.015 : 0.03, isBlack ? 1.7 : 1.65, isBlack ? 0.075 : 0.08)
        );
        this.hitBox.setOrigin(location);
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY.getId());


        soundPlayerFactory = FluentInjection.getInjection(SoundPlayerFactory.class);
    }

    public void setVolume(int volume)
    {
        this.volume = volume/100.0f;
    }

    public void setEffectManager(EffectManager manager)
    {
        effectManager = manager;
    }

    @Override
    public void press(int id, int velocity) {
        final var soundLevel = volume * (velocity) / 50.0f;
        soundPlayerFactory.play(pedalModel.getLocation(),id,soundLevel,pedalModel.isPressed());
        effectManager.invoke(getLocation(), index,velocity);
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY_PRESSED.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY_PRESSED.getId());


        isPressed = true;
    }

    public void press() {
        this.press(index,100);
    }

    @Override
    public void release(int id, int velocity) {
        if (isBlack)
            setCustomModelData(PianoKeysConst.BLACK_KEY.getId());
        else
            setCustomModelData(PianoKeysConst.WHITE_KEY.getId());

        isPressed = false;
    }

    public void release() {
       this.release(1,10);
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
