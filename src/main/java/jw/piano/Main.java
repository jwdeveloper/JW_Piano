package jw.piano;


import jw.piano.awake_actions.FileVersionAction;
import jw.piano.data.PluginConfig;
import jw.piano.awake_actions.PianoSetupAction;
import jw.piano.awake_actions.WebSocketAction;
import jw.piano.gui.MenuGUI;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;


public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {
        configuration
                .useDebugMode()
                .useFilesHandler()
                .userDefaultPermission("piano")
                .useDefaultCommand("piano", builder ->
                {
                    builder.eventsConfig(eventConfig ->
                    {
                        eventConfig.onPlayerExecute(event ->
                        {
                            var gui = FluentInjection.getPlayerInjection(MenuGUI.class, event.getPlayerSender());
                            gui.open(event.getPlayerSender());
                        });
                    });
                })
                .useMetrics(() ->
                {
                    return PluginConfig.METRICTS_ID;
                }).useUpdates((a) ->
                {
                    a.setGithub(PluginConfig.PLUGIN_UPDATE_URL);
                })
                .useCustomAction(new FileVersionAction())
                .useCustomAction(new PianoSetupAction())
                .useCustomAction(new WebSocketAction());
    }


    @Override
    protected void OnFluentPluginEnable() {


    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
