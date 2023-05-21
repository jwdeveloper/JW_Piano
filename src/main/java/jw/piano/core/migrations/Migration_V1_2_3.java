package jw.piano.core.migrations;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ConfigMigration;
import jw.piano.api.data.PluginConsts;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;


public class Migration_V1_2_3 implements ConfigMigration {
    @Override
    public String version() {
        return "1.2.3";
    }

    @Override
    public void onPluginUpdate(YamlConfiguration config) throws IOException {
        updateResourepack(config);
    }


    public void updateResourepack(YamlConfiguration configuration) {
        FluentLogger.LOGGER.success("Updating Resourcepack link");
        var path = "plugin.resourcepack.url";
        if (configuration.contains(path)) {
            FluentLogger.LOGGER.success("Resourcepack link updated successfully");
        }
        configuration.set(path, PluginConsts.RESOURCEPACK_URL);
    }
}