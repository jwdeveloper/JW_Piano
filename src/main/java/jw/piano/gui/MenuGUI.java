package jw.piano.gui;

import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PianoPermission;
import jw.piano.data.PianoData;
import jw.piano.data.PluginConfig;
import jw.piano.game_objects.Piano;
import jw.piano.handlers.create_piano.CreatePianoRequest;
import jw.piano.handlers.create_piano.CreatePianoResponse;
import jw.piano.service.PianoDataService;
import jw.piano.service.PianoService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.api.spigot.messages.FluentMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class MenuGUI extends CrudListUI<PianoData> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoDataService pianoDataService;
    private final PianoService pianoService;
    private final PluginConfig settings;
    private final FluentTranslator lang;

    @Inject
    public MenuGUI(PianoViewGUI pianoViewGUI,
                   PianoDataService pianoDataService,
                   PluginConfig settings,
                   PianoService pianoService,
                   FluentTranslator lang)
    {
        super(lang.get("gui.piano-menu.title"), 6);
        this.lang =lang;
        this.pianoService = pianoService;
        this.settings = settings;
        this.pianoDataService = pianoDataService;
        this.pianoViewGUI = pianoViewGUI;
    }

    @Override
    public void onInitialize() {

        pianoViewGUI.setParent(this);

        getButtonEdit().setActive(false);
        getButtonInsert().setDescription(lang.get("gui.base.insert.desc"));
        getButtonInsert().setPermissions(PianoPermission.PIANO);
        getButtonInsert().setPermissions(PianoPermission.CREATE);
        getButtonDelete().setDescription(lang.get("gui.base.delete.desc"));
        getButtonDelete().setPermissions(PianoPermission.PIANO);
        getButtonDelete().setPermissions(PianoPermission.REMOVE);
        ButtonUI.factory().backgroundButton(0,6,Material.GRAY_STAINED_GLASS_PANE)
                        .buildAndAdd(this);
        ButtonUI.builder()
                .setMaterial(Material.CAMPFIRE)
                .setHighlighted()
                .setTitle(FluentMessage.message()
                .color(org.bukkit.ChatColor.AQUA)
                .inBrackets(lang.get("gui.piano-menu.resourcepack.title")))
                .setDescription(lang.get("gui.piano-menu.resourcepack.desc"))
                .setLocation(0, 2)
                .setOnClick((player, button) ->
                {
                    player.setTexturePack(settings.TEXTURES_URL);
                })
                .buildAndAdd(this);


        ButtonUI.builder()
                .setMaterial(Material.SOUL_CAMPFIRE)
                .setHighlighted()
                .setTitle(FluentMessage.message()
                        .color(org.bukkit.ChatColor.AQUA)
                        .inBrackets(lang.get("gui.piano-menu.client-app.title")))
                .setDescription(lang.get("gui.piano-menu.client-app.desc"))
                .setLocation(0, 3)
                .setOnClick((player, button) ->
                {
                    final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "["+ lang.get("gui.piano-menu.client-app.message")+"]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.CLIENT_APP_URL));
                    player.spigot().sendMessage(message);
                    close();
                })
                .buildAndAdd(this);

        setContentButtons(pianoDataService.get(), (data, button) ->
        {
            button.setTitlePrimary(data.getName());
            button.setDescription(data.getDescriptionLines());
            if(data.getSkinId() == 0)
            {
                button.setMaterial(Material.JUKEBOX);
            }
            else
            {
                button.setCustomMaterial(PluginConfig.SKINS_MATERIAL,data.getSkinId());
            }

            button.setDataContext(data);
        });

        onInsert((player, button) ->
        {
            final var response = FluentApi.mediator().resolve(new CreatePianoRequest(player), CreatePianoResponse.class);
            if (!response.created()) {
                setTitle(FluentMessage.message().color(org.bukkit.ChatColor.DARK_RED).inBrackets(response.message()));
                return;
            }
            close();
        });
        onDelete((player, button) ->
        {
            var piano = button.<PianoData>getDataContext();
            var result = pianoDataService.delete(piano.getUuid());
            if (!result) {
                setTitle(FluentApi.translator().get("gui.base.delete.error"));
            }
            refreshContent();
        });
       onGet((player, button) ->
        {
            var pianoData = button.<PianoData>getDataContext();
            var result = pianoService.get(pianoData.getUuid());
            if (result.isEmpty()) {
                setTitle(FluentApi.translator().get("gui.piano-menu.click.error"));
                refreshContent();
                return;
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
