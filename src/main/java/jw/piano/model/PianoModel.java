package jw.piano.model;

import jw.piano.data.PianoData;
import jw.piano.data.PianoDataObserver;
import jw.piano.utility.ArmorStandFactory;
import jw.piano.utility.PianoTypes;
import jw.spigot_fluent_api.utilites.math.collistions.HitBox;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

@Getter
public class PianoModel
{
    private PianoData pianoData;
    private PianoKey[] pianoKeys = new PianoKey[88];
    private PianoPedal[] pianoPedals = new PianoPedal[3];
    private ArmorStand pianoModelSkin;
    private final int MIDI_KEY_OFFSET = 21;
    private HitBox openViewHitBox;
    private PianoDataObserver pianoDataObserver;

    public PianoModel(PianoData pianoData)
    {
        this.pianoData = pianoData;
        pianoDataObserver = new PianoDataObserver();
        pianoDataObserver.observePianoData(pianoData);
        pianoDataObserver.getPianoTypeBind().onChange(this::setPianoType);
        pianoDataObserver.getEnableBind().onChange(value ->
        {
            if(value)
                create();
            else
                destroy();
        });
        pianoDataObserver.getLocationBind().onChange(value ->
        {
           this.destroy();
           this.create();
        });
    }

    public PianoData getPianoData() {
        return pianoData;
    }

    public void invokeNote(int pressed, int index, int velocity) {
        if (pressed != 0)
            pianoKeys[index - MIDI_KEY_OFFSET].press(index, velocity, 0);
        else
            pianoKeys[index - MIDI_KEY_OFFSET].release(index, velocity, 0);
    }

    public PianoPedal[] getPianoPedals()
    {
        return pianoPedals;
    }
    public void invokePedal(int pressed, int index, int velocity) {

        PianoPedal pedal = null;
        switch (index) {
            case 64:
                pedal = pianoPedals[2];
                break;
            case 65:
                pedal = pianoPedals[1];
                break;
            case 67:
                pedal = pianoPedals[1];
                break;
        }

        if (pedal == null)
            return;

        if (pressed != 0)
            pedal.press(index, velocity, 1);
        else
            pedal.release(index, velocity, 1);
    }

    public void create()
    {
        Location location =pianoData.getLocation().clone();
        location.setYaw(0);
        location.setPitch(0);
        this.pianoModelSkin = ArmorStandFactory.createInvisibleArmorStand(location.clone());
        this.openViewHitBox = new HitBox(location.clone().add(1,4,0),location.clone().add(0,3,-1));
        this.openViewHitBox.showHitBox();
        setPianoType(PianoTypes.None);

        //Creating pedals
        for(int i=0;i<3;i++)
        {
            pianoPedals[i] = new PianoPedal(location.clone().add(-0.40+(i*0.20),-0.1,0.1f));
        }
        //Creating keys
        Location startKeysLocation =location.clone().add(-1.5,-0.4,0.3f);
        startKeysLocation.setDirection(new Vector(0,1,0));
        int key=1;
        int octave =0;
        for(int i=1;i<=88;i++)
        {
            if(i>3 && i<88)
            {
                key = (i-4)%12;
                octave = 1+(i-4)/12;
            }
            if(i<=3)
            {
                key = i + 8;
            }

            if(key == 1 || key == 3 || key == 6 || key == 8 || key ==10)
            {
                pianoKeys[i-1] =  (new PianoKey(startKeysLocation.clone().add(0.025f,0.02f ,-0.05f),true,i+MIDI_KEY_OFFSET));
            }
            else
            {
                pianoKeys[i-1] = (new PianoKey(startKeysLocation.clone().add(0.05f,0,0),false,i+MIDI_KEY_OFFSET));
                startKeysLocation = startKeysLocation.clone().add(0.05f,0,0);
            }
        }
    }

    public Location getPianoKeysCenterLocation()
    {
       return pianoKeys[pianoKeys.length/2].getLocation();
    }

    public void destroy()
    {
        this.openViewHitBox = null;

        for (PianoKey key : pianoKeys)
        {
            key.getArmorStand().remove();
        }
        for (PianoPedal pedal : pianoPedals)
        {
            pedal.getArmorStand().remove();
        }
        pianoModelSkin.remove();
    }

    public PianoKey[] getPianoKeys()
    {
        return pianoKeys;
    }



    private void setPianoType(PianoTypes pianoType)
    {
        if (pianoType == PianoTypes.None)
        {
            if (pianoModelSkin != null)
                pianoModelSkin.setHelmet(null);
        } else
        {
            ItemStack itemStack = new ItemStack(Material.WOODEN_HOE, 1);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(pianoType.getId());
            itemStack.setItemMeta(meta);
            pianoModelSkin.setHelmet(itemStack);
        }
    }
    public Location getLocation()
    {
        return this.pianoData.getLocation();
    }
}
