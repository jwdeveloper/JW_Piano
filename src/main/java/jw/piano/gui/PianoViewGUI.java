package jw.piano.gui;

import jw.dependency_injection.Injectable;
import jw.dependency_injection.InjectionType;
import jw.gui.examples.ChestGUI;
import jw.gui.examples.chestgui.bindingstrategies.examples.SelectEnumStrategy;
import jw.piano.data.PianoData;
import jw.piano.utility.PianoTypes;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;

@Injectable(injectionType = InjectionType.TRANSIENT)
public class PianoViewGUI extends ChestGUI<PianoData>
{
    public PianoViewGUI() {
        super("Piano", 5);
    }

    @Override
    public void onInitialize() {
        this.setTitle("Piano");
        this.drawBorder(Material.YELLOW_STAINED_GLASS_PANE);
        this.addBackArrow();

        this.buildButton()
                .setBoldName("Teleport to piano")
                .setPosition(2,2)
                .setOnClick((player1, button) ->
                {
                       player1.teleport(this.detail.location);
                })
                .buildAndAdd();

        this.buildButton()
                .setMaterial(Material.STONE_PICKAXE)
                .setBoldName("Set active")
                .setPosition(2,4)
                .bindField(this.detail.isEnableBind)
                .buildAndAdd();

        this.buildButton()
                .setMaterial(Material.STONE_PICKAXE)
                .setBoldName("Set type")
                .setPosition(2,6)
                .bindField(new SelectEnumStrategy(this.detail.pianoTypeBind, PianoTypes.class))
                .buildAndAdd();


        this.buildButton()
                .setMaterial(Material.STONE_PICKAXE)
                .setBoldName("Play MIDI file")
                .setPosition(1,4)
                .setOnClick((player1, button) ->
                {
                    TextComponent message = new TextComponent(ChatColor.GREEN+""+ChatColor.BOLD+"[! Click to open MIDI panel page !]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.youtube.com/"));
                    player.spigot().sendMessage(message);
                    this.close();
                })
                .buildAndAdd();
    }
}
