package jw.piano.web_http_server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import jw.piano.data.Settings;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.*;

@Injection
public class WebHttpServer {
    private HttpServer server;
    private final Settings settings;
    private final List<ServerResource> serverResources;

    @Inject
    public WebHttpServer(Settings settings) {
        this.settings = settings;
        serverResources =  ServerContent.load();

    }

    public void start() {
       /* try {
            server = HttpServer.create(new InetSocketAddress(settings.getWebClientPort()), 0);
            server.createContext("/", new FileFinder(serverResources));
            server.createContext("/piano-client", new FileHandler(serverResources));
            server.setExecutor(null);
            server.start();

            FluentPlugin.logSuccess("Website run on "+ Bukkit.getServer().getIp()+":" + settings.getWebClientPort() + "/piano-client");
        } catch (IOException e) {
            FluentLogger.error("Http server error", e);
        }*/
    }

    public void stop() {
        if (server == null)
            return;

        server.stop(0);
    }

    static class FileFinder implements HttpHandler {

        private HashMap<String, ServerResource> files;

        public FileFinder(List<ServerResource> files) {
         this.files = sortFiles(files);
        }

        public HashMap<String, ServerResource> sortFiles(List<ServerResource> files) {
            var result = new HashMap<String,ServerResource>();
            for (var file : files) {
                result.put("/"+file.name,file);
            }
            return result;
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException
        {
            var url = httpExchange.getRequestURI().toString();
           // FluentPlugin.logInfo("Request "+url);
            if(!files.containsKey(url))
            {
               NotFound(httpExchange);
               return;
            }

            Success(httpExchange,files.get(url));
        }

        public void NotFound(HttpExchange httpExchange)
        {
            try {
              //  FluentPlugin.logInfo("not found "+httpExchange.getRequestURI().toString());
                var message = "File Not found";
                httpExchange.getResponseHeaders().set(HttpConstants.CONTENT_TYPE, HttpConstants.TEXT_PLAIN);
                httpExchange.sendResponseHeaders(404,message.length());
                httpExchange.getResponseBody().write(message.getBytes());
                httpExchange.getResponseBody().close();
            } catch (Exception e)
            {
              //  FluentLogger.error("Http server error: ", e);
            }
        }
        public void Success(HttpExchange httpExchange, ServerResource serverResource)
        {
            //FluentPlugin.logInfo("Success "+serverResource.name);
            try {
                httpExchange.getResponseHeaders().set(HttpConstants.CONTENT_TYPE,serverResource.fileType);
                httpExchange.sendResponseHeaders(200, serverResource.content.length);
                httpExchange.getResponseBody().write(serverResource.content);
                httpExchange.getResponseBody().close();
            } catch (Exception e)
            {
              //  FluentLogger.error("Http server error: ", e);
            }
        }
    }

    static class FileHandler implements HttpHandler {

        private final ServerResource index;

        public FileHandler(List<ServerResource> indexHtml) {
            index = indexHtml.stream().filter(c -> c.name.equals("index.html")).findFirst().get();
        }

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            try {
                httpExchange.getResponseHeaders().set(HttpConstants.CONTENT_TYPE, HttpConstants.TEXT_PLAIN);
                httpExchange.sendResponseHeaders(200, index.content.length);
                httpExchange.getResponseBody().write(index.content);
                httpExchange.getResponseBody().close();
            } catch (Exception e) {
              //  FluentLogger.error("Http server error: ", e);
            }
        }
    }
}
