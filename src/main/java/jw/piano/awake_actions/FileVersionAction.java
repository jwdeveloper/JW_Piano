package jw.piano.awake_actions;

import jw.fluent_api.logger.OldLogger;
import jw.fluent_plugin.api.PluginAction;
import jw.piano.data.PluginConfig;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.config.ConfigFile;
import jw.fluent_plugin.api.options.PipelineOptions;
import jw.fluent_api.utilites.files.FileUtility;
import jw.fluent_api.utilites.files.yml.implementation.YmlConfigurationImpl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileVersionAction implements PluginAction {
    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        var config = FluentInjection.findInjection(PluginConfig.class);
        var oldConfigPath = FluentPlugin.getPath() + File.separator + "Settings.yml";
        if (FileUtility.pathExists(oldConfigPath)) {
            OldLogger.info("old config detected, moving data from Settings.yml to config.yml");
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
