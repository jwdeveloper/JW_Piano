/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.api.data;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.SoundCategory;
import org.bukkit.inventory.ItemStack;

public class PluginConsts
{
    public final static String SPIGOT_URL = "https://www.spigotmc.org/resources/piano.103490/";

    //https://api.github.com/repos/jwdeveloper/JW_Piano/releases
    public final static String GITHUB_URL = "https://github.com/jwdeveloper/JW_Piano";

    // < 1.2.1 resource-pack=https://download.mc-packs.net/pack/4d00dcb5c0eeb65464f37ced9c0c93551cd70bdc.zip

    //resource-pack-sha1=4d00dcb5c0eeb65464f37ced9c0c93551cd70bdc
    public final static String RESOURCEPACK_URL = "https://download.mc-packs.net/pack/5637db2609d0c73c45b80614db98053147e598ef.zip";



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
