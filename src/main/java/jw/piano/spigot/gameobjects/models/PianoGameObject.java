package jw.piano.spigot.gameobjects.models;

import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.models.PianoSkin;
import jw.piano.data.enums.PianoEffect;
import jw.fluent.api.spigot.gameobjects.api.GameObject;
import jw.fluent.api.utilites.math.collistions.HitBox;
import jw.piano.factory.ArmorStandFactory;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.spigot.gameobjects.models.key.PianoKeyGameObject;
import jw.piano.spigot.gameobjects.models.key.PianoKeyGroupGameObject;
import jw.piano.spigot.gameobjects.models.pedals.PedalGameObject;
import jw.piano.spigot.gameobjects.models.pedals.PedalGroupGameObject;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;


public class PianoGameObject extends GameObject {

    private final String guid;
    private final ArmorStandFactory armorStandFactory;
    private HitBox openViewHitBox;

    private HitBox pianoHitBox;
    private ArmorStand pianoArmorStand;

    @Getter
    private BenchGameObject pianoBench;
    @Getter
    private PianoKeyGroupGameObject pianoKeyGroup;
    @Getter
    private PedalGroupGameObject pedalGroup;


    public PianoGameObject(String guid, ArmorStandFactory armorStandFactory) {
        this.guid = guid;
        this.armorStandFactory = armorStandFactory;
    }


    @Override
    public void onCreated() {
        location.setYaw(0);
        location.setPitch(0);

        pianoArmorStand = armorStandFactory.create(location.clone(), guid);
        setPianoSkin(new PianoSkin(109, "Grand piano"));

        openViewHitBox = new HitBox(location.clone().add(-1.5, 1.5, -0.5), location.clone().add(1.3, 2.2, 0.3));
        pianoHitBox = new HitBox(location.clone().add(-1.5, 0.1, -1), location.clone().add(1.3, 2, 0.3));

        pianoBench = new BenchGameObject(guid, armorStandFactory);
        pianoKeyGroup = new PianoKeyGroupGameObject(guid, armorStandFactory);
        pedalGroup = new PedalGroupGameObject(guid, armorStandFactory);

        addGameComponent(pianoBench);
        addGameComponent(pianoKeyGroup);
        addGameComponent(pedalGroup);
    }

    @Override
    public void onDestroy() {
        openViewHitBox.hide();
        pianoArmorStand.remove();
    }

    public void refreshKeys() {
        pianoKeyGroup.refreshKeys();
    }

    public void setEffect(PianoEffect pianoEffect) {
        pianoKeyGroup.setEffect(pianoEffect);
    }

    public void setVolume(int volume) {
        pianoKeyGroup.setVolume(volume);
    }

    public void updateSustain() {
        pedalGroup.updateSustain();
    }

    public void invokeNote(int pressed, int index, int velocity) {
        pianoKeyGroup.invokeNote(pressed, index, velocity, pedalGroup.isSustainPressed());
    }

    public void invokePedal(int isPressed, int index) {
        pedalGroup.invokePedal(isPressed, index);
    }

    public boolean handlePlayerClick(Player player, Piano piano, boolean isLeftClick) {

        if (pianoBench.onPlayerClick(player, piano, isLeftClick)) {
            return true;
        }

        if (!pianoHitBox.isCollider(player.getEyeLocation(), 3)) {
            return false;
        }

        if (isLeftClick) {
            return pianoKeyGroup.onPlayerClick(player, pedalGroup.isSustainPressed());
        }
        return false;
    }

    public boolean isGuiHitBoxCollider(Player player) {
        return openViewHitBox.isCollider(player.getEyeLocation(), 3);
    }

    public void setPianoSkin(PianoSkin skin) {
        if (skin.getCustomModelId() == 0) {
            if (pianoArmorStand != null)
                pianoArmorStand.setHelmet(null);
        } else {
            pianoArmorStand.setHelmet(skin.getItemStack());
        }
    }

}
