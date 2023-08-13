/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.generator.resources;


import io.github.jwdeveloper.ff.core.documentation.api.models.Documentation;
import io.github.jwdeveloper.ff.core.documentation.implementation.renderer.PluginDocumentationRenderer;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.core.spigot.messages.message.MessageBuilder;
import io.github.jwdeveloper.ff.core.spigot.permissions.api.PermissionDto;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.ff.plugin.implementation.extensions.decorator.PermissionDocumentationDecorator;
import io.github.jwdeveloper.ff.tools.FluentApiMock;
import io.github.jwdeveloper.ff.tools.TaskBase;
import io.github.jwdeveloper.ff.tools.files.TemplateUtility;
import permissions.PermissionsTemplate;
import jw.piano.spigot.PianoExtension;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;

public class PluginYmlGenerator extends TaskBase {

    @Test
    public void genrateTemp() throws IOException {
        generate(true);
    }

    @Test
    public void generateActial() throws IOException {
        generate(false);
    }


    private void generate(boolean isTemp) throws IOException {
        FluentApiMock.getInstance(new PianoExtension());
        var template = """
                name: JW_Piano
                version: ${project.version}
                main: jw.piano.spigot.PianoPluginMain
                author: JW
                description: Basically It adds pianos and MIDI player to Minecraft
                api-version: 1.17
                {permissions}
                """;
        var values = new HashMap<String, Object>();
        values.put("permissions", getPermissonsContent());
        var pluginYml = TemplateUtility.generateTemplate(template, values);
        var path = "D:\\Git\\JW_Piano\\src\\main\\resources";
        var file = isTemp?"plugin_temp.yml":"plugin.yml";
        FileUtility.saveToFile(path, file, pluginYml);
    }

    private String getPermissonsContent() {
        var permissionss = FluentApi.permission().getPermissions();
        var settings = new PermissionDto(PermissionsTemplate.class, permissionss);
        var permissionGenerate = new PermissionDocumentationDecorator(settings);
        var documentation = new Documentation();
        permissionGenerate.decorate(documentation);
        return  new PluginDocumentationRenderer().render(new MessageBuilder(), documentation);
    }
}
