package jw.piano;


import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.PluginConfiguration;


public final class Main extends FluentPlugin {

    @Override
    protected void OnConfiguration(PluginConfiguration configuration) {
        configuration
                .useDependencyInjection()
                .useDataContext()
                .useInfoMessage()
                .useDebugMode();
    }

    @Override
    protected void OnFluentPluginEnable() {

    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
