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

package jw.piano.spigot.piano.token;

import io.github.jwdeveloper.ff.core.common.Emoticons;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.translator.api.FluentTranslator;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.resourcepack.LinkMessageUtility;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.token.TokenGenerator;
import jw.piano.core.mediator.piano.token_data.TokenData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

public class TokenGeneratorImpl implements TokenGenerator {

    private PianoData pianoData;
    private FluentTranslator lang;
    private FluentMediator mediator;

    @Inject
    public TokenGeneratorImpl(PianoData data, FluentTranslator translator, FluentMediator mediator) {
        this.lang = translator;
        this.pianoData = data;
        this.mediator = mediator;
    }

    public String generateAndSend(Player player) {
        if (!pianoData.getDesktopClientAllowed()) {
            FluentApi.messages()
                    .chat()
                    .color(org.bukkit.ChatColor.AQUA).bold().inBrackets(lang.get(PluginTranslations.GENERAL.INFO))
                    .space()
                    .reset()
                    .text(lang.get(PluginTranslations.GUI.PIANO.DESKTOP_CLIENT_ACTIVE.DISABLED)).send(player);
            return StringUtils.EMPTY;
        }

        var token = generate();
        if (token.equals(StringUtils.EMPTY)) {
            FluentApi.messages()
                    .chat()
                    .error()
                    .text(PluginTranslations.GUI.PIANO.TOKEN.ERROR);
            return token;
        }

        FluentApi.messages()
                .chat()
                .color(org.bukkit.ChatColor.AQUA)
                .bold()
                .inBrackets(lang.get(PluginTranslations.GENERAL.INFO))
                .space()
                .reset()
                .text(lang.get(PluginTranslations.GUI.PIANO.TOKEN.MESSAGE_1)).send(player);


        final var desktopAppMessage = FluentApi.messages()
                .chat()
                .text(ChatColor.AQUA)
                .text(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(lang.get(PluginTranslations.GUI.PIANO_LIST.CLIENT_APP.MESSAGE))
                .toTextComponent();
        desktopAppMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginConsts.CLIENT_APP_URL));


        final var tokenCopyMessage = FluentApi.messages()
                .chat()
                .text(ChatColor.AQUA)
                .text(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(lang.get(PluginTranslations.GUI.PIANO.TOKEN.CLICK_TO_COPY))
                .toTextComponent();
        tokenCopyMessage.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, token));
        final var hover = new Text(ChatColor.GRAY + lang.get(PluginTranslations.GUI.PIANO.TOKEN.MESSAGE_2));
        tokenCopyMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

        player.sendMessage(" ");
        player.spigot().sendMessage(desktopAppMessage);
        // player.spigot().sendMessage(tokenCopyMessage);
        player.sendMessage(" ");

        LinkMessageUtility.send(player, token, "Desktop app access token");
        return token;
    }

    public String generate() {
        if (!pianoData.getDesktopClientAllowed()) {
            return StringUtils.EMPTY;
        }

        final var linkRequest = new TokenData.Request(pianoData);
        final var response = mediator.resolve(linkRequest, TokenData.Response.class);
        if (response == null) {

            return StringUtils.EMPTY;
        }
        return response.getUrl();
    }
}
