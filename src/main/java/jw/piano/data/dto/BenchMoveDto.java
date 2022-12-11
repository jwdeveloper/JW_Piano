package jw.piano.data.dto;

import jw.piano.data.enums.MoveGameObjectAxis;
import jw.piano.spigot.gameobjects.Piano;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@Getter
@Setter
public class BenchMoveDto
{
    private MoveGameObjectAxis moveType;

    private Player player;

    private Piano piano;

    private Consumer<Void> onAccept;

    private Consumer<Void> onCanceled;

    private Location originLocation;
}
