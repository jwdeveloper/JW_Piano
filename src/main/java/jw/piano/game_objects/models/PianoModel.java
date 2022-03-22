package jw.piano.game_objects.models;

import jw.piano.game_objects.PianoDataObserver;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.enums.PianoType;
import jw.spigot_fluent_api.fluent_game_object.GameObject;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
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
public class PianoModel extends GameObject {

    private final int MIDI_KEY_OFFSET = 21;
    private final int PRESS_PEDAL = 1;
    private final PianoKeyModel[] pianoKeys = new PianoKeyModel[88];
    private final PianoPedalModel[] pianoPedals = new PianoPedalModel[3];
    private HitBox openViewHitBox;
    private ArmorStand pianoModelSkin;

    public PianoModel() { }

    public void invokeNote(int pressed, int index, int velocity) {
        if(index < MIDI_KEY_OFFSET)
            return;

        if (pressed != 0)
            pianoKeys[index - MIDI_KEY_OFFSET].press(index, velocity, 0);
        else
            pianoKeys[index - MIDI_KEY_OFFSET].release(index, velocity, 0);
    }


    public void invokePedal(int isPressed, int index, int velocity) {
        final var pedal = switch (index) {
            case 64 -> pianoPedals[2];
            case 65 -> pianoPedals[1];
            case 67 -> pianoPedals[0];
            default -> null;
        };
        if (pedal == null)
            return;

        if (isPressed == PRESS_PEDAL)
            pedal.press(index, velocity, 1);
        else
            pedal.release(index, velocity, 1);
    }

    public void create(Location location) {

        location.setYaw(0);
        location.setPitch(0);
        pianoModelSkin = ArmorStandFactory.createInvisibleArmorStand(location.clone());
        openViewHitBox = new HitBox(location.clone().add(-1, 1, 0), location.clone().add(0, 2, -1));
        openViewHitBox.showHitBox();
        setPianoType(PianoType.NONE);

        //Creating pedals
        for (int i = 0; i < 3; i++) {
            pianoPedals[i] = new PianoPedalModel(location.clone().add(-0.4 + (i * 0.20), -0.1, 0.1f));
        }
        //Creating keys
        var startKeysLocation = location.clone().add(-1.5, -0.4, 0.3f);
        startKeysLocation.setDirection(new Vector(0, 1, 0));
        var key = 1;
        for (int i = 1; i <= 88; i++) {
            if (i > 3 && i < 88) {
                key = (i - 4) % 12;
            }
            if (i <= 3) {
                key = i + 8;
            }

            switch (key) {
                case 1, 3, 6, 8, 10:
                    pianoKeys[i - 1] = new PianoKeyModel(startKeysLocation.clone().add(0.025f, 0.02f, -0.05f), true, i + MIDI_KEY_OFFSET);
                    break;
                default:
                    pianoKeys[i - 1] = new PianoKeyModel(startKeysLocation.clone().add(0.05f, 0, 0), false, i + MIDI_KEY_OFFSET);
                    startKeysLocation = startKeysLocation.clone().add(0.05f, 0, 0);
                    break;
            }
        }
    }

    public void destroy() {
        openViewHitBox.hideHitbox();
        for (PianoKeyModel key : pianoKeys) {
            key.destroy();
        }
        for (PianoPedalModel pedal : pianoPedals) {
            pedal.destroy();
        }
        pianoModelSkin.remove();
    }

    public PianoKeyModel[] getSortedKeys() {
        var keys = getPianoKeys().clone();
        Arrays.sort(keys, new Comparator<PianoKeyModel>() {
            @Override
            public int compare(PianoKeyModel o1, PianoKeyModel o2) {
                return Boolean.compare(o1.isWhite(), o2.isWhite());
            }
        });
        return keys;
    }

    public void setPianoType(PianoType pianoType) {
        FluentPlugin.logSuccess("Type changed to "+pianoType.name());
        if (pianoType == PianoType.NONE) {
            if (pianoModelSkin != null)
                pianoModelSkin.setHelmet(null);
        } else {
            var itemStack = new ItemStack(Material.WOODEN_HOE, 1);
            var meta = itemStack.getItemMeta();
            meta.setCustomModelData(pianoType.getId());
            itemStack.setItemMeta(meta);
            pianoModelSkin.setHelmet(itemStack);
        }
    }

    public PianoKeyModel[] getPianoKeys() {
        return pianoKeys;
    }

    public PianoPedalModel[] getPianoPedals() {
        return pianoPedals;
    }

    public PianoPedalModel getSustainPedal() {
        return pianoPedals[2];
    }

    public Location getPianoKeysCenterLocation() {
        return pianoKeys[pianoKeys.length / 2].getLocation();
    }


}
