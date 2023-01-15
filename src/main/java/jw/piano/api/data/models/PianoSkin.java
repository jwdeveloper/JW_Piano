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

package jw.piano.api.data.models;


import jw.piano.api.data.PluginConsts;

import lombok.Data;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@Data
public class PianoSkin
{
    private int customModelId;
    private String name;
    private ItemStack itemStack;

    public PianoSkin(int customModelId, String name)
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

        itemStack =  new ItemStack(PluginConsts.MATERIAL,1);
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static PianoSkin defaultSkin()
    {
        return new PianoSkin(0,"none");
    }
}
