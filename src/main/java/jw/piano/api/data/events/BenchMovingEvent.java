package jw.piano.api.data.events;

import jw.piano.api.data.dto.BenchMove;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;

@Data
@AllArgsConstructor
public class BenchMovingEvent
{
    private BenchMove benchMoveDto;
    private Location startLocation;
}
