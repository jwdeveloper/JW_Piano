package jw.piano.api.data.models;

import lombok.Data;
import org.bukkit.util.Vector;

@Data
public class BenchSettings
{
    private Boolean active = true;

    private Vector offset = new Vector(0, 0, 0);
}
