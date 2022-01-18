package jw.piano.data;
import jw.spigot_fluent_api.data.annotation.files.YmlFile;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import lombok.Data;


@Injection
@YmlFile
@Data
public class Settings
{
    private float minDistanceToPiano = 3;
    private float maxDistanceFromPiano = 3;
    private String texturesURL = "https://drive.google.com/file/d/1HClLdDwtPe7EaZjIL8vtGhoHvvipWjDj/view?usp=sharing";
    private float maxDistanceFromKeys = 2;
    private boolean midiPlayer = false;
    private int webSocketPort = 2022;
}
