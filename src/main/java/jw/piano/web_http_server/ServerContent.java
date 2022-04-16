package jw.piano.web_http_server;

import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;

public class ServerContent
{
    private static final String WEBSITE_FOLDER = "web_client";
    private static final Class CLASS = ServerContent.class;
    private static final List<String> FILES = new ArrayList<>();

   static
   {
       FILES.add("css/pianoDesign.css");
       FILES.add("index.html");
       FILES.add("scripts/piano.js");
       FILES.add("scripts/player.js");
       FILES.add("scripts/webSocketManager.js");
   }

    @SneakyThrows
    public static List<ServerResource> load()
    {
        var result = new ArrayList<ServerResource>();
        for (var file: FILES)
        {
           var file_ = tryLoadFile(file);
           if(file_.isEmpty())
           {
              throw new Exception("File "+file+" not found!!!");
           }
         result.add(file_.get());
        }
        return result;
    }

    private static Optional<ServerResource> tryLoadFile(final String fileName) throws IOException {
        var input = CLASS.getResourceAsStream("/web_client/"+fileName);
        var bytes = input.readAllBytes();
        var fileType = HttpConstants.TEXT_PLAIN;
        if(fileName.contains("script"))
        {
            fileType = HttpConstants.TEXT_SCRIPT;
        }
        if(fileName.contains("css"))
        {
            fileType = HttpConstants.TEXT_CSS;
        }

        return Optional.of(new ServerResource(bytes,fileName,fileType));
    }
}
