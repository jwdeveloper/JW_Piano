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

package io.github.jwdeveoper.spigot.piano.core.services;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.extension.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;
import io.github.jwdeveloper.spigot.piano.api.config.PianoPluginConfig;
import io.github.jwdeveloper.spigot.piano.api.config.SkinConfig;
import io.github.jwdeveloper.spigot.piano.api.data.PianoSkinData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Injection
public class SkinLoaderService {
    private final List<PianoSkinData> skins;
    private final FluentTranslator lang;
    private final ConfigOptions<PianoPluginConfig> pluginConfig;

    @Inject
    public SkinLoaderService(FluentTranslator lang,
                             ConfigOptions<PianoPluginConfig> pluginConfig) {
        this.lang = lang;
        this.pluginConfig = pluginConfig;
        skins = new ArrayList<>();
    }


    public void load() {
        checkDefaultSkins();
        loadConfigSkins();
    }

    private void checkDefaultSkins() {
        var skinConfig = pluginConfig.get().getSkinsConfigs();
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
        pluginConfig.save();
    }


    private void loadConfigSkins() {

        var skinsConfig = pluginConfig.get().getSkinsConfigs();
        for (var configSkin : skinsConfig) {
            var materialName = configSkin.getMaterial().toUpperCase();
            var material = Material.valueOf(materialName);
            var itemStack = new ItemStack(material);
            var pianoSkin = new PianoSkinData(configSkin.getCustomModelId(), configSkin.getName(), itemStack);
            skins.add(pianoSkin);
        }
    }

    public PianoSkinData grandPianoSkin() {
        return new PianoSkinData(PluginModels.GRAND_PIANO.id(), lang.get(PluginTranslations.SKINS.GRAND_PIANO));
    }

    private List<PianoSkinData> createDefaultSkins() {
        var result = new ArrayList<PianoSkinData>();
        result.add(new PianoSkinData(0, lang.get(PluginTranslations.SKINS.NONE), new ItemStack(Material.AIR)));
        result.add(new PianoSkinData(PluginModels.UP_RIGHT_PIANO_CLOSE.id(), lang.get(PluginTranslations.SKINS.UPRIGHT_PIANO)));
        result.add(grandPianoSkin());
        result.add(new PianoSkinData(PluginModels.ELECTRIC_PIANO.id(), lang.get(PluginTranslations.SKINS.ELECTRIC_PIANO)));
        result.add(new PianoSkinData(PluginModels.GRAND_PIANO_CLOSE.id(), lang.get(PluginTranslations.SKINS.GRAND_PIANO_CLOSED)));
        return result;
    }


    public List<PianoSkinData> skins() {
        return skins;
    }
}
