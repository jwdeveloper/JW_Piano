package io.github.jwdeveloper.spigot.piano.api.data.keyboard;

import lombok.Data;
import org.bukkit.Color;


@Data
public class KeyboardTrackColorData {
    private int trackId = 0;
    private Color color = Color.WHITE;
    private String name;

    public String getName() {
        return "Midi track " + trackId;
    }

    public KeyboardTrackColorData() {

    }

    public KeyboardTrackColorData(int track, Color color, String name) {
        trackId = track;
        this.color = color;
        this.name = name;
    }

}