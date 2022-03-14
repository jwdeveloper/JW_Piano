package jw.piano;

import jw.piano.managers.PianoManager;
import jw.piano.managers.PianoWebSocketManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;
import jw.spigot_fluent_api.fluent_plugin.configuration.config.ConfigFile;


public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {

        int metricsId = configFile.get("metric");
        configuration
                .useDataContext()
                .useInfoMessage()
                .useMetrics(metricsId)
                .useCustomAction(new PianoManager())
                .useCustomAction(new PianoWebSocketManager())
                .useDebugMode();
    }

    @Override
    protected void OnFluentPluginEnable() {

    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
