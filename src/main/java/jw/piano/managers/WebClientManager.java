package jw.piano.managers;
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
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class WebClientManager implements PluginPipeline {

    private PianoWebSocket webSocket;
    private WebHttpServer webHttpServer;

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        webSocket = FluentInjection.getInjection(PianoWebSocket.class);
        webHttpServer = FluentInjection.getInjection(WebHttpServer.class);
       // webSocket.setWebSocketFactory(getSS2L());
        webSocket.start();
        webHttpServer.start();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
        webSocket.stop();
        FluentPlugin.logSuccess("Closing web socket server");
        webHttpServer.stop();
        FluentPlugin.logSuccess("Closing web website");
    }

    @SneakyThrows
    public SSLParametersWebSocketServerFactory getSS2L()
    {
      return null;
    }

//keytool -genkey -alias server-alias -keyalg RSA -keypass test1234 -storepass testasd -keystore keystore.jks
    @SneakyThrows
    public SSLParametersWebSocketServerFactory getSSL()
    {
        var path = FluentPlugin.getPath()+"/keystore.jks";
        String STORETYPE = "JKS";
        String KEYSTORE = Paths.get("src", "test", "java", "org", "java_websocket", "keystore.jks")
                .toString();
        FluentPlugin.logSuccess(path);
        String STOREPASSWORD = "testasd";
        String KEYPASSWORD = "test1234";

        KeyStore ks = KeyStore.getInstance(STORETYPE);
        File kf = new File(path);
        ks.load(new FileInputStream(kf), STOREPASSWORD.toCharArray());

        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, KEYPASSWORD.toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
        tmf.init(ks);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        SSLParameters sslParameters = new SSLParameters();
        // This is all we need
        sslParameters.setNeedClientAuth(false);
        return new SSLParametersWebSocketServerFactory(sslContext, sslParameters);
    }
}

