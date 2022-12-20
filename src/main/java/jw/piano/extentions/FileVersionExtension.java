package jw.piano.extentions;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.config.PluginConfig;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.files.implementation.yml.implementation.YmlConfigurationImpl;
import jw.piano.services.PianoService;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileVersionExtension implements FluentApiExtension {


    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var config = FluentApi.container().findInjection(PluginConfig.class);
        var oldConfigPath = FluentApi.path() + File.separator + "Settings.yml";
        if (FileUtility.pathExists(oldConfigPath)) {
            FluentLogger.LOGGER.info("old config detected, moving data from Settings.yml to config.yml");
            var file = new File(oldConfigPath);
            var oldConfig = YamlConfiguration.loadConfiguration(file);
            migrateSettingsConfig(config, FluentApi.config(),oldConfig);
            Files.delete(Path.of(oldConfigPath));
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {
        var service =  FluentApi.container().findInjection(PianoService.class);
        for (var piano : service.findAll()) {
            piano.destroy();
        }
    }

    public void migrateSettingsConfig(PluginConfig pluginConfig, FluentConfig currentConfig, YamlConfiguration oldConfig) throws IllegalAccessException {
        new YmlConfigurationImpl().toConfiguration(pluginConfig, currentConfig.config());
        currentConfig.save();
    }


}
