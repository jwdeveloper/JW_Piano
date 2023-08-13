package io.github.jwdeveloper.spigot.piano;

import io.github.jwdeveloper.ff.extension.resourcepack.FluentResourcepackApi;
import io.github.jwdeveloper.ff.extension.websocket.FluentWebsocketApi;
import io.github.jwdeveloper.ff.plugin.FluentPlugin;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import io.github.jwdeveloper.spigot.piano.commands.PluginPianoCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PianoPluginMain extends JavaPlugin {
    //  /give @p minecraft:leather_horse_armor{display:{color:16711680},CustomModelData:108} 1
    ///time set 23600


    @Override
    public void onEnable() {
        init(this);
    }


    public static FluentApiSpigot init(Plugin pluginClass) {
        return FluentPlugin.initialize(pluginClass)
                .withCommand(fluentCommandOptions ->
                {
                    fluentCommandOptions.setDefaultCommand(PluginPianoCommand.class);
                })
                .withUpdater(updaterApiOptions ->
                {
                    updaterApiOptions.getGithubOptionsBuilder().setGithubUserName("jwdeveloper").setRepositoryName("JW_Piano");
                })
                .withExtension(FluentResourcepackApi.use(resourcepackOptions ->
                {
                    resourcepackOptions.setResourcepackUrl(PianoPluginConsts.RESOURCEPACK_URL);
                }))
                .withExtension(FluentWebsocketApi.use())
                .withExtension(new PianoPluginExtension())
                .withBstatsMetrics(PianoPluginConsts.BSTATS_ID)
                .create();
    }


}
