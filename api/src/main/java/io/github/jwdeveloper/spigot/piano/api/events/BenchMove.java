package io.github.jwdeveloper.spigot.piano.api.events;

import io.github.jwdeveloper.spigot.piano.api.enums.AxisMove;
import lombok.Builder;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

@Builder
@Getter
public class BenchMove
{
    private Player player;
    private AxisMove axisMove = AxisMove.X;
    private int timeout = 20*60;
    private Consumer<String> onAccept;
    private Consumer<String> onCanceled;
}
