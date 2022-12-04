package jw.piano.awake_actions;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.PluginConfig;
import jw.fluent.plugin.implementation.config.FluentConfig;
import jw.fluent.api.utilites.files.FileUtility;
import jw.fluent.api.utilites.files.yml.implementation.YmlConfigurationImpl;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileVersionAction implements FluentApiExtention {


    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        var config = FluentApi.injection().findInjection(PluginConfig.class);
        var oldConfigPath = FluentApi.path() + File.separator + "Settings.yml";
        if (FileUtility.pathExists(oldConfigPath)) {
            fluentAPI.getFluentLogger().info("old config detected, moving data from Settings.yml to config.yml");
            var file = new File(oldConfigPath);
            var oldConfig = YamlConfiguration.loadConfiguration(file);
            migrateSettingsConfig(config,fluentAPI.getFluentConfig(),oldConfig);
            Files.delete(Path.of(oldConfigPath));
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {

    }

    public void migrateSettingsConfig(PluginConfig pluginConfig, FluentConfig currentConfig, YamlConfiguration oldConfig) throws IllegalAccessException {
        pluginConfig.setCustomServerIp(oldConfig.getString("Settings.customServerIp"));
        pluginConfig.setPort(oldConfig.getInt("Settings.webSocketPort"));
        pluginConfig.setRunPianoPlayerServer(oldConfig.getBoolean("Settings.runPianoPlayerServer"));
        pluginConfig.setDownloadResourcePack(oldConfig.getBoolean("Settings.autoDownloadTexturepack"));
        pluginConfig.setPianoInstancesLimit(oldConfig.getInt("Settings.pianoInstancesLimit"));

        new YmlConfigurationImpl().toConfiguration(pluginConfig, currentConfig.config());
        currentConfig.save();
    }


}
