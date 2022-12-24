package jw.piano.api.data.dto;

import jw.piano.api.data.enums.AxisMove;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
