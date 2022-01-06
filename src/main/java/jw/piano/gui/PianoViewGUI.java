package jw.piano.gui;

import jw.piano.data.PianoData;
import jw.piano.data.PianoDataObserver;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@SpigotBean
public class PianoViewGUI extends ChestUI
{
    private PianoDataObserver pianoDataObserver;

    public PianoViewGUI()
    {
        super("Piano", 5);
        pianoDataObserver = new PianoDataObserver();
    }

    public void open(Player player, PianoData pianoModel)
    {
        pianoDataObserver.observePianoData(pianoModel);
        open(player);
    }

    @Override
    public void onInitialize()
    {
        this.setTitle("Piano");
        this.setBorderMaterial(Material.YELLOW_STAINED_GLASS_PANE);
        ButtonUI.builder()
                .setTitle("Teleport to piano")
                .setLocation(2,2)
                .setOnClick((player, button) ->
                {
                    player.teleport(pianoDataObserver.getLocationBind().get());
                })
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.STONE_PICKAXE)
                .setTitle("Set active")
                .setLocation(2,4)
                .setOnClick((player, button) ->
                {
                 //  pianoModel.getEnableBind();
                })
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.STONE_PICKAXE)
                .setTitle("Set type")
                .setLocation(2,6)
                .setOnClick((player, button) ->
                {
                   //this.detail.pianoTypeBind
                })
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.STONE_PICKAXE)
                .setTitle("Play MIDI file")
                .setLocation(1,4)
                .setOnClick((player, button) ->
                {
                    TextComponent message = new TextComponent(ChatColor.GREEN+""+ChatColor.BOLD+"[! Click to open MIDI panel page !]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/"));
                    player.spigot().sendMessage(message);
                    this.close();
                })
                .buildAndAdd(this);
    }
}
