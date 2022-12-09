package jw.piano.spigot.gui;

import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermission;
import jw.piano.api.data.models.PianoData;
import jw.piano.handlers.piano.create.CreatePiano;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.services.PianoDataService;
import jw.piano.services.PianoService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@PlayerContext
@Injection(lifeTime = LifeTime.SINGLETON)
public class MenuGUI extends CrudListUI<PianoData> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoDataService pianoDataService;
    private final PianoService pianoService;
    private final FluentTranslator lang;
    private final FluentMediator mediator;

    @Inject
    public MenuGUI(PianoViewGUI pianoViewGUI,
                   PianoDataService pianoDataService,
                   PianoService pianoService,
                   FluentMediator mediator,
                   FluentTranslator lang) {
        super("pianos", 6);
        this.lang = lang;
        this.pianoService = pianoService;
        this.pianoDataService = pianoDataService;
        this.pianoViewGUI = pianoViewGUI;
        this.mediator = mediator;
    }

    @Override
    public void onInitialize() {

        pianoViewGUI.setParent(this);
        getButtonEdit().setActive(false);
        getButtonInsert().setDescription(lang.get("gui.base.insert.desc"));
        getButtonInsert().setPermissions(PluginPermission.CREATE);
        getButtonDelete().setDescription(lang.get("gui.base.delete.desc"));
        getButtonDelete().setPermissions(PluginPermission.REMOVE);
        setTitlePrimary(lang.get("gui.piano-menu.title"));
        ButtonUI.factory().backgroundButton(0, 5, Material.GRAY_STAINED_GLASS_PANE)  .buildAndAdd(this);
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
                    player.setTexturePack(PluginConsts.RESOURCEPACK_URL);
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
                    final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "[" + lang.get("gui.piano-menu.client-app.message") + "]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginConsts.CLIENT_APP_URL));
                    player.spigot().sendMessage(message);
                    close();
                })
                .buildAndAdd(this);

        setContentButtons(pianoDataService.findAll(), (data, button) ->
        {
            button.setTitlePrimary(data.getName());
            button.setDescription(data.getDescriptionLines());
            if (data.getSkinId() == 0) {
                button.setMaterial(Material.JUKEBOX);
            } else {
                button.setCustomMaterial(PluginConsts.SKINS_MATERIAL, data.getSkinId());
            }

            button.setDataContext(data);
        });

        onInsert((player, button) ->
        {
            final var response = mediator.resolve(new CreatePiano.Request(player), CreatePiano.Response.class);
            if (!response.created()) {
                setTitle(FluentMessage.message().error().inBrackets(response.message()));
                return;
            }
            close();
        });
        onDelete((player, button) ->
        {
            var piano = button.<PianoData>getDataContext();
            var result = pianoDataService.delete(piano.getUuid());
            if (!result) {
                setTitle(lang.get("gui.base.delete.error"));
            }
            refreshContent();
        });
        onGet((player, button) ->
        {
            var pianoData = button.<PianoData>getDataContext();
            var result = pianoService.find(pianoData.getUuid());
            if (result.isEmpty()) {
                setTitle(lang.get("gui.piano-menu.click.error"));
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

    public void openPianoView(Player player, Piano piano) {
        pianoViewGUI.open(player, piano);
    }
}
