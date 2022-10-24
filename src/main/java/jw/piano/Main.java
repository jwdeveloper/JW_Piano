package jw.piano;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.implementation.FluentAPI;
import jw.piano.awake_actions.FileVersionAction;
import jw.piano.data.PluginConfig;
import jw.piano.awake_actions.PianoSetupAction;
import jw.piano.awake_actions.WebSocketAction;
import jw.piano.gui.MenuGUI;
import jw.piano.test.PianoGameObject;
import jw.fluent_api.spigot.gameobjects.implementation.GameObjectManager;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.PluginConfiguration;
import jw.fluent_plugin.implementation.config.ConfigFile;
import org.bukkit.Bukkit;

public final class Main extends FluentPlugin {
    @Override
    protected void OnConfiguration(PluginConfiguration configuration, ConfigFile configFile) {
        configuration
                .useDebugMode()
                .useFilesHandler()
                .configureDependencyInjection(containerBuilder ->
                {
                    containerBuilder.register(Main.class, LifeTime.SINGLETON,(c)->
                    {
                        return this;
                    });
                })
                .configurePlugin(pluginOptions ->
                {
                    pluginOptions.useDefaultNamespace("piano");
                    pluginOptions.useMetrics(PluginConfig.METRICTS_ID);
                    pluginOptions.useUpdate(PluginConfig.PLUGIN_UPDATE_URL);
                })
                .useDefaultCommand("piano", builder ->
                {
                    builder.eventsConfig(eventConfig ->
                    {
                        eventConfig.onPlayerExecute(event ->
                        {
                            var gui = FluentAPI.spigot().playerContext().find(MenuGUI.class, event.getPlayer());
                            gui.open(event.getPlayer());
                        });
                    });
                })
                .useCustomAction(new FileVersionAction())
                .useCustomAction(new PianoSetupAction())
                .useCustomAction(new WebSocketAction());
    }


    @Override
    protected void OnFluentPluginEnable()
    {

        for (var player : Bukkit.getOnlinePlayers()) {
            var loc = player.getLocation();
            loc.setPitch(0);
            loc.setYaw(0);
            GameObjectManager.register(new PianoGameObject(), loc);
        }
    }

    @Override
    protected void OnFluentPluginDisable() {

    }
}
