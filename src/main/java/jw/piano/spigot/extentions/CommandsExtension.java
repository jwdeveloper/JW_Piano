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

package jw.piano.spigot.extentions;

import io.github.jwdeveloper.ff.core.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import jw.piano.api.data.PluginPermissions;
import jw.piano.spigot.CrazyKey;
import jw.piano.spigot.colorpicker.ColorPickerCommand;
import jw.piano.spigot.gui.PianoListGUI;

public class CommandsExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {


        var colorPicker = new ColorPickerCommand();
        builder.container().register(ColorPickerCommand.class, LifeTime.SINGLETON, (a) -> colorPicker);
        builder.defaultCommand()
                .setName("piano")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(PluginPermissions.COMMANDS.PIANO);
                    propertiesConfig.setDescription("base plugin commands, /piano opens piano list");
                    propertiesConfig.setUsageMessage("/piano");
                })
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(ColorPickerCommand.getCommand());
                    subCommandConfig.addSubCommand("key",commandBuilder ->
                    {
                       commandBuilder.eventsConfig(eventConfig ->
                       {
                           eventConfig.onPlayerExecute(event ->
                           {
                              new CrazyKey(event.getPlayer().getLocation().add(0,2,0));
                           });
                       });
                    });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var gui = FluentApi.playerContext().find(PianoListGUI.class, event.getPlayer());
                        gui.open(event.getPlayer());
                    });
                });
    }




    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
