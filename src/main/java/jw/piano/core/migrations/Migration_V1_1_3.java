package jw.piano.core.migrations;

import jw.fluent.plugin.api.config.migrations.ConfigMigration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Migration_V1_1_3 implements ConfigMigration
{

    @Override
    public String version()
    {
        return "1.1.3";
    }

    @Override
    public void onPluginUpdate(YamlConfiguration config) {

    }

}
