package jw.piano.awake_actions;

import jw.piano.data.PluginConfig;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;
import jw.spigot_fluent_api.utilites.files.FileUtility;
import jw.spigot_fluent_api.utilites.files.yml.implementation.YmlConfigurationImpl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileVersionAction implements PluginPipeline {
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var config = FluentInjection.getInjection(PluginConfig.class);
        var oldConfigPath = FluentPlugin.getPath() + File.separator + "Settings.yml";
        if (FileUtility.pathExists(oldConfigPath)) {
            FluentLogger.info("old config detected, moving data from Settings.yml to config.yml");
            var file = new File(oldConfigPath);
            var oldConfig = YamlConfiguration.loadConfiguration(file);
            migrateSettingsConfig(config,options.getConfigFile(),oldConfig);
            Files.delete(Path.of(oldConfigPath));
        }
    }

    public void migrateSettingsConfig(PluginConfig pluginConfig, ConfigFile currentConfig, YamlConfiguration oldConfig) throws IllegalAccessException {
        pluginConfig.setCustomServerIp(oldConfig.getString("Settings.customServerIp"));
        pluginConfig.setPort(oldConfig.getInt("Settings.webSocketPort"));
        pluginConfig.setRunPianoPlayerServer(oldConfig.getBoolean("Settings.runPianoPlayerServer"));
        pluginConfig.setDownloadResourcePack(oldConfig.getBoolean("Settings.autoDownloadTexturepack"));
        pluginConfig.setPianoInstancesLimit(oldConfig.getInt("Settings.pianoInstancesLimit"));

        new YmlConfigurationImpl().toConfiguration(pluginConfig, currentConfig.config());
        currentConfig.save();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

    }
}
