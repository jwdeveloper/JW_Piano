package jw.piano.gui;

import jw.piano.data.Settings;
import jw.piano.game_objects.Piano;
import jw.piano.game_objects.PianoDataObserver;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
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


    private final PianoDataObserver pianoDataObserver;
    private final Settings settings;

    @Inject
    public PianoViewGUI(Settings settings) {
        super("Piano", 5);
        this.settings = settings;
        pianoDataObserver = new PianoDataObserver();
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver.observePianoData(piano.getPianoData());
        pianoDataObserver.getLocationBind().onChange(location ->
        {
            FluentMessage.message().text("Location has been changed").text(location).send();
        });
        open(player);
    }

    @Override
    public void onInitialize() {
        this.setTitle("Piano");
        this.setBorderMaterial(Material.BLUE_STAINED_GLASS_PANE);

        ButtonUI.builder()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Teleport to piano"))
                .setLocation(2, 2)
                .setOnClick((player, button) ->
                {
                    player.teleport(pianoDataObserver.getLocationBind().get());
                })
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getEnableBind())
                .setMaterial(Material.STONE_PICKAXE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Is active"))
                .setLocation(2, 4)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getPianoTypeBind())
                .setMaterial(Material.STONE_PICKAXE)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Piano design"))
                .setLocation(2, 6)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.STONE_PICKAXE)
                .setTitle("Play MIDI file")
                .setDescription("By open this website you can connect",
                        "minecraft piano with the real one",
                        "or just play MIDI file")
                .setLocation(1, 4)
                .setOnClick((player, button) ->
                {
                    TextComponent message = new TextComponent(ChatColor.GREEN + "" + ChatColor.BOLD + "[! Click to open MIDI panel page !]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.getPianoPlayerURL()));
                    player.spigot().sendMessage(message);
                    this.close();
                })
                .buildAndAdd(this);

        ButtonUI.factory()
                .goBackButton(this, getParent())
                .buildAndAdd(this);
    }


}
