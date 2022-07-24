package jw.piano.data;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlIgnore;
import lombok.Data;


@Injection
@YmlFile(globalPath = "Settings")
@Data
public class Settings
{
    private int pianoInstancesLimit = 10;
    private float minDistanceToPiano = 3;
    private float maxDistanceFromPiano = 3;
    private float maxDistanceFromKeys = 2;
    private boolean autoDownloadTexturepack = true;

    private boolean runPianoPlayerServer = true;
    private String customServerIp = "";
    private int webSocketPort = 2022;

    @YmlIgnore
    private String texturesURL = "https://download.mc-packs.net/pack/f4bfbb9e82edaa8317aa95d4d3dfafbd51157ef2.zip";
    @YmlIgnore
    private String clientAppURL = "https://github.com/jwdeveloper/JW_Piano_Desktop/releases/latest/download/JW_Piano_Desktop.jar";
    @YmlIgnore
    private String pluginAppURL = "https://github.com/jwdeveloper/JW_Piano";
    @YmlIgnore
    private String serverIp = "";
    @YmlIgnore
    private Integer metrictsId = 15825;

}
