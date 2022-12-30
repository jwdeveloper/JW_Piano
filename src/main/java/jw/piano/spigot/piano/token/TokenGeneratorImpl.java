package jw.piano.spigot.piano.token;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
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

@Injection
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
            FluentMessage.message().color(org.bukkit.ChatColor.AQUA).bold().inBrackets(PluginTranslations.GENERAL.INFO).space().
                    reset().
                    text(lang.get(PluginTranslations.GUI.PIANO.DESKTOP_CLIENT_ACTIVE.DISABLED)).send(player);
            return StringUtils.EMPTY;
        }

        var token = generate();
        if(token.equals(StringUtils.EMPTY))
        {
            FluentMessage.message().error().text(PluginTranslations.GUI.PIANO.TOKEN.ERROR);
            return token;
        }

        FluentMessage.message()
                .color(org.bukkit.ChatColor.AQUA)
                .bold()
                .inBrackets(PluginTranslations.GENERAL.INFO)
                .space()
                .reset()
                .text(lang.get(PluginTranslations.GUI.PIANO.TOKEN.MESSAGE_1)).send(player);


        final var desktopAppMessage = FluentMessage.message()
                .text(ChatColor.AQUA)
                .text(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(lang.get(PluginTranslations.GUI.PIANO_LIST.CLIENT_APP.MESSAGE))
                .toTextComponent();
        desktopAppMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginConsts.CLIENT_APP_URL));


        final var tokenCopyMessage = FluentMessage.message()
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
        player.spigot().sendMessage(tokenCopyMessage);
        player.sendMessage(" ");
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
