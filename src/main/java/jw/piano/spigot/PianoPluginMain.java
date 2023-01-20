/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot;

import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.FluentPlugin;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.spigot.extentions.CommandsExtension;
import jw.piano.spigot.extentions.ConfigLoaderExtension;
import lombok.Data;

import java.util.function.Consumer;

public final class PianoPluginMain extends FluentPlugin {

    //  /give @p minecraft:leather_horse_armor{display:{color:16711680},CustomModelData:108} 1
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
                    options.setUseGithubDocumentation(false);
                    options.setPermissionTemplate(PermissionsTemplate.class);
                })
                .addWebSocket()
                .addPlayerContext();

        builder.permissions()
                .setBasePermissionName(PluginPermissions.BASE);

        builder.useExtension(FluentScreen.extension(config ->
        {
            config.setNumberOfPlayers(12);
        }));
        builder.useExtension(new ConfigLoaderExtension());
        builder.useExtension(new CommandsExtension());
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) {


    }


    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) {

    }

    public static class FluentScreen implements FluentApiExtension
    {

        public static FluentScreen extension(Consumer<Config> consumer)
        {
            return new FluentScreen();
        }
        @Data
        public static class Config
        {
            private boolean useScreens;
            private int numberOfPlayers;
        }

        @Override
        public void onConfiguration(FluentApiSpigotBuilder builder) {

        }

        @Override
        public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

        }

        @Override
        public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

        }
    }


}
