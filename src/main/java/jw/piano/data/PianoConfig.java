package jw.piano.data;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.config.config_sections.FluentConfigSection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlFile;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlIgnore;
import jw.spigot_fluent_api.utilites.files.yml.api.annotations.YmlProperty;
import lombok.Data;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;


@Injection
@YmlFile
@Data
public class PianoConfig implements FluentConfigSection {


    @YmlProperty(path = "piano-config",
            name = "models-limit",
            description = "Limit of pianos that could be spawn on the server")
    private int pianoInstancesLimit = 10;
    @YmlProperty(path = "piano-config")
    private float minDistanceToPiano = 3;
    @YmlProperty(path = "piano-config")
    private float maxDistanceFromPiano = 3;
    @YmlProperty(path = "piano-config")
    private float maxDistanceFromKeys = 2;

    @YmlProperty(
            path = "plugin",
            name = "auto-download-resourcepack",
            description = "automatically download texture pack when player joins to server")
    private boolean autoDownloadTexturepack = true;

    @YmlProperty(
            path = "piano-server",
            name = "enable",
            description = "runs piano server so players could join with desktop app")
    private boolean runPianoPlayerServer = true;

    @YmlProperty(
            path = "piano-server",
            name = "customer-server-ip",
            description = "When value is empty IP of your server will be detected automatically \n" +
            " Common issues: \n" +
            " - if you server IP has port ignore port WRONG -> craftplayer.com:22225  GOOD -> craftplayer.com\n" +
            " - if you are running server locally set value to 'localhost'\n" +
            " - if your server use proxy put here proxy IP\n" +
            " - if above solutions does not help set IP that players are connecting to")
    private String customServerIp = "";

    @YmlProperty(
            path = "piano-server",
            name = "port",
            description = "Make sure port is open on your hosting")
    private int webSocketPort = 2022;


    @YmlProperty(name = "custom-skins", description = "you can attache your own skin here!" +
            " just make new resourcepack and create model for `stick` with custom model ID")
    private List<PianoSkin> skins = new ArrayList<>();


    @YmlIgnore
    public final static String TEXTURES_URL = "https://download.mc-packs.net/pack/f4bfbb9e82edaa8317aa95d4d3dfafbd51157ef2.zip";
    @YmlIgnore
    public final static String CLIENT_APP_URL = "https://github.com/jwdeveloper/JW_Piano_Desktop/releases/latest/download/JW_Piano_Desktop.jar";
    @YmlIgnore
    public final static String PLUGIN_UPDATE_URL = "https://github.com/jwdeveloper/JW_Piano";
    @YmlIgnore
    public static String SERVER_IP = "";
    @YmlIgnore
    public final static Integer METRICTS_ID = 15825;
    @YmlIgnore
    public final static NamespacedKey PIANO_NAMESPACE = NamespacedKey.minecraft("piano");
    @YmlIgnore
    public final static Color PARTICLE_COLOR = Color.fromRGB(92, 225, 230);
    @YmlIgnore
    public final static Material SKINS_MATERIAL = Material.STICK;

    @Override
    public void migrate(FileConfiguration oldVersion)
    {

    }
}
