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

package jw.generator.resources;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.TemplateUtility;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModelsIdGenerator {


    @Test
    public void generatesIDs() throws IOException {

        var inputPath = "D:\\Git\\JW_Piano\\pianopack\\assets\\minecraft\\models\\item\\jw";
        var outputPath = "D:\\Git\\JW_Piano\\pianopack\\assets\\minecraft\\models\\item";
        var fileName = "leather_horse_armor";


        var names = Files.find(Paths.get(inputPath),
                Integer.MAX_VALUE,
                (filePath, fileAttr) -> fileAttr.isRegularFile()).toList();


        int startId = 167072;
        var modelsTemplates = new ArrayList<String>();
        for (var name : names) {
            var modelPath = name.toString().replace(outputPath + "\\", "");

            modelPath = modelPath.replace("\\", "/");
            modelPath = modelPath.replace(".json", "");
            modelPath = "item/" + modelPath;
            modelsTemplates.add(modelTemplate(startId, modelPath));
            startId++;
        }

        var template = template(fileName, modelsTemplates);

        FileUtility.save(template, outputPath, fileName + ".json");

    }


    public String template(String minecraftItem, List<String> modelTemplates) {
        var builder = new MessageBuilder();
        for (var i = 0; i < modelTemplates.size(); i++) {

            var modeltemp = modelTemplates.get(i);
            if (i == modelTemplates.size() - 1) {
                modeltemp = modeltemp.substring(0,modeltemp.length()-2);
            }
            builder.space(5).text(modeltemp);
        }
        var values = new HashMap<String, Object>();
        values.put("content", builder.toString());
        values.put("item", minecraftItem);

        var template = """
                {
                    "parent": "minecraft:item/generated",
                    "textures": {
                        "layer0": "minecraft:item/{item}"
                    },
                    "overrides": [
                {content}
                    ]
                }
                """;
        return TemplateUtility.generateTemplate(template, values);
    }


    public String modelTemplate(Integer customID, String path) {
        var values = new HashMap<String, Object>();
        values.put("customId", customID);
        values.put("path", path);

        var tempalte = """
                   { "predicate": {  "custom_model_data": {customId} } , "model": "{path}" },
                """;
        return TemplateUtility.generateTemplate(tempalte, values);
    }
}
