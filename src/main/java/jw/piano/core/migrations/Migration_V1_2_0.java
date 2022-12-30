package jw.piano.core.migrations;

import jw.fluent.plugin.api.config.migrations.ConfigMigration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class Migration_V1_2_0 implements ConfigMigration {
    @Override
    public String version() {
        return "1.2.0";
    }

    @Override
    public void onPluginUpdate(YamlConfiguration config) throws IOException {
        var oldIP = config.get("plugin.websocket.custom-id");
        config.set("plugin.websocket.server-ip", oldIP);
        config.set("plugin.websocket.custom-id",null);

        var section = config.getConfigurationSection("piano-config");
        if(section == null)
        {
            return;
        }
        config.set("piano-config.models-limit",null);
        config.set("piano-config.minDistanceToPiano",null);
        config.set("piano-config.maxDistanceFromPiano",null);
        config.set("piano-config.maxDistanceFromKeys",null);
        config.set("piano-config",null);
    }

}
