package jw.piano.game_objects.models;

import jw.piano.game_objects.PianoDataObserver;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.enums.PianoTypes;
import jw.spigot_fluent_api.fluent_game_object.GameObject;
import jw.spigot_fluent_api.utilites.math.collistions.HitBox;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public class PianoModel extends GameObject
{
    private PianoKeyModel[] pianoKeys = new PianoKeyModel[88];
    private PianoPedalModel[] pianoPedals = new PianoPedalModel[3];
    private final int MIDI_KEY_OFFSET = 21;
    private final int PRESS_PEDAL =  1;
    private HitBox openViewHitBox;
    private ArmorStand pianoModelSkin;
    private PianoDataObserver observer;

    public PianoModel(PianoDataObserver observer)
    {
        this.observer = observer;
        observer.getPianoTypeBind().onChange(this::setPianoType);
        observer.getEnableBind().onChange(value ->
        {
            if(value)
                create();
            else
                destroy();
        });
        observer.getLocationBind().onChange(value ->
        {
           this.destroy();
           this.create();
        });
    }

    public void invokeNote(int pressed, int index, int velocity) {
        if (pressed != 0)
            pianoKeys[index - MIDI_KEY_OFFSET].press(index, velocity, 0);
        else
            pianoKeys[index - MIDI_KEY_OFFSET].release(index, velocity, 0);
    }

    public PianoPedalModel[] getPianoPedals()
    {
        return pianoPedals;
    }
    public void invokePedal(int pressed, int index, int velocity) {

        PianoPedalModel pedal = null;
        switch (index) {
            case 64:
                pedal = pianoPedals[2];
                break;
            case 65:
                pedal = pianoPedals[1];
                break;
            case 67:
                pedal = pianoPedals[0];
                break;
        }
        if (pedal == null)
            return;

        if (pressed == PRESS_PEDAL)
            pedal.press(index, velocity, 1);
        else
            pedal.release(index, velocity, 1);
    }

    public void create()
    {
        Location location = observer.getLocationBind().get();
        location.setYaw(0);
        location.setPitch(0);
        this.pianoModelSkin = ArmorStandFactory.createInvisibleArmorStand(location.clone());
        this.openViewHitBox = new HitBox(location.clone().add(1,4,0),location.clone().add(0,3,-1));
        this.openViewHitBox.showHitBox();
        setPianoType(PianoTypes.NONE);

        //Creating pedals
        for(int i=0;i<3;i++)
        {
            pianoPedals[i] = new PianoPedalModel(location.clone().add(-0.40+(i*0.20),-0.1,0.1f));
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
                pianoKeys[i-1] =  (new PianoKeyModel(startKeysLocation.clone().add(0.025f,0.02f ,-0.05f),true,i+MIDI_KEY_OFFSET));
            }
            else
            {
                pianoKeys[i-1] = (new PianoKeyModel(startKeysLocation.clone().add(0.05f,0,0),false,i+MIDI_KEY_OFFSET));
                startKeysLocation = startKeysLocation.clone().add(0.05f,0,0);
            }
        }
    }

    public void destroy()
    {
        this.openViewHitBox = null;

        for (PianoKeyModel key : pianoKeys)
        {
            key.getArmorStand().remove();
        }
        for (PianoPedalModel pedal : pianoPedals)
        {
            pedal.getArmorStand().remove();
        }
        pianoModelSkin.remove();
    }

    public PianoKeyModel[] getPianoKeys()
    {
        return pianoKeys;
    }

    public PianoKeyModel[] getSortedKeys()
    {
        var keys = getPianoKeys().clone();
        Arrays.sort(keys, new Comparator<PianoKeyModel>() {
            @Override
            public int compare(PianoKeyModel o1, PianoKeyModel o2)
            {
                return Boolean.compare(o1.isWhite(), o2.isWhite());
            }
        });
        return keys;
    }

    public PianoPedalModel getSustainPedal()
    {
        return pianoPedals[2];
    }

    private void setPianoType(PianoTypes pianoType)
    {
        if (pianoType == PianoTypes.NONE)
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

    public Location getPianoKeysCenterLocation()
    {
        return pianoKeys[pianoKeys.length/2].getLocation();
    }
    public Location getLocation()
    {
        return observer.getLocationBind().get();
    }
}
