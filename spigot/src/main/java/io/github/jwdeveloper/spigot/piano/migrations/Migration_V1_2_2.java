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

package io.github.jwdeveloper.spigot.piano.migrations;

import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.messages.FluentMessages;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.plugin.api.config.migrations.ExtensionMigration;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Migration_V1_2_2 implements ExtensionMigration {
    @Override
    public String version() {
        return "1.2.2";
    }

    @Override
    public void onUpdate(YamlConfiguration config) throws IOException {
        movePianoData();
        updateResourepack(config);
        updateSkins(config);
    }


    public void movePianoData() {
        var pianosFile = FluentApi.getFluentApiSpigot().path() + FileUtility.separator() + "PianoData.json";
        if (FileUtility.pathExists(pianosFile)) {
            FluentLogger.LOGGER.info("Copying plugins/JW_Piano/PianoData.json file to plugins/JW_Piano/data");
            var destination =FluentApi.getFluentApiSpigot().path()+ FileUtility.separator() + "data";
            FileUtility.ensurePath(destination);
            var output = destination + FileUtility.separator() + "PianoData.json";

            File source = new File(pianosFile);
            File dest = new File(output);
            try {
                Files.move(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                FluentLogger.LOGGER.error("Unable to copy pianoData", e);
            }
        }
    }


    public void updateSkins(YamlConfiguration configuration) {
        if (!configuration.contains("skins")) {
            return;
        } else {
            configuration.set("skins", null);
        }
        FluentLogger.LOGGER.success("SKINS HAS BEEN UPDATED, custom skins have been removed");
    }

    public void updateResourepack(YamlConfiguration configuration) {
        FluentLogger.LOGGER.success("Updating resourcepack link");
        var path = "plugin.resourcepack.url";
        var oldLink = "https://download.mc-packs.net/pack/6fd6764e874d973fecd2d6debce416671399782b.zip";
        var newLink = PianoPluginConsts.RESOURCEPACK_URL;
        if (configuration.contains(path)) {
            var value = configuration.getString(path);
            var message = new FluentMessages();

            if (!value.equals(oldLink)) {
                var msg1 = new MessageBuilder().text("PianoPack Updated, and it seems you are used modified version of it").toString();
                var msg2 = new MessageBuilder().text("Remember to update your custom resourcepack to be compatible with plugin").toString();
                var builder = new MessageBuilder().text("Click to get current version of Pianopack").toTextComponent();

                message.component().withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, newLink));





                var copyMsg = new MessageBuilder().info().text("Or here to copy link").toTextComponent();
                copyMsg.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, newLink));
                for (var player : Bukkit.getOnlinePlayers()) {
                    if (!player.isOp()) {
                        continue;
                    }
                    player.sendMessage(msg1);
                    player.sendMessage(msg2);
                    player.spigot().sendMessage(builder);
                    player.spigot().sendMessage(copyMsg);
                }

                FluentLogger.LOGGER.success(msg1);
                FluentLogger.LOGGER.success(msg2);
                FluentLogger.LOGGER.success(builder.getText(), newLink);
                FluentLogger.LOGGER.success(copyMsg.getText(), newLink);
                return;
            }
        }
        FluentLogger.LOGGER.success("Resourcepack updated successfully");
        configuration.set(path, newLink);
    }
}

/*
plugin:
  version: 1.2.2
  resourcepack:
    url: https://download.mc-packs.net/pack/6fd6764e874d973fecd2d6debce416671399782b.zip
 */