package io.github.jwdeveloper.spigot.piano.api;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class PluginConsts
{
    public final static String SPIGOT_URL = "https://www.spigotmc.org/resources/piano.103490/";

    //https://api.github.com/repos/jwdeveloper/JW_Piano/releases
    public final static String GITHUB_URL = "https://github.com/jwdeveloper/JW_Piano";

    // < 1.2.1 resource-pack=https://download.mc-packs.net/pack/4d00dcb5c0eeb65464f37ced9c0c93551cd70bdc.zip

    //resource-pack-sha1=5fb90b8870c925ec73f6debc7b7dfb18ec565ebc
    public final static String RESOURCEPACK_URL = "https://download.mc-packs.net/pack/5fb90b8870c925ec73f6debc7b7dfb18ec565ebc.zip";

    public final static String DISCORD_URL = "https://discord.gg/2hu6fPPeF7";

    public final static String CLIENT_APP_URL = "https://github.com/jwdeveloper/JW_Piano_Desktop/releases/latest/download/JW_Piano_Desktop.jar";

    public final static Integer BSTATS_ID = 15825;

    public final static NamespacedKey PIANO_NAMESPACE = NamespacedKey.minecraft("piano");
    public final static Color PARTICLE_COLOR = Color.fromRGB(92, 225, 230);
    public final static Material MATERIAL = Material.LEATHER_HORSE_ARMOR;

    public static final int MIDI_KEY_OFFSET = 21;
    public static final int PRESSED_CODE = 1;
    public static final int NOTES_NUMBER = 88;

    public static ItemStack ITEMSTACK()
    {
        return new ItemStack(PluginConsts.MATERIAL,1);
    }
}
