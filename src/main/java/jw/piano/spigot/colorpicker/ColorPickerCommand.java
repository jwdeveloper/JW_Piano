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

package jw.piano.spigot.colorpicker;

import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.api.enums.ArgumentType;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;

public class ColorPickerCommand {
    public static CommandBuilder getCommand() {
       return FluentCommand.create("colors")
               .propertiesConfig(propertiesConfig ->
               {
                   propertiesConfig.setDescription("command used for internal color picker system, just ignore it");
               })
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand("page",
                            commandBuilder ->
                            {
                                commandBuilder.argumentsConfig(argumentConfig ->
                                {
                                    argumentConfig.addArgument("color", ArgumentType.TEXT);
                                });
                                commandBuilder.eventsConfig(eventConfig ->
                                {
                                    eventConfig.onPlayerExecute(event ->
                                    {
                                        var value = event.getCommandArgs()[0];
                                        var picker = FluentApi.container().findInjection(ColorPicker.class);
                                        picker.handlePageSelection(event.getPlayer(), value);
                                    });
                                });
                            });
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        if(!event.getPlayer().isOp())
                        {
                            return;
                        }
                        event.getPlayer().sendMessage("Command used for internal color picker system, just ignore it");
                    });
                });
    }

}
