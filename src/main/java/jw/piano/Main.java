package jw.piano;


import jw.piano.database.DbContext;
import jw.piano.managers.PianoManager;
import jw.piano.managers.WebClientManager;
import jw.spigot_fluent_api.database.mysql_db.MySqlDbExtention;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFile;


public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {
        configFile.get("DataBase");
        configuration.useFilesHandler()
                .useInfoMessage()
                .useCustomAction(new PianoManager())
                .useCustomAction(new WebClientManager())
                .useCustomAction(new MySqlDbExtention(DbContext.class, null))
                .useDebugMode();
    }


    @Override
    protected void OnFluentPluginEnable() {

    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
