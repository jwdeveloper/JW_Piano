package jw.piano.spigot;

import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermission;
import jw.piano.spigot.extentions.CommandsExtension;
import jw.piano.spigot.extentions.ConfigLoaderExtension;

public final class Main extends FluentPlugin {

    //TODO V custom-id has been changed to server-ip
    //TODO Add permissions piano.* , piano.player.*
    //TODO Add MIDI player

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.container()
                .addMetrics(PluginConsts.BSTATS_ID)
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
                    options.setUseSpigotDocumentation(true);
                    options.setUseGithubDocumentation(true);
                    options.setPermissionModel(PluginPermission.class);
                })
                .addWebSocket()
                .addPlayerContext();



        builder.permissions()
                .setBasePermissionName(PluginPermission.PIANO);

        builder.useExtension(new ConfigLoaderExtension());
        builder.useExtension(new CommandsExtension());
    }



    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {


    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }
}
