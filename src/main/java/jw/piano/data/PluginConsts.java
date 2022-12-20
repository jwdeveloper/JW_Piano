package jw.piano.data;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.SoundCategory;

public class PluginConsts
{
    public final static String SPIGOT_URL = "https://www.spigotmc.org/resources/piano.103490/";

    //https://api.github.com/repos/jwdeveloper/JW_Piano/releases
    public final static String GITHUB_URL = "https://github.com/jwdeveloper/JW_Piano";

    public final static String RESOURCEPACK_URL = "https://download.mc-packs.net/pack/6fd6764e874d973fecd2d6debce416671399782b.zip";

    public final static String DISCORD_URL = "https://discord.gg/2hu6fPPeF7";

    public final static String CLIENT_APP_URL = "https://github.com/jwdeveloper/JW_Piano_Desktop/releases/latest/download/JW_Piano_Desktop.jar";

    public final static Integer BSTATS_ID = 15825;

    public final static NamespacedKey PIANO_NAMESPACE = NamespacedKey.minecraft("piano");

    public final static Color PARTICLE_COLOR = Color.fromRGB(92, 225, 230);

    public final static Material MATERIAL = Material.STICK;
    public final static String SOUND_PATH = "minecraft";

    public final static SoundCategory SOUND_CATEGORY = SoundCategory.VOICE;

    public static final int MIDI_KEY_OFFSET = 21;
    public static final int PRESSED_CODE = 1;
    public static final int NOTES_NUMBER = 88;
}
