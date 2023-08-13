package io.github.jwdeveloper.spigot.piano.api.data.keyboard;

import lombok.Data;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

@Data
public class KeyboardData
{
    private Color defaultColor = Color.AQUA;

    private List<KeyboardTrackColorData> midiTrackColors = new ArrayList<>();
}
