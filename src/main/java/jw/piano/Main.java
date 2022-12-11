package jw.piano;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.PluginConsts;
import jw.piano.data.PluginPermission;
import jw.piano.extentions.CommandsExtension;
import jw.piano.extentions.FileVersionExtension;

public final class Main extends FluentPlugin {

    //TODO Add permissions piano.* , piano.player.*
    //TODO Make bench move in range, save bench location
    //TODO Add translations for bench
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
                    options.addSection(new PluginDocumentation());
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
