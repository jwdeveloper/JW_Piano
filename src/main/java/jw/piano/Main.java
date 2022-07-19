package jw.piano;


import jw.piano.data.Settings;
import jw.piano.awake_actions.PianoManager;
import jw.piano.awake_actions.WebSocketManager;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFile;


public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {
        configuration.useFilesHandler()
                .useCustomAction(new PianoManager())
                .useCustomAction(new WebSocketManager())
                .useMetrics(() ->
                {
                    var settings = FluentInjection.getInjection(Settings.class);
                    return settings.getMetrictsId();
                })
                .useDebugMode();
    }


    @Override
    protected void OnFluentPluginEnable() {

    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
