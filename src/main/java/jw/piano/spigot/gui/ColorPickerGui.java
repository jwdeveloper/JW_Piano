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

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.fluent_ui.styles.ButtonStyleInfo;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.picker_list_ui.PickerUI;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.api.FluentTranslations;
import jw.piano.api.data.dto.ColorInfo;
import jw.piano.core.services.ColorsService;
import jw.piano.spigot.colorpicker.ColorPicker;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ColorPickerGui extends PickerUI<ColorInfo> {

    @Setter
    private ItemStack itemStack;

    private final FluentChestUI fluentChestUI;
    private final ColorsService colorsService;

    private final ColorPicker colorPicker;

    @Inject
    public ColorPickerGui(FluentChestUI fluentChestUI,
                          ColorsService colorsService,
                          ColorPicker colorPicker) {
        super("Color picker");

        this.fluentChestUI = fluentChestUI;
        this.colorsService = colorsService;
        this.colorPicker = colorPicker;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }


    @Override
    protected void onInitialize() {
        setListTitlePrimary(getTranslator().get(FluentTranslations.COLOR_PICKER.GUI.TITLE));


        var renderer = fluentChestUI.renderer();
        var info = new ButtonStyleInfo();
        info.setLeftClick(getTranslator().get(FluentTranslations.GUI.BASE.SELECT));
        info.setRightClick(getTranslator().get(FluentTranslations.GUI.BASE.REMOVE));
        var description = renderer.render(info);

        onListOpen(player ->
        {
            setContentButtons(colorsService.getColors(), (data, button) ->
            {
                button.setTitlePrimary(data.getName());
                button.setDataContext(data);
                button.setCustomMaterial(itemStack.getType(), itemStack.getItemMeta().getCustomModelData());
                button.setColor(data.getColor());
                button.setDescription(description);
                button.setOnRightClick((player1, button1) ->
                {
                    colorsService.removeColor(data);
                    refreshContent();
                });
            });
        });
        fluentChestUI
                .buttonBuilder()
                .setLocation(0, 7)
                .setMaterial(Material.LIME_DYE)
                .setDescription(e ->
                {
                    e.setTitle(getTranslator().get(FluentTranslations.COLOR_PICKER.GUI.ADD_COLOR.TITLE));
                    e.addDescriptionLine(getTranslator().get(FluentTranslations.COLOR_PICKER.GUI.ADD_COLOR.DESC_1)+" #FFFFFF");
                    e.setOnLeftClick(getTranslator().get(FluentTranslations.COLOR_PICKER.GUI.ADD_COLOR.TITLE));
                })
                .setOnLeftClick((player, button) ->
                {
                    close();
                    colorPicker.register(player, result ->
                    {
                        colorsService.addColor(new ColorInfo(result.getAsHex(), result.getAsBukkitColor(), false));
                        open(player);
                    });
                })
                .build(this);
    }


}
