package jw.piano.awake_actions;
import jw.piano.data.Settings;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

import java.io.*;
import java.net.URL;

public class WebSocketManager implements PluginPipeline {

    private PianoWebSocket webSocket;
    private Settings settings;

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        settings =  FluentInjection.getInjection(Settings.class);
        if(!settings.isRunPianoPlayerServer())
        {
            FluentLogger.info("Piano server is disabled to changed that jump to  plugin/JW_Piano/settings.json");
        }
        if(settings.getCustomServerIp().equals(""))
        {
            var ip = getServerPublicIP();
            settings.setServerIp(ip);
        }
        else
        {
            settings.setServerIp(settings.getCustomServerIp());
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

