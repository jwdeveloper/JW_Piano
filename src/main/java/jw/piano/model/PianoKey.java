package jw.piano.model;

import jw.colistions.HitBox;
import jw.piano.utility.ArmorStandFactory;
import jw.piano.utility.PianoKeysConst;
import jw.utilites.MathHelper;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PianoKey extends Pressable implements Comparable {
    private boolean isBlack;
    private int index;
    private boolean isPressed = false;
    private HitBox hitBox;
    private Particle.DustOptions particles;
    private Location particleLocation;
    private World world;

    public PianoKey(Location location, boolean isBlack,int index) {
        super(location);
        this.isBlack = isBlack;
        this.index = index;

        this.hitBox = new HitBox(location.clone().add(isBlack?-0.015:-0.03,isBlack?1.61:1.6,-0.08),
                                 location.clone().add(isBlack?0.015:0.03,isBlack?1.7:1.65,isBlack?0.075:0.08)
                );
        this.hitBox.setOrigin(location);
        if (isBlack)
            setCustomModelData(4);
        else
            setCustomModelData(2);

        Color color = Color.fromRGB(MathHelper.getRandom(0,255), MathHelper.getRandom(0,255), MathHelper.getRandom(0,255));
        particles = new Particle.DustOptions(color,  0.3F);
        particleLocation =  location.clone().add(0,1.8f,0);
        world = particleLocation.getWorld();

    }
    public HitBox getHitBox()
    {
        return hitBox;
    }
    public boolean isBlack()
    {
        return isBlack;
    }
    public boolean isWhite()
    {
        return !isBlack;
    }
    public int getIndex()
    {
        return index;
    }
    public void setHighlighted(boolean isHighlight)
    {
        int id = 0;
        if(isHighlight)
        {
          id = isBlack?PianoKeysConst.Black_Key_Selected.getId():PianoKeysConst.White_Key_Selected.getId();
        }
        else
        {
            id = isBlack?PianoKeysConst.Black_Key.getId():PianoKeysConst.White_Key.getId();
        }
        setCustomModelData(id);
    }

    private boolean isPedalPressed;

    public void setPedalPressed(boolean pedalPressed)
    {
        isPedalPressed =pedalPressed;
    }

    public String getSound(int id)
    {
        return isPedalPressed?"minecraft:1c."+id:"minecraft:1."+id;
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

        world.spawnParticle(Particle.REDSTONE,particleLocation , 1,particles);

        if (this.isBlack)
            setCustomModelData(PianoKeysConst.Black_Key_Pressed.getId());
        else
            setCustomModelData(PianoKeysConst.White_Key_Pressed.getId());

        isPressed =true;
    }

    @Override
    public void release(int id, int velocity, int channel) {
        if (this.isBlack)
            setCustomModelData(PianoKeysConst.Black_Key.getId());
        else
            setCustomModelData(PianoKeysConst.White_Key.getId());

        isPressed =false;
    }


    @Override
    public int compareTo(Object o) {
        return isBlack?0:1;
    }
}
