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

package jw.piano.core.services;

import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.PluginTranslations;
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
        return new PianoSkin(PluginModels.GRAND_PIANO.id(), lang.get(PluginTranslations.SKINS.GRAND_PIANO));
    }

    private List<PianoSkin> createDefaultSkins() {
        var result = new ArrayList<PianoSkin>();
        result.add(new PianoSkin(0, lang.get(PluginTranslations.SKINS.NONE), new ItemStack(Material.AIR)));
        result.add(new PianoSkin(PluginModels.UP_RIGHT_PIANO_CLOSE.id(), lang.get(PluginTranslations.SKINS.UPRIGHT_PIANO)));
        result.add(grandPianoSkin());
        result.add(new PianoSkin(PluginModels.ELECTRIC_PIANO.id(), lang.get(PluginTranslations.SKINS.ELECTRIC_PIANO)));
        result.add(new PianoSkin(PluginModels.GRAND_PIANO_CLOSE.id(), lang.get(PluginTranslations.SKINS.GRAND_PIANO_CLOSED)));
        return result;
    }


    public List<PianoSkin> skins() {
        return skins;
    }
}
