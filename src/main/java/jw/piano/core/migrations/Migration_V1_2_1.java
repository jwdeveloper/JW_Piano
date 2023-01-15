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

package jw.piano.core.migrations;

import jw.fluent.plugin.api.config.migrations.ConfigMigration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class Migration_V1_2_1 implements ConfigMigration
{
    @Override
    public String version() {
        return "1.2.1";
    }

    @Override
    public void onPluginUpdate(YamlConfiguration config) throws IOException
    {
        var oldIP = config.get("plugin.resourcepack.load-on-join");
        if(oldIP == null)
        {
            return;
        }
        config.set("plugin.resourcepack.download-on-join", oldIP);
        config.set("plugin.resourcepack.load-on-join",null);

    }
}
