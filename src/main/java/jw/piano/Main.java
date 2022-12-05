package jw.piano;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.piano.api.data.PianoPermission;
import jw.piano.extentions.FileVersionAction;
import jw.piano.api.data.PluginConfig;
import jw.piano.extentions.PianoSetupAction;
import jw.piano.spigot.PluginDocumentation;
import jw.piano.spigot.gui.MenuGUI;
import jw.piano.spigot.temp.PianoGameObject;
import jw.fluent.api.spigot.gameobjects.implementation.GameObjectManager;
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
                }).addPlayerContext()
                .addDocumentation(options ->
                {
                    options.addDecorator(new PluginDocumentation());
                    options.setUseSpigotDocumentation(true);
                    options.setUseGithubDocumentation(true);
                    options.setPermissionModel(PianoPermission.class);
                });

        builder.command()
                .setName(PianoPermission.PIANO)
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("opens GUI where you can Create/Edit/Delete pianos");
                    propertiesConfig.setUsageMessage("/piano");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var gui = FluentApi.spigot().playerContext().find(MenuGUI.class, event.getPlayer());
                        gui.open(event.getPlayer());
                    });
                });

        builder.permissions().setBasePermissionName(PianoPermission.PIANO);

        builder.useExtention(new FileVersionAction());
        builder.useExtention(new PianoSetupAction());
        // builder.useExtention(new WebSocketAction());
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
