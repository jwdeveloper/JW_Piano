package io.github.jwdeveloper.spigot.piano.migrations;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;


public class Migration_V1_2_3 implements ExtensionMigration {
    @Override
    public String version() {
        return "1.2.3";
    }

    @Override
    public void onUpdate(YamlConfiguration config) throws IOException {
        FluentLogger.LOGGER.success("Updating Resourcepack link");
        var path = "plugin.resourcepack.url";
        if (config.contains(path)) {
            FluentLogger.LOGGER.success("Resourcepack link updated successfully");
        }
        config.set(path, PianoPluginConsts.RESOURCEPACK_URL);
    }





}