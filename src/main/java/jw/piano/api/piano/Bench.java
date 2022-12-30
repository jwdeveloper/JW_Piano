package jw.piano.api.piano;

import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.piano.common.*;
import org.bukkit.entity.Player;

import javax.security.auth.Refreshable;

public interface Bench extends Teleportable, Visiable, Interactable, GuiViewer, Resetable {
    boolean sitPlayer(Player player);

    void move(BenchMove benchMove);

    void refresh();
}
