package jw.piano.data;
import jw.spigot_fluent_api.data.implementation.annotation.files.YmlFile;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import lombok.Data;


@Injection
@YmlFile
@Data
public class Settings
{
    private float minDistanceToPiano = 3;
    private float maxDistanceFromPiano = 3;
    private float maxDistanceFromKeys = 2;
    private boolean midiPlayer = false;
    private boolean runPianoPlayerServer = true;
    private String texturesURL = "https://drive.google.com/file/d/1HClLdDwtPe7EaZjIL8vtGhoHvvipWjDj/view?usp=sharing";
    private String serverURL = "localhost";
    private int webClientPort =2021;
    private int webSocketPort = 2022;
}
