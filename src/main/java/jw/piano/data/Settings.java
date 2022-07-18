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
    private boolean runPianoPlayerServer = true;
    private boolean autoDownloadTexturepack = true;
    private String texturesURL = "https://download.mc-packs.net/pack/f4bfbb9e82edaa8317aa95d4d3dfafbd51157ef2.zip";
    private String clientAppURL = "https://github.com/jwdeveloper/JW_Piano_Client/releases/download/V1.1/JWPiano_Client.exe";
    private String serverURL = "";
    private int webSocketPort = 2022;
}
