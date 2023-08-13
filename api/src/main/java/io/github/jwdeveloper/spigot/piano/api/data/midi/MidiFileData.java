package io.github.jwdeveloper.spigot.piano.api.data.midi;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Material;

@Data
@AllArgsConstructor
public class MidiFileData
{
    private String path;
    private String name;
    private Material icon = Material.MUSIC_DISC_13;
    private int index = 0;
    private boolean isCorrupted;
}
