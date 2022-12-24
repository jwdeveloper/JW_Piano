package jw.piano.api.data.config;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import jw.fluent.plugin.api.config.ConfigSection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@Injection
public class PluginConfig implements ConfigSection {

    @YamlProperty(name = "piano")
    private PianoConfig pianoConfig = new PianoConfig();

    @YamlProperty(name = "skins")
    private List<SkinConfig> skinsConfigs = new ArrayList<>();

    @YamlProperty(name = "sounds")
    private List<SoundConfig> soundsConfig = new ArrayList<>();
}
