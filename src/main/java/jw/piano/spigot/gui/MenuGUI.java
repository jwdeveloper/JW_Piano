package jw.piano.spigot.gui;

import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PluginConsts;
import jw.piano.data.PluginPermission;
import jw.piano.data.models.PianoData;
import jw.piano.mediator.piano.create.CreatePiano;
import jw.piano.repositories.PianoDataRepository;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.services.PianoService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.piano.spigot.gui.piano.PianoViewGUI;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@PlayerContext
@Injection
public class MenuGUI extends CrudListUI<PianoData> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoDataRepository pianoDataRepository;
    private final PianoService pianoService;
    private final FluentTranslator lang;
    private final FluentMediator mediator;

    @Inject
    public MenuGUI(PianoViewGUI pianoViewGUI,
                   PianoDataRepository pianoDataRepository,
                   PianoService pianoService,
                   FluentMediator mediator,
                   FluentTranslator lang) {
        super("pianos", 6);
        this.lang = lang;
        this.pianoService = pianoService;
        this.pianoDataRepository = pianoDataRepository;
        this.pianoViewGUI = pianoViewGUI;
        this.mediator = mediator;
    }

    @Override
    public void onInitialize() {

        pianoViewGUI.setParent(this);

        setListTitlePrimary(lang.get("gui.piano-menu.title"));

        hideEditButton();
        getButtonInsert().setPermissions(PluginPermission.CREATE);
        getButtonDelete().setPermissions(PluginPermission.REMOVE);
        addSearchStrategy("By piano name", event ->
        {
            return event.data().getName().contains(event.searchKey());
        });
        getFluentUI()
                .buttonBuilder()
                .setMaterial(Material.CAMPFIRE)
                .setHighlighted()
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano-menu.resourcepack.title"));
                    options.addDescriptionLine(lang.get("gui.piano-menu.resourcepack.desc"));
                })
                .setLocation(0, 2)
                .setOnLeftClick((player, button) ->
                {
                    player.setResourcePack(PluginConsts.RESOURCEPACK_URL);
                })
                .build(this);


        getFluentUI()
                .buttonBuilder()
                .setMaterial(Material.SOUL_CAMPFIRE)
                .setHighlighted()
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano-menu.client-app.title"));
                    options.addDescriptionLine(lang.get("gui.piano-menu.client-app.desc"));
                })
                .setLocation(0, 3)
                .setOnLeftClick((player, button) ->
                {
                    final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "[" + lang.get("gui.piano-menu.client-app.message") + "]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginConsts.CLIENT_APP_URL));
                    player.spigot().sendMessage(message);
                    close();
                })
                .build(this);

        setContentButtons(pianoDataRepository.findAll(), (data, button) ->
        {
            button.setTitlePrimary(data.getName());
            if (data.getSkinId() == 0) {
                button.setMaterial(Material.JUKEBOX);
            } else {
                button.setCustomMaterial(PluginConsts.MATERIAL, data.getSkinId());
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
            var result = pianoService.delete(piano.getUuid());
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
