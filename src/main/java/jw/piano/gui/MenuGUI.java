package jw.piano.gui;

import jw.piano.data.Constants;
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
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
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
        super("Pianos", 6);
        this.pianoService = pianoService;
        this.settings = settings;
        this.pianoDataService = pianoDataService;
        this.pianoViewGUI = pianoViewGUI;
    }

    @Override
    public void onInitialize() {

        pianoViewGUI.setParent(this);

        getButtonEdit().setActive(false);

        getButtonInsert().setDescription("Creates brand a new piano");
        getButtonInsert().setPermissions(Constants.PERMISSION_MANAGER);
        getButtonDelete().setDescription("Removes a piano");
        getButtonDelete().setPermissions(Constants.PERMISSION_MANAGER);
        ButtonUI.factory().backgroundButton(0,6,Material.GRAY_STAINED_GLASS_PANE)
                        .buildAndAdd(this);
        ButtonUI.builder()
                .setMaterial(Material.CAMPFIRE)
                .setHighlighted()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Get texture pack"))
                .setDescription("If you don't 3D piano models",
                               "better click up here")
                .setLocation(0, 2)
                .setOnClick((player, button) ->
                {
                    player.setTexturePack(settings.getTexturesURL());
                })
                .buildAndAdd(this);


        ButtonUI.builder()
                .setMaterial(Material.SOUL_CAMPFIRE)
                .setHighlighted()

                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets("Get client app"))
                .setDescription("Download Piano client app to",
                        "connect your real piano ",
                        "or just playing midi files")
                .setLocation(0, 3)
                .setOnClick((player, button) ->
                {
                    final var message = new TextComponent(ChatColor.GREEN + "" + ChatColor.BOLD + "[click here to download]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.getClientAppURL()));
                    player.spigot().sendMessage(message);
                    close();
                })
                .buildAndAdd(this);



        setContentButtons(pianoDataService.get(), (data, button) ->
        {
            button.setTitlePrimary(data.getName());
            button.setDescription(data.getDescriptionLines());
            if(data.getPianoType() == PianoType.NONE)
            {
                button.setMaterial(Material.JUKEBOX);
            }
            else
            {
                button.setCustomMaterial(Material.WOODEN_HOE,data.getPianoType().getId());
            }

            button.setDataContext(data);
        });

        onInsert((player, button) ->
        {
            var location = player.getLocation().setDirection(new Vector(0, 0, 1));
            var pianoData = new PianoData();
            pianoData.setName(player.getName()+" piano");
            pianoData.setLocation(location);
            pianoData.setEnable(true);
            pianoData.setPianoType(PianoType.GRAND_PIANO);
            var result = pianoDataService.insert(pianoData);
            if (!result) {
                setTitle("Unable to create new piano");
            }
            close();
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
