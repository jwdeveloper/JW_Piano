package jw.piano.data;

import jw.fluent_api.utilites.files.yml.api.YmlMapping;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class PianoSkin implements YmlMapping
{
    private int customModelId;

    private String name;

    //ignore
    private ItemStack itemStack;

    public PianoSkin()
    {

    }

    public PianoSkin(int modelid, String  name)
    {
       this.customModelId = modelid;
       this.name = name;
    }


    public ItemStack getItemStack()
    {
        if(itemStack != null)
        {
            return itemStack.clone();
        }

        itemStack =  new ItemStack(PluginConfig.SKINS_MATERIAL,1);
        var meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelId);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @Override
    public Map<String, Object> serialize()
    {
        var map = new HashMap<String ,Object>();
        map.put("name",name);
        map.put("custom-model-id",customModelId);
        return map;
    }

    @Override
    public Object deserialize(Map<String, Object> props)
    {

        return null;
    }
}
