package jw.generator;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.spigot.documentation.api.models.Documentation;
import jw.fluent.api.spigot.documentation.implementation.decorator.PermissionDocumentationDecorator;
import jw.fluent.api.spigot.permissions.implementation.PermissionGenerator;
import jw.fluent.api.spigot.documentation.implementation.renderer.PluginDocumentationRenderer;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.permissions.api.PermissionDto;
import jw.piano.spigot.PermissionsTemplate;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PermissionsGeneratorRunner {

    private final Class<?> template = PermissionsTemplate.class;


    @Test
    public void generate() throws IOException, IllegalAccessException {
        generatePermissionsClass();
        generatePluginYml();
    }

    @Test
    public void generatePluginYml() throws IOException {

        var settings = new PermissionDto(template, new ArrayList<>());
        var permissionGenerate = new PermissionDocumentationDecorator(settings);

        var documentation = new Documentation();

        permissionGenerate.decorate(documentation);


        var file = new File("D:\\MC\\paper_1.19\\plugins\\JW_Piano\\test.txt");

        var pluginDocumentationRenderer = new PluginDocumentationRenderer();
        var content = pluginDocumentationRenderer.render(new MessageBuilder(), documentation);
        var writer = new FileWriter(file);
        writer.write(content);
        writer.close();
    }

    @Test
    public void generatePermissionsClass() throws IOException, IllegalAccessException {
        var base =  path();
        PermissionGenerator.generate(PermissionsTemplate.class,base+ FileUtility.separator()+"src\\main\\java\\jw\\piano\\api\\data");
    }
    public String  path()
    {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length()-1);
        return path;
    }
}
