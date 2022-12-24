package jw.piano.spigot.gui;

import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermission;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.Piano;
import jw.piano.core.mediator.piano.create.CreatePiano;
import jw.piano.core.repositories.PianoDataRepository;
import jw.piano.core.services.PianoService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
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
public class MenuGUI extends CrudListUI<Piano> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoService pianoService;
    private final FluentTranslator lang;
    private final FluentMediator mediator;

    @Inject
    public MenuGUI(PianoViewGUI pianoViewGUI,
                   PianoService pianoService,
                   FluentMediator mediator,
                   FluentTranslator lang) {
        super("pianos", 6);
        this.lang = lang;
        this.pianoService = pianoService;
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


        onListOpen(player ->
        {
            loadPianos();
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
            var piano = button.<Piano>getDataContext();
            var result = pianoService.delete(piano.getPianoObserver().getPianoData().getUuid());
            if (!result) {
                setTitle(lang.get("gui.base.delete.error"));
            }
            loadPianos();
        });
        onGet((player, button) ->
        {
            var pianoData = button.<Piano>getDataContext();
            if (pianoData == null) {
                setTitle(lang.get("gui.piano-menu.click.error"));
                refreshContent();
                return;
            }
            openPianoGui(player,pianoData);
        });

        onListOpen(player ->
        {
            refreshContent();
        });
    }

    public void loadPianos()
    {
        setContentButtons(pianoService.findAll(), (data, button) ->
        {
            button.setTitlePrimary(data.getPianoObserver().getPianoData().getName());

            var skin = data.getSkinManager().getCurrent();
            button.setCustomMaterial(skin.getItemStack().getType(), skin.getCustomModelId());
            button.setDataContext(data);
        });
    }

    public void openPianoGui(Player player, Piano piano) {
        pianoViewGUI.open(player, piano);
    }
}
