package io.github.jwdeveloper.spigot.piano.api.data;

import lombok.Data;
import org.bukkit.util.Vector;

@Data
public class BenchData
{
    private Boolean active = true;

    private Vector offset = new Vector(0, 0, 0);
}
