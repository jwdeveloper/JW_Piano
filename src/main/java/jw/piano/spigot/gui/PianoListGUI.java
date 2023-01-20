/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot.gui;

import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.permissions.implementation.PermissionsUtility;
import jw.fluent.api.utilites.LinkMessageUtility;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.resourcepack.ResourcepackOptions;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.piano.Piano;
import jw.piano.core.mediator.piano.create.CreatePiano;
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
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.List;

@PlayerContext
@Injection
public class PianoListGUI extends CrudListUI<Piano> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoService pianoService;
    private final FluentTranslator lang;
    private final FluentMediator mediator;

    private final ResourcepackOptions resourcepackOptions;

    @Inject
    public PianoListGUI(PianoViewGUI pianoViewGUI,
                        PianoService pianoService,
                        FluentMediator mediator,
                        FluentTranslator lang,
                        ResourcepackOptions resourcepackOptions) {
        super("piano list", 6);
        this.lang = lang;
        this.pianoService = pianoService;
        this.pianoViewGUI = pianoViewGUI;
        this.mediator = mediator;
        this.resourcepackOptions = resourcepackOptions;

    }

    @Override
    public void onInitialize() {

        pianoViewGUI.setParent(this);
        setListTitlePrimary(lang.get(PluginTranslations.GUI.PIANO_LIST.TITLE));
        hideEditButton();
        getButtonInsert().setPermissions(PluginPermissions.GUI.PIANO_LIST.CREATE);
        getButtonDelete().setPermissions(PluginPermissions.GUI.PIANO_LIST.REMOVE);
        getFluentUI()
                .buttonBuilder()
                .setMaterial(Material.CAMPFIRE)
                .setHighlighted()
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.TITLE));
                    options.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.DESC));
                    options.setOnLeftClick(lang.get(PluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.CLICK.LEFT));
                    options.setOnRightClick(lang.get(PluginTranslations.GUI.PIANO_LIST.RESOURCEPACK.CLICK.RIGHT));
                })
                .setLocation(0, 2)
                .setOnLeftClick((player, button) ->
                {
                    close();
                    player.setResourcePack(resourcepackOptions.getDefaultUrl());
                    open(player);
                })
                .setOnRightClick((player, button) ->
                {
                    close();
                    LinkMessageUtility.send(player, resourcepackOptions.getDefaultUrl(), "Resourcepack URL");
                })
                .build(this);

        getFluentUI()
                .buttonBuilder()
                .setMaterial(Material.SOUL_CAMPFIRE)
                .setHighlighted()
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO_LIST.CLIENT_APP.TITLE));
                    options.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO_LIST.CLIENT_APP.DESC));
                })
                .setLocation(0, 3)
                .setOnLeftClick((player, button) ->
                {

                    final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + "[" + lang.get(PluginTranslations.GUI.PIANO_LIST.CLIENT_APP.MESSAGE) + "]");
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
                open(player);
                return;
            }
            close();
        });
        onDelete((player, button) ->
        {
            var piano = button.<Piano>getDataContext();
            var result = pianoService.delete(piano.getPianoObserver().getPianoData().getUuid());
            if (!result) {
                setTitle(lang.get(PluginTranslations.GUI.BASE.DELETE.ERROR));
            }
            loadPianos();
            open(player);
        });
        onGet((player, button) ->
        {
            var pianoData = button.<Piano>getDataContext();
            if (pianoData == null) {
                setTitle(lang.get(PluginTranslations.GUI.PIANO_LIST.CLICK.ERROR));
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
            if(skin.getItemStack().getType() == Material.AIR)
            {
                button.setMaterial(Material.NOTE_BLOCK);
            }
            else
            {
                button.setCustomMaterial(skin.getItemStack().getType(), skin.getCustomModelId());
            }


            button.setDataContext(data);
        });
    }

    public void openPianoGui(Player player, Piano piano) {

        if(!PermissionsUtility.hasOnePermission(player,PluginPermissions.GUI.PIANO.BASE))
        {
            return;
        }

        pianoViewGUI.open(player, piano);
    }
}
