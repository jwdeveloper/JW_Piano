package jw.piano.data.models;

import lombok.Data;
import org.bukkit.util.Vector;

@Data
public class BenchSettings {
    private Boolean isActive = true;
    private Vector offset = new Vector(0, 0, 0);
}
