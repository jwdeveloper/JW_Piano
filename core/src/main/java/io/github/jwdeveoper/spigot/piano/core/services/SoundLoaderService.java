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

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.plugin.implementation.config.options.ConfigOptions;
import io.github.jwdeveloper.spigot.piano.api.PluginConsts;
import io.github.jwdeveloper.spigot.piano.api.config.PianoPluginConfig;
import io.github.jwdeveloper.spigot.piano.api.config.SoundConfig;
import io.github.jwdeveloper.spigot.piano.api.data.PianoSoundData;
import lombok.Getter;
import org.bukkit.SoundCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Injection
public class SoundLoaderService {
    private final ConfigOptions<PianoPluginConfig> config;
    @Getter
    private List<PianoSoundData> sounds;

    @Getter
    private PianoSoundData defaultSound;


    @Inject
    public SoundLoaderService(ConfigOptions<PianoPluginConfig> config) {
        sounds = new ArrayList<>();
        this.config = config;
        defaultSound = createDefaultSound();
    }


    public void load()
    {
        checkDefaultSound();
        loadConfigSounds();
        for(var sound : sounds)
        {
            FluentLogger.LOGGER.info(sound.getNamespace());
        }
    }


    private void checkDefaultSound() {
        var skinConfig = config.get().getSoundsConfig();
        if (!skinConfig.isEmpty()) {
            return;
        }
        var soundConfig = new SoundConfig();
        soundConfig.setSoundCategory(defaultSound.getSoundCategory().name());
        soundConfig.setNamespace(defaultSound.getNamespace());
        soundConfig.setName(defaultSound.getName());

        skinConfig.add(soundConfig);
        config.save();
    }

    private void loadConfigSounds()
    {
          for(var soundConfig : config.get().getSoundsConfig())
          {
              var sound = createSound(soundConfig);
              sounds.add(sound);
          }
    }



    private PianoSoundData createSound(SoundConfig soundConfig)
    {
        var soundsPrefix = soundConfig.getNamespace();
        var withPedal = new HashMap<Integer, String>();
        var withoutPedal = new HashMap<Integer, String>();
        for (int i = PluginConsts.MIDI_KEY_OFFSET; i < PluginConsts.MIDI_KEY_OFFSET + PluginConsts.NOTES_NUMBER; i++) {
            withPedal.put(i, soundsPrefix + ":1c." + i);
            withoutPedal.put(i, soundsPrefix + ":1." + i);
        }

        var categoryName = soundConfig.getSoundCategory().toUpperCase();
        var category = SoundCategory.valueOf(categoryName);
        var sound = new PianoSoundData();
        sound.setNamespace(soundsPrefix);
        sound.setName(soundConfig.getName());
        sound.setSoundCategory(category);
        sound.setSoundsWithPedal(withPedal);
        sound.setSoundsWithoutPedal(withoutPedal);
        return sound;
    }

    private PianoSoundData createDefaultSound() {
        var soundsPrefix = "minecraft";
        var withPedal = new HashMap<Integer, String>();
        var withoutPedal = new HashMap<Integer, String>();
        for (int i = PluginConsts.MIDI_KEY_OFFSET; i < PluginConsts.MIDI_KEY_OFFSET + PluginConsts.NOTES_NUMBER; i++) {
            withPedal.put(i, soundsPrefix + ":1c." + i);
            withoutPedal.put(i, soundsPrefix + ":1." + i);
        }

        var sound = new PianoSoundData();
        sound.setNamespace(soundsPrefix);
        sound.setName("Default");
        sound.setSoundCategory(SoundCategory.VOICE);
        sound.setSoundsWithPedal(withPedal);
        sound.setSoundsWithoutPedal(withoutPedal);

        defaultSound = sound;
        return defaultSound;
    }


}
