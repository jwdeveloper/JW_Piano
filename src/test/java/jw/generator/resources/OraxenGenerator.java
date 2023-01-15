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

import com.google.gson.JsonParser;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.TemplateUtility;
import jw.piano.api.data.PluginConsts;
import org.bukkit.Material;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class OraxenGenerator {


    @Test
    public void GenerateOraxenTemplate() throws IOException {
        var path = "D:\\Git\\JW_Piano\\pianopack\\assets\\minecraft\\models\\item";
        var modelName = "leather_horse_armor.json";


        var content = FileUtility.loadFileContent(Path.of(path, modelName).toString());
        var json = new JsonParser().parse(content).getAsJsonObject();
        var overrides = json.getAsJsonArray("overrides");

        var templates = new ArrayList<String>();
        for (var element : overrides) {
            var jsonElement = element.getAsJsonObject();
            var modelPath = jsonElement.get("model").getAsString();
            var customId = jsonElement.get("predicate").getAsJsonObject().get("custom_model_data").getAsInt();
            var displayName = modelPath.split("\\/");
            var name = displayName[displayName.length - 1];
            name = name.replaceAll("\\_", " ");
            templates.add(getTemplate(name, modelPath, customId, PluginConsts.MATERIAL));
        }

        var builder = new MessageBuilder();

        builder.textNewLine("#Generated template for Oraxen, It only contains models from pianopack");
        builder.textNewLine("#Remember to refresh config when pianopack got updated");
        builder.textNewLine("#Note that when you change LEATHER_HORSE_ARMOR to other material functionalities as Colored keys, Pianos, will not work");
        builder.newLine();
        for (var template : templates) {
            builder.textNewLine(template);
        }
        builder.newLine();
        builder.text(getNotesNamesTemplate());

        var outputPath = "D:\\Git\\JW_Piano\\src\\main\\resources\\oraxen";
        var outputName = "jw_piano_oraxen_config.yml";
        FileUtility.saveToFile(outputPath, outputName, builder.build());
    }


    public String getTemplate(String displayName, String path, int customId, Material material) {
        var values = new HashMap<String, Object>();

        values.put("name", displayName.replaceAll(" ", "-"));
        values.put("displayed_name", displayName);
        values.put("material", material.name().toUpperCase());
        values.put("customId", customId);
        values.put("model_path", path);

        var template =
                """
                        {name}:
                         displayname: {displayed_name}
                         material: {material}
                         excludeFromInventory: true
                         Pack:
                          generate_model: false
                          model: {model_path}
                          custom_model_data: {customId}   
                        """;

        return TemplateUtility.generateTemplate(template, values);
    }


    private String getNotesNamesTemplate() {
        return """
                note_a:
                  texture: icons/notes/a
                  ascent: 2
                  height: 2
                  code: 4096
                note_aSharp:
                  texture: icons/notes/a_sharp
                  ascent: 2
                  height: 2
                  code: 4097
                note_b:
                  texture: icons/notes/b
                  ascent: 2
                  height: 2
                  code: 4098
                note_c:
                  texture: icons/notes/c
                  ascent: 2
                  height: 2
                  code: 4099
                note_cSharp:
                  texture: icons/notes/c_sharp
                  ascent: 2
                  height: 2
                  code: 4100
                note_d:
                  texture: icons/notes/d
                  ascent: 2
                  height: 2
                  code: 4101
                note_dSharp:
                  texture: icons/notes/d_sharp
                  ascent: 2
                  height: 2
                  code: 4102
                note_e:
                  texture: icons/notes/e
                  ascent: 2
                  height: 2
                  code: 4103
                note_f:
                  texture: icons/notes/f
                  ascent: 2
                  height: 2
                  code: 4104
                note_fSharp:
                  texture: icons/notes/f_sharp
                  ascent: 2
                  height: 2
                  code: 4105
                note_g:
                  texture: icons/notes/g
                  ascent: 2
                  height: 2
                  code: 4112
                note_gSharp:
                  texture: icons/notes/g_sharp
                  ascent: 2
                  height: 2
                  code: 4113
                 \s
                """;

    }

}
