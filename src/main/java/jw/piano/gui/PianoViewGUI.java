package jw.piano.gui;

import jw.piano.data.Permissions;
import jw.piano.data.Settings;
import jw.piano.game_objects.Piano;
import jw.piano.game_objects.PianoDataObserver;
import jw.piano.handlers.web_client.WebClientLinkRequest;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Injection
public class PianoViewGUI extends ChestUI {

    private PianoDataObserver pianoDataObserver;
    private final Settings settings;

    @Inject
    public PianoViewGUI(Settings settings) {
        super("Piano", 5);
        this.settings = settings;
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoDataObserver();
        setTitle(piano.getPianoData().getName());
        open(player);
    }

    @Override
    public void onInitialize() {

        setBorderMaterial(Material.LIME_STAINED_GLASS_PANE);

        ButtonUI.builder()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Teleport"))
                .setMaterial(Material.ENDER_PEARL)
                .setLocation(2, 2)
                .setOnClick((player, button) ->
                {
                    player.teleport(pianoDataObserver.getLocationBind().get());
                })
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getEnableBind())
                .setMaterial(Material.REDSTONE_TORCH)
                .setPermissions(Permissions.PIANO,Permissions.ACTIVE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Is active"))
                .setLocation(2, 4)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getPianoTypeBind())
                .setMaterial(Material.CRAFTING_TABLE)
                .setPermissions(Permissions.PIANO, Permissions.APPEARANCE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Appearance"))
                .setLocation(3, 4)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .intSelectObserver(pianoDataObserver.getVolumeBind(),0,100,5)
                .setMaterial(Material.BELL)
                .setPermissions(Permissions.PIANO, Permissions.VOLUME)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Volume"))
                .setLocation(2, 6)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("click to copy token"))
                .setDescription("do you want connect your real piano?",
                        "No problem just past token ",
                        "to JW-PianoClient application")
                .setLocation(1, 4)
                .setOnClick((player, button) ->
                {
                    FluentMessage.message().color(org.bukkit.ChatColor.AQUA).bold().inBrackets("Info").space().
                    reset().
                     text("Copy and paste token to Piano client app").send(player);
                    player.sendMessage(" ");
                    final var msg = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + Emoticons.arrowRight+" [Download client app]");
                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.getClientAppURL()));
                    player.spigot().sendMessage(msg);


                    player.sendMessage(" ");
                    final var linkRequest = new WebClientLinkRequest(player,pianoDataObserver.getPianoData());
                    final var url = FluentMediator.resolve(linkRequest,String.class);
                    final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + Emoticons.arrowRight+" [Click to copy token]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, url));

                    final var hover = new Text(ChatColor.GRAY + "value will be copied after click");
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                    player.spigot().sendMessage(message);
                    player.sendMessage(" ");

                    close();
                })
                .buildAndAdd(this);

        ButtonUI.factory()
                .goBackButton(this, getParent())
                .buildAndAdd(this);
    }


}
