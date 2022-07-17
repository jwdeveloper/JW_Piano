package jw.piano.gui;

import jw.piano.data.PianoData;
import jw.piano.data.Settings;
import jw.piano.enums.PianoType;
import jw.piano.game_objects.Piano;
import jw.piano.service.PianoDataService;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.implementation.crud_list_ui.CrudListUI;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.UUID;

@Injection
public class MenuGUI extends CrudListUI<PianoData> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoDataService pianoDataService;
    private final PianoService pianoService;
    private final Settings settings;

    @Inject
    public MenuGUI(PianoViewGUI pianoViewGUI,
                   PianoDataService pianoDataService,
                   Settings settings,
                   PianoService pianoService) {
        super("Menu", 6);
        this.pianoService = pianoService;
        this.settings = settings;
        this.pianoDataService = pianoDataService;
        this.pianoViewGUI = pianoViewGUI;
        this.setEnableLogs(true);
    }

    @Override
    public void onInitialize() {
        setTitle("Menu");
        pianoViewGUI.setParent(this);

        ButtonUI.builder()
                .setMaterial(Material.MUSIC_DISC_CAT)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Get texture pack"))
                .setLocation(0, 4)
                .setOnClick((player, button) ->
                {
                    var message = new TextComponent(ChatColor.GREEN + "" + ChatColor.BOLD + "[! Click to get texture pack !]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.getTexturesURL()));
                    player.spigot().sendMessage(message);
                    this.close();
                })
                .buildAndAdd(this); ButtonUI.builder()
                .setMaterial(Material.MUSIC_DISC_CAT)
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Get texture pack"))
                .setLocation(1, 5)
                .setOnClick((player, button) ->
                {
                    var message = new TextComponent(ChatColor.GREEN + "" + ChatColor.BOLD + "[! Click to get texture pack !]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.getTexturesURL()));
                    player.spigot().sendMessage(message);
                    this.close();
                })
                .buildAndAdd(this);

        setContentButtons(pianoDataService.get(), (data, button) ->
        {
            FluentLogger.success(data.getPianoType().getId()+"");
            button.setTitle(data.getName());
            button.setDescription(data.getDescription());
            button.setCustomMaterial(Material.WOODEN_HOE,data.getPianoType().getId());
            button.setDataContext(data);
        });

        onInsert((player, button) ->
        {
            var location = player.getLocation().setDirection(new Vector(0, 0, 1));
            var pianoData = new PianoData();
            pianoData.setName("NEW PIANO");
            pianoData.setLocation(location);
            pianoData.setEnable(true);
            pianoData.setPianoType(PianoType.GRAND_PIANO);
            var result = pianoDataService.insert(pianoData);
            if (!result) {
                setTitle("Unable to create new piano");
            }
            refreshContent();
        });
        onDelete((player, button) ->
        {
            var piano = button.<PianoData>getDataContext();
            var result = pianoDataService.delete(piano.getUuid());
            if (!result) {
                setTitle("Unable to remove piano");
            }
            refreshContent();
        });
       onGet((player, button) ->
        {
            var pianoData = button.<PianoData>getDataContext();
            var result = pianoService.get(pianoData.getUuid());
            if (result.isEmpty()) {
                setTitle("Unable to find piano");
            }
            openPianoView(player, result.get());
        });

       onListOpen(player ->
       {
           refreshContent();
       });
    }

    public void openPianoView(Player player, Piano piano)
    {
        pianoViewGUI.open(player, piano);
    }
}
