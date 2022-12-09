package jw.piano;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermission;
import jw.piano.extentions.CommandsExtension;
import jw.piano.extentions.FileVersionExtension;
import jw.piano.spigot.PluginDocumentation;
import jw.piano.spigot.gui.MenuGUI;

public final class Main extends FluentPlugin {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container()
                .addMetrics(PluginConsts.METRICTS_ID)
                .addUpdater(options ->
                {
                    options.setGithub(PluginConsts.GITHUB_URL);
                })
                .addResourcePack(options ->
                {
                    options.setDefaultUrl(PluginConsts.RESOURCEPACK_URL);
                })
                .addDocumentation(options ->
                {
                    options.addDecorator(new PluginDocumentation());
                  //  options.setUseSpigotDocumentation(true);
                 //   options.setUseGithubDocumentation(true);
                    options.setPermissionModel(PluginPermission.class);
                })
                .addWebSocket()
                .addPlayerContext();

        builder.permissions()
                .setBasePermissionName(PluginPermission.PIANO);

        builder.useExtension(new CommandsExtension());
        builder.useExtension(new FileVersionExtension());
    }



    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }
}
