package jw.piano.data.config;

import jw.fluent.api.files.implementation.yml.api.annotations.YmlProperty;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.PluginConsts;
import lombok.Data;
import org.bukkit.Material;
import org.bukkit.SoundCategory;

@Data
public class ResourcePackConfig {

    @YmlProperty(name = "item", description = """
            ! optional For custom Resourcepack!
            when your resoucepack use different item for piano modes change it
            Default value: STICK
            """)
    private String materialName = PluginConsts.MATERIAL.name();


    @YmlProperty(name = "sound-path", description = """
            ! optional For custom Resourcepack!
            by the default piano sounds are located inside assets/minecraft/sounds/  
            if you want change it edit `minecraft` to your resourcepack sounds location
            Default value: minecraft
            """)
    private String soundsPath = PluginConsts.SOUND_PATH;


    @YmlProperty(name = "sound-category", description = """
            Sounds category you can find inside your minecraft game/Options/Music & Sounds options
            by default piano use Voice/Speech category
            """)
    private String soundCategoryName = PluginConsts.SOUND_CATEGORY.name();


    public Material getMaterial() {
        if (StringUtils.isNullOrEmpty(materialName)) {
            showMaterialNotFoundWarning();
            return PluginConsts.MATERIAL;
        }

        var material = Material.getMaterial(materialName);
        if (material == null) {
            showMaterialNotFoundWarning();
            return PluginConsts.MATERIAL;
        }

        return material;
    }

    public SoundCategory getSoundCategory()
    {
        return PluginConsts.SOUND_CATEGORY;
    }

    private void showMaterialNotFoundWarning() {
        FluentLogger.LOGGER.warning("Custom item from 3D models has incorrect name of is empty therefor default item is used: ", PluginConsts.MATERIAL.name());
    }


}
