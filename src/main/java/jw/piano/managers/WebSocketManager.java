package jw.piano.managers;
import jw.piano.data.Settings;
import jw.piano.web_http_server.WebHttpServer;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_tasks.FluentTasks;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.java_websocket.server.SSLParametersWebSocketServerFactory;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class WebSocketManager implements PluginPipeline {

    private PianoWebSocket webSocket;
    private Settings settings;

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        settings =  FluentInjection.getInjection(Settings.class);
        if(!settings.isRunPianoPlayerServer())
        {
            FluentPlugin.logInfo("Piano server is disabled to changed that jump to  plugin/JW_Piano/settings.json");
        }
        if(settings.getServerURL().equals(""))
        {
            var ip = getServerPublicIP();
            settings.setServerURL(ip);
        }

        webSocket = FluentInjection.getInjection(PianoWebSocket.class);
        webSocket.start();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
        if(!settings.isRunPianoPlayerServer())
        {
          return;
        }
        webSocket.stop();
    }

    private String getServerPublicIP() throws IOException {
        var url = new URL("http://checkip.amazonaws.com/");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }
}

