package jw.piano;


import jw.piano.managers.PianoManager;
import jw.piano.managers.PianoWebSocketManager;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFile;
import jw.spigot_fluent_api.utilites.messages.MessageBuilder;
import org.bukkit.ChatColor;


public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {
        configuration
                .useDependencyInjection()
                .useDataContext()
                .useInfoMessage()
                .useCustomAction(new PianoManager())
                //.useCustomAction(new PianoWebSocketManager())
                .useDebugMode();
    }

    @Override
    protected void OnFluentPluginEnable() {
        int value = 12;
        String playerName = "JW";
        String adasd = "My value is :" + value + " and player: " + playerName;
        var longMessage = false;
        var builder = new MessageBuilder();

        builder.color(ChatColor.GREEN)
                .text("My value is", ChatColor.AQUA);

        if (longMessage) {
            builder.text(value)
                    .text("and player", ChatColor.RED)
                    .text(playerName);
        }
        var msg = builder.toString();
    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
