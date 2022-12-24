package jw.piano.core.services;

import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.config.PluginConfig;
import jw.piano.api.data.config.SkinConfig;
import jw.piano.api.data.models.PianoSkin;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Injection
public class SkinLoaderService {
    private final List<PianoSkin> skins;
    private final FluentTranslator lang;

    private final FluentConfig config;
    private final PluginConfig pluginConfig;

    @Inject
    public SkinLoaderService(FluentTranslator lang, PluginConfig pluginConfig, FluentConfig config) {
        this.lang = lang;
        this.pluginConfig = pluginConfig;
        this.config = config;
        skins = new ArrayList<>();
    }


    public void load()
    {
        checkDefaultSkins();
        loadConfigSkins();
    }
    private void checkDefaultSkins() {
        var skinConfig = pluginConfig.getSkinsConfigs();
        if (!skinConfig.isEmpty()) {
            return;
        }
        var defaultSkins = createDefaultSkins();
        for (var defaultSkin : defaultSkins) {
            var skin = new SkinConfig();
            skin.setCustomModelId(defaultSkin.getCustomModelId());
            skin.setMaterial(defaultSkin.getItemStack().getType().name());
            skin.setName(defaultSkin.getName());
            skinConfig.add(skin);
        }
        config.save(pluginConfig);
    }


    private void loadConfigSkins() {

        var skinsConfig = pluginConfig.getSkinsConfigs();
        for (var configSkin : skinsConfig) {
            var materialName = configSkin.getMaterial().toUpperCase();
            var material = Material.valueOf(materialName);
            var itemStack = new ItemStack(material);


            var pianoSkin = new PianoSkin(configSkin.getCustomModelId(), configSkin.getName(),itemStack);
            skins.add(pianoSkin);
        }
    }

    public PianoSkin grandPianoSkin()
    {
        return new PianoSkin(109, lang.get("skins.grand-piano"));
    }

    private List<PianoSkin> createDefaultSkins() {
        var result = new ArrayList<PianoSkin>();
        result.add(new PianoSkin(0, lang.get("skins.none"), new ItemStack(Material.AIR)));
        result.add(new PianoSkin(108, lang.get("skins.upright-piano")));
        result.add(grandPianoSkin());
        result.add(new PianoSkin(110, lang.get("skins.electric-piano")));
        return result;
    }


    public List<PianoSkin> skins() {
        return skins;
    }
}
