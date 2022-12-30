package jw.piano.core.migrations;

import jw.fluent.plugin.api.config.migrations.ConfigMigration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class Migration_V1_2_1 implements ConfigMigration
{
    @Override
    public String version() {
        return "1.2.1";
    }

    @Override
    public void onPluginUpdate(YamlConfiguration config) throws IOException
    {
        var oldIP = config.get("plugin.resourcepack.load-on-join");
        if(oldIP == null)
        {
            return;
        }
        config.set("plugin.resourcepack.download-on-join", oldIP);
        config.set("plugin.resourcepack.load-on-join",null);

    }
}
