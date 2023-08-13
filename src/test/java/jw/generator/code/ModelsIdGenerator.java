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

package jw.generator.code;

import com.google.gson.JsonParser;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.core.files.FileUtility;
import io.github.jwdeveloper.ff.tools.files.code.ClassCodeBuilder;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ModelsIdGenerator {
    private static String inputPath = "D:\\Git\\JW_Piano\\pianopack\\assets\\minecraft\\models\\item";
    private static String fileName = "leather_horse_armor";
    private static String outputPath = "D:\\Git\\JW_Piano\\src\\main\\java\\jw\\piano\\api\\data";

    @Test
    public void generateIds() throws IOException {


        var yaml = loadToYml();


        var root = new ClassCodeBuilder();
        root.setClassName("PluginModels");
        root.setPackage("jw.piano.api.data");
        root.setModifiers("public");
        root.addImport("org.bukkit.Color");
        root.addImport("org.bukkit.Material");
        root.addImport("org.bukkit.inventory.ItemStack");
        root.addImport("org.bukkit.inventory.meta.LeatherArmorMeta");

        root.addField(e ->
        {
            e.setModifier("private static final");
            e.setType("Material");
            e.setName("MATERIAL");
            e.setValue("Material." + fileName.toUpperCase());
        });


        root.addClass("""
                    public record ResourceModel(int id, String name) {
                     
                             public Material getMaterial() {
                                 return MATERIAL;
                             }
                     
                             public ItemStack toItemStack() {
                                 return toItemStack(Color.WHITE);
                             }
                             public ItemStack toItemStack(Color color) {
                                     var itemStack = new ItemStack(getMaterial());
                                     var meta = itemStack.getItemMeta();
                                     if(meta == null)
                                      {
                                         return itemStack;
                                      }
                                      meta.setDisplayName(name);
                                      meta.setCustomModelData(id);
                                      if(meta instanceof LeatherArmorMeta leatherArmorMeta)
                                      {
                                         leatherArmorMeta.setColor(color);
                                      }
                                      itemStack.setItemMeta(meta);
                                      return itemStack;
                             }
                         }
                """);

        makeFields(yaml, root);
        var content = root.build();
        FileUtility.saveClassFile(content, false, "jw.piano.api.data", "PluginModels");

    }


    public void makeFields(ConfigurationSection section, ClassCodeBuilder builder) {
        if (section == null) {
            return;
        }

        if (section.contains("MODEL")) {
            builder.addField(methodCodeGenerator ->
            {
                methodCodeGenerator.setModifier("public static");
                methodCodeGenerator.setType("ResourceModel");
                methodCodeGenerator.setName("getId");
                methodCodeGenerator.setName(section.getName().toUpperCase());
                var id = section.get("id");
                var name = section.get("name");

                methodCodeGenerator.setValue("new ResourceModel(" + id + ", \"" + name + "\")");
            });
            return;
        }

        for (var key : section.getKeys(false)) {
            // FluentLogger.LOGGER.success("CLASS", key);
            if (key.contains("MODEL")) {
                continue;
            }
            var subClass = new ClassCodeBuilder();
            var className = key;
            className = className.replace("_", "");
            className = className.trim();
            className = StringUtils.capitalize(className);
            subClass.setClassName(className);
            subClass.setModifiers("public static");

            makeFields(section.getConfigurationSection(key), builder);
            // builder.addClass(subClass.build());
        }
    }

    private YamlConfiguration loadToYml() throws IOException {
        var config = new YamlConfiguration();
        var content = FileUtility.loadFileContent(Path.of(inputPath, fileName + ".json").toString());
        var json = new JsonParser().parse(content).getAsJsonObject();
        var overrides = json.getAsJsonArray("overrides");
        for (var element : overrides) {
            var jsonElement = element.getAsJsonObject();
            var modelPath = jsonElement.get("model").getAsString();
            var customId = jsonElement.get("predicate").getAsJsonObject().get("custom_model_data").getAsInt();
            var displayName = modelPath.split("\\/");
            var name = displayName[displayName.length - 1];
            name = name.replaceAll("\\_", " ");

            var yamlPath = modelPath.replace("/", ".");
            yamlPath = yamlPath.replace("item.jw.", " ");
            config.set(yamlPath + ".MODEL", true);
            config.set(yamlPath + ".name", name);
            config.set(yamlPath + ".id", customId);
        }
        return config;
    }


    public class Tree {
        private ClassCodeBuilder root;
        private List<ClassCodeBuilder> childs = new ArrayList<>();


        public Tree(ClassCodeBuilder root) {
            this.root = root;
        }

        public void addChild(ClassCodeBuilder child) {
            childs.add(child);
        }
    }

    /*
    public class PluginModels
{
    private static final Material MATERIAL;

    public class Piano
    {
        public class Electric
        {
            public static int getCustomID()
            {
                return 12;
            }

            public static String getName()
            {
                return "electric";
            }

            public static Material getMaterial()
            {
                return material;
            }

            public static ItemStack toItemStack()
            {
                return PluginModels.toItemStack(getName(),getCustomID(),Color.WHITE);
            }

            public static ItemStack toItemStack(Color color)
            {
                return PluginModels.toItemStack(getName(),getCustomID(), color);
            }
        }
    }

    private static ItemStack toItemStack(String name, int id, Color color)
    {
        return new ItemStack(Material.BLUE_DYE);
    }
}
     */

    /*
    {
    "parent": "minecraft:item/generated",
    "textures": {
        "layer0": "minecraft:item/leather_horse_armor"
    },
    "overrides": [
        { "predicate": {  "custom_model_data": 222222200 } , "model": "item/jw/bench" },
        { "predicate": {  "custom_model_data": 222222201 } , "model": "item/jw/flyingnote" },
        { "predicate": {  "custom_model_data": 222222202 } , "model": "item/jw/icons/icon" },
        { "predicate": {  "custom_model_data": 222222203 } , "model": "item/jw/key/piano_black_key" },
        { "predicate": {  "custom_model_data": 222222204 } , "model": "item/jw/key/piano_black_key_down" },
        { "predicate": {  "custom_model_data": 222222205 } , "model": "item/jw/key/piano_key" },
        { "predicate": {  "custom_model_data": 222222206 } , "model": "item/jw/key/piano_key_down" },
        { "predicate": {  "custom_model_data": 222222207 } , "model": "item/jw/pedal/piano_pedal" },
        { "predicate": {  "custom_model_data": 222222208 } , "model": "item/jw/pedal/piano_pedal_down" },
        { "predicate": {  "custom_model_data": 222222209 } , "model": "item/jw/pianist/pianist" },
        { "predicate": {  "custom_model_data": 222222210 } , "model": "item/jw/pianist/pianist_hands" },
        { "predicate": {  "custom_model_data": 222222211 } , "model": "item/jw/pianist/pianist_head" },
        { "predicate": {  "custom_model_data": 222222212 } , "model": "item/jw/piano/electric_piano" },
        { "predicate": {  "custom_model_data": 222222213 } , "model": "item/jw/piano/grand_piano" },
        { "predicate": {  "custom_model_data": 222222214 } , "model": "item/jw/piano/grand_piano_close" },
        { "predicate": {  "custom_model_data": 222222215 } , "model": "item/jw/piano/up_right_piano_close" }
    ]
}
     */
}
