package jw.piano;

import jw.fluent_api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent_plugin.api.FluentApiBuilder;
import jw.fluent_plugin.implementation.FluentApi;
import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.config.FluentConfig;
import jw.piano.awake_actions.FileVersionAction;
import jw.piano.data.PluginConfig;
import jw.piano.awake_actions.PianoSetupAction;
import jw.piano.awake_actions.WebSocketAction;
import jw.piano.gui.MenuGUI;
import jw.piano.test.PianoGameObject;
import jw.fluent_api.spigot.gameobjects.implementation.GameObjectManager;
import org.bukkit.Bukkit;

public final class Main extends FluentPlugin {

    @Override
    public void onConfiguration(FluentApiBuilder builder) {
        builder.container()
                .addMetrics(PluginConfig.METRICTS_ID)
                .addUpdater(updaterOptions ->
                {
                    //  pluginOptions.useDefaultNamespace("piano");
                    updaterOptions.setGithub(PluginConfig.PLUGIN_UPDATE_URL);
                });

        builder.command().eventsConfig(eventConfig ->
        {
            eventConfig.onPlayerExecute(event ->
            {
                var gui = FluentApi.spigot().playerContext().find(MenuGUI.class, event.getPlayer());
                gui.open(event.getPlayer());
            });
        });
        builder.useExtention(new FileVersionAction());
        builder.useExtention(new PianoSetupAction());
        builder.useExtention(new WebSocketAction());
    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) {
        for (var player : Bukkit.getOnlinePlayers()) {
            var loc = player.getLocation();
            loc.setPitch(0);
            loc.setYaw(0);
            GameObjectManager.register(new PianoGameObject(), loc);
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) {

    }
}
