package jw.piano.api.data.models;


import jw.piano.api.data.PluginConsts;

import lombok.Data;
import org.bukkit.inventory.ItemStack;

@Data
public class PianoSkin
{
    private int customModelId;
    private String name;
    private ItemStack itemStack;

    public PianoSkin(int customModelId, String  name)
    {
       this.customModelId = customModelId;
       this.name = name;
    }
    public PianoSkin(int customModelId, String  name, ItemStack itemStack)
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

    public ItemStack getItemStack()
    {
        if(itemStack != null)
        {
            return itemStack.clone();
        }

        itemStack =  new ItemStack(PluginConsts.MATERIAL,1);
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
