package jw.piano.api.piano;

import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.piano.common.*;
import org.bukkit.entity.Player;

public interface Bench extends Teleportable, Visiable, Interactable, GuiViewer, Resetable {
    boolean sitPlayer(Player player);
    void move(BenchMove benchMove);
}
