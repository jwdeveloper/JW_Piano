package jw.piano.core.migrations;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.plugin.api.config.migrations.ConfigMigration;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.data.PluginConsts;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
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