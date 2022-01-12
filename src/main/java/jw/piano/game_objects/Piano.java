package jw.piano.game_objects;

import jw.piano.data.PianoData;
import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;
import jw.piano.game_objects.models.PianoModel;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_game_object.GameObject;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;


@Getter
public class Piano
{
    private final PianoModel pianoModel;
    private final PianoDataObserver pianoDataObserver;
    private final Settings settings;
    private PianoInteractionHandler pianoInteractionHandler;

    private boolean isCreated;

    public Piano(PianoData pianoData) {
        pianoDataObserver = new PianoDataObserver();
        pianoDataObserver.observePianoData(pianoData);
        pianoModel = new PianoModel(pianoDataObserver);
        settings = InjectionManager.getObject(Settings.class);
    }


    public void openGUIPanel(Player player) {
        final var gui = InjectionManager.getObjectPlayer(MenuGUI.class, player.getUniqueId());
        gui.openPianoView(player, this);
    }

    public void create() {
        pianoModel.create();
        pianoInteractionHandler = new PianoInteractionHandler(pianoModel);
        isCreated =true;
    }

    public void destroy() {
        pianoModel.destroy();
        isCreated =false;
    }

    public Location getLocation() {
        return pianoModel.getLocation();
    }

    public void handlePlayerInteraction(Player player) {
        if(!isCreated)
            return;

        if (!pianoModel.getOpenViewHitBox().isCollider(player.getEyeLocation(), 5)) {
            openGUIPanel(player);
            return;
        }
        pianoInteractionHandler.onPlayerClick(player.getEyeLocation());
    }

    public boolean isLocationInPianoRage(Location location) {
        if(!isCreated)
            return false;

        final var minDistance = settings.getMinDistanceToPiano();
        final var distance = location.distance(getLocation());
        return distance <= minDistance;
    }
}
