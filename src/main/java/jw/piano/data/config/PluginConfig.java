package jw.piano.data.config;

import jw.fluent.plugin.implementation.config.config_sections.FluentConfigSection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlFile;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlProperty;
import lombok.Data;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;


@Injection
@YmlFile
@Data
public class PluginConfig implements FluentConfigSection {


    @YmlProperty(path = "piano")
    private PianoConfig pianoConfig = new PianoConfig();

    @YmlProperty(path = "plugin.resourcepack")
    private ResourcePackConfig resourcePackConfig = new ResourcePackConfig();

    @Override
    public void migrate(FileConfiguration oldVersion)
    {

    }
}
