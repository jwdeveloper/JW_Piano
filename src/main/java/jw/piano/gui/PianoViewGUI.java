package jw.piano.gui;

import jw.piano.data.PianoPermission;
import jw.piano.data.Settings;
import jw.piano.game_objects.Piano;
import jw.piano.game_objects.PianoDataObserver;
import jw.piano.handlers.web_client.WebClientLinkRequest;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.fluent_gui.EventsListenerInventoryUI;
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

@Injection( lifeTime = LifeTime.TRANSIENT)
public class PianoViewGUI extends ChestUI {

    private PianoDataObserver pianoDataObserver;
    private final Settings settings;
    private ButtonUI effects;
    private ButtonUI appearance;

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
    public void setTitle(String title)
    {
        super.setTitle("["+title+"]");
    }

    @Override
    protected void onOpen(Player player) {
        refreshButton(effects);
        refreshButton(appearance);
    }

    @Override
    public void onInitialize() {

        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

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
                .setPermissions(PianoPermission.PIANO, PianoPermission.ACTIVE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Is active"))
                .setLocation(2, 4)
                .buildAndAdd(this);

        appearance=  ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getPianoTypeBind())
                .setMaterial(Material.CRAFTING_TABLE)
                .setPermissions(PianoPermission.PIANO, PianoPermission.APPEARANCE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Appearance"))
                .setLocation(3, 4)
                .buildAndAdd(this);

       effects= ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getEffectBind())
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PianoPermission.PIANO, PianoPermission.EFFECTS)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Effects"))
                .setLocation(3, 2)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .intSelectObserver(pianoDataObserver.getVolumeBind(),0,100,5)
                .setMaterial(Material.BELL)
                .setPermissions(PianoPermission.PIANO, PianoPermission.VOLUME)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Volume"))
                .setLocation(2, 6)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.PAPER)
                .setPermissions(PianoPermission.PIANO, PianoPermission.RENAME)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Rename"))
                .setLocation(3, 6)
                .setDescription("Set custom piano's name")
                .setOnClick((player, button) ->
                {
                    this.close();
                    FluentMessage.message()
                            .color(org.bukkit.ChatColor.AQUA)
                            .bold()
                            .inBrackets("Piano info")
                            .space().
                            reset().
                            text("Write new piano's name on the chat").send(player);
                    EventsListenerInventoryUI.registerTextInput(player,s ->
                    {
                        pianoDataObserver.getPianoData().setName(s);
                        setTitle(s);
                        this.open(player);
                    });
                })
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("click to copy token"))
                .setDescription("Do you want connect your real piano?",
                        "No problem just past token ",
                        "to JW-PianoClient application")
                .setLocation(1, 4)
                .setOnClick((player, button) ->
                {
                    FluentMessage.message().color(org.bukkit.ChatColor.AQUA).bold().inBrackets("Piano info").space().
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
