package jw.generator;

import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.api.translator.implementation.TranslationGenerator;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TranslationGeneratorRunner
{
    @Test
    public void generate() throws IOException, IllegalAccessException {
        var base =  path();
        TranslationGenerator.generate(base+ FileUtility.separator()+"src\\main\\resources\\languages\\piano\\en.yml",base+ FileUtility.separator()+"src\\main\\java\\jw\\piano\\api\\data");
    }

    public String  path()
    {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length()-1);
        return path;
    }
}
