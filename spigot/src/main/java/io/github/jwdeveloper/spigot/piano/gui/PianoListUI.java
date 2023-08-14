package io.github.jwdeveloper.spigot.piano.gui;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.pagination.ButtonMapping;
import io.github.jwdeveloper.ff.extension.gui.prefab.simple.SimpleDataGridGUI;
import io.github.jwdeveloper.ff.extension.resourcepack.api.ResourcepackOptions;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.mediator.FluentMediator;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginPluginTranslations;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveoper.spigot.piano.core.mediator.piano.create.CreatePiano;
import io.github.jwdeveoper.spigot.piano.core.services.PianoService;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Material;

import java.util.List;


@Injection
public class PianoListUI extends SimpleDataGridGUI<Piano> {
    private final PianoService pianoService;
    private final FluentMediator mediator;
    private final ResourcepackOptions resourcepackOptions;

    public PianoListUI(PianoService pianoService,
                       FluentMediator mediator,
                       ResourcepackOptions resourcepackOptions) {
        this.pianoService = pianoService;
        this.mediator = mediator;
        this.resourcepackOptions = resourcepackOptions;
    }

    @Override
    public ButtonMapping<Piano> getContentMapping() {
        return (piano, button) ->
        {
            button.editStyleRendererOptions(options ->
            {
                options.withTitle(piano.getPianoData().getName());
            });
            var skin = piano.getSkinManager().getCurrent();
            if (skin.getItemStack().getType() == Material.AIR) {
                button.setMaterial(Material.NOTE_BLOCK);
            } else {
                button.setCustomMaterial(skin.getItemStack().getType(), skin.getCustomModelId());
            }
        };
    }

    @Override
    public List<Piano> getContentSource() {
        return pianoService.findAll();
    }

    @Override
    public void onInit(InventoryDecorator decorator, InventoryApi inventoryApi) {
        title(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.TITLE));
        // hideEditButton();
        // getButtonInsert().setPermissions(PluginPermissions.GUI.PIANO_LIST.CREATE);
        //  getButtonDelete().setPermissions(PluginPermissions.GUI.PIANO_LIST.REMOVE);


        button(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.TITLE))
                .withMaterial(Material.CAMPFIRE)
                .withHighlighted()
                .withPosition(0, 2)
                .withStyleRenderer(options ->
                {
                    options.withDescription(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.DESC));
                    options.withLeftClickInfo(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.CLICK.LEFT));
                    options.withRightClickInfo(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.CLICK.RIGHT));
                })
                .withOnLeftClick(event ->
                {
                    close();
                    event.getPlayer().setResourcePack(resourcepackOptions.getResourcepackUrl());
                    open(event.getPlayer());
                }).withOnRightClick(event ->
                {
                    close();
                    // LinkMessageUtility.send(player, resourcepackOptions.getDefaultUrl(), "Resourcepack URL");
                });


        button(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.TITLE))
                .withMaterial(Material.SOUL_CAMPFIRE)
                .withHighlighted()
                .withPosition(0, 3)
                .withStyleRenderer(options ->
                {
                    options.withDescription(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.CLIENT_APP.DESC));
                })
                .withOnLeftClick(event ->
                {
                    FluentApi.messages()
                            .component()
                            .withText(e ->
                            {
                                e.text(ChatColor.AQUA + "" + ChatColor.BOLD + "[" + translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.CLIENT_APP.MESSAGE) + "]");
                            }).withClickEvent(ClickEvent.Action.OPEN_URL, PianoPluginConsts.CLIENT_APP_URL)
                            .send(event.getPlayer());
                    close();
                });


        onCreate(event ->
        {
            final var response = mediator.resolve(new CreatePiano.Request(event.getPlayer()), CreatePiano.Response.class);
            if (!response.created()) {
                errorTitle(response.message());
                return;
            }
            close();
        });
        onDelete((event) ->
        {
            var button = event.getButton();
            var piano = button.<Piano>getDataContext();
            var result = pianoService.delete(piano.getPianoData().getUuid());
            if (!result) {
                errorTitle(translate(PianoPluginPluginTranslations.GUI.BASE.DELETE.ERROR));
            }
        });

        onSelect(event ->
        {
            var pianoData = event.getButton().<Piano>getDataContext();
            if (pianoData == null) {
                errorTitle(translate(PianoPluginPluginTranslations.GUI.PIANO_LIST.CLICK.ERROR));
                refresh();
                return;
            }
            //openPianoGui(player, pianoData);
        });
    }
}
