package jw.piano;


import jw.piano.managers.PianoManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFile;
import jw.spigot_fluent_api.fluent_message.MessageBuilder;
import org.bukkit.ChatColor;


public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {
        configuration
                .useDataContext()
                .useInfoMessage()
                .useCustomAction(new PianoManager())
                //.useCustomAction(new PianoWebSocketManager())
                .useDebugMode();
    }

    @Override
    protected void OnFluentPluginEnable() {

    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
