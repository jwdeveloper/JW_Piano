package jw.piano.gui;

import jw.piano.data.Settings;
import jw.piano.game_objects.Piano;
import jw.piano.game_objects.PianoDataObserver;
import jw.piano.handlers.web_clinet.WebClientLinkRequest;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverUI;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
        open(player);
    }

    @Override
    public void onInitialize() {
        this.setTitle("Piano");
        this.setBorderMaterial(Material.BLUE_STAINED_GLASS_PANE);

        ButtonUI.builder()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Teleport"))
                .setMaterial(Material.ENDER_EYE)
                .setLocation(2, 2)
                .setOnClick((player, button) ->
                {
                    player.teleport(pianoDataObserver.getLocationBind().get());
                })
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getEnableBind())
                .setMaterial(Material.REDSTONE_TORCH)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Is active"))
                .setLocation(2, 4)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getPianoTypeBind())
                .setMaterial(Material.CRAFTING_TABLE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Appearance"))
                .setLocation(3, 4)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .intSelectObserver(pianoDataObserver.getVolumeBind(),0,100,5)
                .setMaterial(Material.BELL)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Volume"))
                .setLocation(2, 6)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.MUSIC_DISC_CAT)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("click to copy token"))
                .setDescription("do you want connect your real piano?",
                        "No problem just past token ",
                        "to  JW-PianoClient application")
                .setLocation(1, 4)
                .setOnClick((player, button) ->
                {
                    final var linkRequest = new WebClientLinkRequest(player,pianoDataObserver.getPianoData());
                    final var url = FluentMediator.resolve(linkRequest,String.class);

                    final var message = new TextComponent(ChatColor.GREEN + "" + ChatColor.BOLD + "[click to copy token]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, url));
                    player.spigot().sendMessage(message);
                    player.sendMessage("Tutorial -> www.piano.com");
                    close();
                })
                .buildAndAdd(this);


        ButtonUI.factory()
                .goBackButton(this, getParent())
                .buildAndAdd(this);
    }


}
