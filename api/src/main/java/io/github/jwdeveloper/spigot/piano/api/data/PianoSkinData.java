package io.github.jwdeveloper.spigot.piano.api.data;

import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import lombok.Data;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;


@Data
public class PianoSkinData
{
    private int customModelId;
    private String name;
    private ItemStack itemStack;

    public PianoSkinData(int customModelId, String name)
    {
        this.customModelId = customModelId;
        this.name = name;
    }
    public PianoSkinData(int customModelId, String  name, ItemStack itemStack)
    {
        this.customModelId = customModelId;
        this.name = name;
        this.itemStack = itemStack;

        var meta = itemStack.getItemMeta();
        if(meta != null)
        {
            meta.setCustomModelData(customModelId);
            itemStack.setItemMeta(meta);
        }

    }


    public void setColor(Color color)
    {
        if(itemStack == null)
        {
            return;
        }
        if(itemStack.getItemMeta() instanceof LeatherArmorMeta meta)
        {
            meta.setColor(color);
            itemStack.setItemMeta(meta);
        }
    }

    public ItemStack getItemStack()
    {
        if(itemStack != null)
        {
            return itemStack.clone();
        }

        itemStack =  new ItemStack(PianoPluginConsts.MATERIAL,1);
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static PianoSkinData defaultSkin()
    {
        return new PianoSkinData(0,"none");
    }
}