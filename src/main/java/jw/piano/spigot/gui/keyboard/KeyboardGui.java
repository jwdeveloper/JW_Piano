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

package jw.piano.spigot.gui.keyboard;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonStyleInfo;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.api.spigot.text_input.FluentTextInput;
import jw.fluent.plugin.api.FluentTranslations;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.piano.api.data.PluginModels;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.dto.ColorInfo;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.models.keyboard.KeyboardSettings;
import jw.piano.api.data.models.keyboard.KeyboardTrackColor;
import jw.piano.spigot.gui.ColorPickerGui;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@PlayerContext
@Injection(lifeTime = LifeTime.SINGLETON)
public class KeyboardGui extends CrudListUI<KeyboardTrackColor> {
    private final FluentChestUI fluentChestUI;
    private final ColorPickerGui colorPickerGui;
    private KeyboardSettings keyboardSettings;
    private ButtonUI defaultColorButton;
    private PianoSkin keySkin;

    public KeyboardGui(FluentChestUI fluentChestUI,
                       ColorPickerGui colorPickerGui) {
        super("keyboard", 6);
        this.fluentChestUI = fluentChestUI;
        this.colorPickerGui = colorPickerGui;
        keySkin = new PianoSkin(PluginModels.PIANO_KEY.id(), "key");
        keySkin.setColor(Color.WHITE);
    }


    public void open(Player player, KeyboardSettings keyboardSettings) {
        this.keyboardSettings = keyboardSettings;
        open(player);
    }

    @Override
    protected void onInitialize() {
        colorPickerGui.setParent(this);
        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        setListTitlePrimary(getTranslator().get(PluginTranslations.GUI.KEYBOARD.TITLE));

        hideEditButton();
        hideDeleteButton();


        var info = new ButtonStyleInfo();
        info.setDescriptionTitle(getTranslator().get(PluginTranslations.GUI.KEYBOARD.MIDI_TRACK.TITLE));
        info.setLeftClick(getTranslator().get(PluginTranslations.GUI.KEYBOARD.MIDI_TRACK.CLICK.LEFT));
        info.setDescriptionLines(List.of(getTranslator().get(PluginTranslations.GUI.KEYBOARD.MIDI_TRACK.DESC.LINE_1),
                getTranslator().get(PluginTranslations.GUI.KEYBOARD.MIDI_TRACK.DESC.LINE_2),
                getTranslator().get(PluginTranslations.GUI.KEYBOARD.MIDI_TRACK.DESC.LINE_3),
                getTranslator().get(PluginTranslations.GUI.KEYBOARD.MIDI_TRACK.DESC.LINE_4)
        ));


        var insertDescription = fluentChestUI.renderer().render(info);

        getButtonInsert().setTitle(" ");
        getButtonInsert().setDescription(insertDescription);

        onInsert((player, button) ->
        {
            keyboardSettings.getMidiTrackColors().add(new KeyboardTrackColor());
            refreshContent();
        });

        defaultColorButton = fluentChestUI.buttonBuilder()
                .setMaterial(keySkin.getItemStack().getType(), keySkin.getCustomModelId(), Color.WHITE)
                .setLocation(0, 1)
                .setDescription(descriptionInfoBuilder ->
                {
                    descriptionInfoBuilder.setTitle(getTranslator().get(PluginTranslations.GUI.KEYBOARD.DEFAULT_COLOR.TITLE));
                    descriptionInfoBuilder.setOnLeftClick(getTranslator().get(PluginTranslations.GUI.KEYBOARD.CLICK.LEFT));
                })
                .setOnLeftClick((player1, button) ->
                {
                    colorPickerGui.setItemStack(button.getItemStack());
                    colorPickerGui.onContentPicked((player2, button1) ->
                    {
                        var colorInfo = button1.<ColorInfo>getDataContext();
                        keyboardSettings.setDefaultColor(colorInfo.getColor());
                        open(player1, keyboardSettings);
                    });
                    colorPickerGui.open(player1);
                })
                .build(this);


        var trackInfo = new ButtonStyleInfo();
        trackInfo.setLeftClick(getTranslator().get(PluginTranslations.GUI.KEYBOARD.CLICK.LEFT));
        trackInfo.setRightClick(getTranslator().get(FluentTranslations.GUI.BASE.REMOVE));
        trackInfo.setShiftClick(getTranslator().get(PluginTranslations.GUI.KEYBOARD.CLICK.SHIFT));
        var description = fluentChestUI.renderer().render(trackInfo);

        onListOpen(player1 ->
        {
            defaultColorButton.setColor(keyboardSettings.getDefaultColor());
            setContentButtons(keyboardSettings.getMidiTrackColors(), (data, button) ->
            {

                button.setTitlePrimary(data.getName());
                button.setCustomMaterial(keySkin.getItemStack().getType(), keySkin.getCustomModelId());
                button.setColor(data.getColor());
                button.setDataContext(data);
                button.setDescription(description);
                button.setOnLeftClick((player, button1) ->
                {
                    colorPickerGui.setItemStack(button.getItemStack());
                    colorPickerGui.onContentPicked((p, b) ->
                    {
                        var colorInfo = b.<ColorInfo>getDataContext();
                        data.setColor(colorInfo.getColor());
                        open(player1, keyboardSettings);
                    });
                    colorPickerGui.open(player1);
                });
                button.setOnRightClick((player, button1) ->
                {
                    keyboardSettings.getMidiTrackColors().remove(data);
                    refreshContent();
                });
                button.setOnShiftClick((player, button1) ->
                {
                    close();


                    var msg = FluentMessage.message()
                            .color(org.bukkit.ChatColor.AQUA)
                            .bold()
                            .inBrackets(getTranslator().get(PluginTranslations.GENERAL.INFO))
                            .space()
                            .reset()
                            .text(getTranslator().get(PluginTranslations.GUI.KEYBOARD.CHAT.INFO))
                            .toString();
                    FluentTextInput.openTextInput(player, msg, value ->
                    {
                        try {
                            var number = Integer.parseInt(value);
                            data.setTrackId(number);
                        } catch (Exception e) {
                            FluentMessage.message().error().text(getTranslator().get(PluginTranslations.GUI.KEYBOARD.CHAT.ERROR)).send(player);
                        }
                        open(player, keyboardSettings);
                    });
                });
            });
        });


    }
}
