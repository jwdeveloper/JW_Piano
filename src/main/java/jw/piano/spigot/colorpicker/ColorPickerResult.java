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

package jw.piano.spigot.colorpicker;

import jw.fluent.api.utilites.ColorUtility;
import org.bukkit.ChatColor;

public class ColorPickerResult {
    private final String colorHex;

    public ColorPickerResult(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getAsHex() {
        return colorHex;
    }

    public net.md_5.bungee.api.ChatColor getAsBungeeChatColor() {
        return net.md_5.bungee.api.ChatColor.of(colorHex);
    }

    public String getAsBukkitChatColor()
    {

        return ColorUtility.chatColorFromHex(colorHex);
    }

    public org.bukkit.Color getAsBukkitColor()
    {

        return ColorUtility.fromHex(colorHex);
    }

    public java.awt.Color getAsJavaColor() {
        return ColorUtility.fromHexJava(colorHex);
    }
}
