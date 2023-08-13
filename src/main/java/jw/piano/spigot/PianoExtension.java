package jw.piano.spigot;

import io.github.jwdeveloper.ff.color_picker.ColorPickerApi;
import io.github.jwdeveloper.ff.extension.websocket.FluentWebsocketApi;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.spigot.extentions.CommandsExtension;
import jw.piano.spigot.extentions.ConfigLoaderExtension;

public class PianoExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {
        builder.useMetrics(PluginConsts.BSTATS_ID)
                .useUpdater(options ->
                {
                    options.useGithub().setRepositoryName(PluginConsts.GITHUB_URL);
                })
                .useResourcePack(resourcepackOptions ->
                {
                    resourcepackOptions.setDefaultUrl(PluginConsts.RESOURCEPACK_URL);
                });



        builder.permissions().setBasePermissionName(PluginPermissions.BASE);
        builder.useExtension(new ConfigLoaderExtension());
        builder.useExtension(new CommandsExtension());
        builder.useExtension(FluentWebsocketApi.use());
        builder.useExtension(ColorPickerApi.use());
    }
}
