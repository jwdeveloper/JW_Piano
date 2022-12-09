package jw.piano.api.data;

import jw.fluent.plugin.implementation.config.config_sections.FluentConfigSection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlFile;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlProperty;
import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;


@Injection
@YmlFile
@Data
public class PluginConfig implements FluentConfigSection {


    @YmlProperty(path = "piano-config",
            name = "models-limit",
            description = "Limit of pianos that could be spawn on the server")
    private int pianoInstancesLimit = 10;

    @YmlProperty(path = "piano-config")
    private float minDistanceToPiano = 3;

    @YmlProperty(path = "piano-config")
    private float maxDistanceFromPiano = 3;

    @YmlProperty(path = "piano-config")
    private float maxDistanceFromKeys = 2;

    @Override
    public void migrate(FileConfiguration oldVersion)
    {

    }
}
