package jw.piano.core.services;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.plugin.api.config.FluentConfig;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.config.PluginConfig;
import jw.piano.api.data.config.SoundConfig;
import jw.piano.api.data.sounds.PianoSound;
import lombok.Getter;
import org.bukkit.SoundCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Injection
public class SoundLoaderService {
    private final PluginConfig pluginConfig;
    private final FluentConfig config;

    @Getter
    private List<PianoSound> sounds;

    @Getter
    private PianoSound defaultSound;


    @Inject
    public SoundLoaderService(PluginConfig pluginConfig, FluentConfig config) {
        sounds = new ArrayList<>();
        this.pluginConfig = pluginConfig;
        this.config = config;
        defaultSound = createDefaultSound();
    }


    public void load()
    {
        checkDefaultSound();
        loadConfigSounds();
    }


    private void checkDefaultSound() {
        if (!pluginConfig.getSoundsConfig().isEmpty()) {
            return;
        }
        var soundConfig = new SoundConfig();
        soundConfig.setSoundCategory(defaultSound.getSoundCategory().name());
        soundConfig.setNamespace(defaultSound.getNamespace());
        soundConfig.setName(defaultSound.getName());

        pluginConfig.getSoundsConfig().add(soundConfig);
        config.save(pluginConfig);
    }

    private void loadConfigSounds()
    {
          for(var soundConfig : pluginConfig.getSoundsConfig())
          {
              var sound = createSound(soundConfig);
              sounds.add(sound);
          }
    }



    private PianoSound createSound(SoundConfig soundConfig)
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
        var sound = new PianoSound();
        sound.setNamespace(soundsPrefix);
        sound.setName(soundConfig.getName());
        sound.setSoundCategory(category);
        sound.setSoundsWithPedal(withPedal);
        sound.setSoundsWithoutPedal(withoutPedal);
        return sound;
    }

    private PianoSound createDefaultSound() {
        var soundsPrefix = "minecraft";
        var withPedal = new HashMap<Integer, String>();
        var withoutPedal = new HashMap<Integer, String>();
        for (int i = PluginConsts.MIDI_KEY_OFFSET; i < PluginConsts.MIDI_KEY_OFFSET + PluginConsts.NOTES_NUMBER; i++) {
            withPedal.put(i, soundsPrefix + ":1c." + i);
            withoutPedal.put(i, soundsPrefix + ":1." + i);
        }

        var sound = new PianoSound();
        sound.setNamespace(soundsPrefix);
        sound.setName("Default");
        sound.setSoundCategory(SoundCategory.VOICE);
        sound.setSoundsWithPedal(withPedal);
        sound.setSoundsWithoutPedal(withoutPedal);

        defaultSound = sound;
        return defaultSound;
    }


}
