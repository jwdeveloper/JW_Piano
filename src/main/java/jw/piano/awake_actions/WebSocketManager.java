package jw.piano.awake_actions;
import jw.piano.data.PianoConfig;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data.PipelineOptions;

import java.io.*;
import java.net.URL;

public class WebSocketManager implements PluginPipeline {

    private PianoWebSocket webSocket;
    private PianoConfig settings;

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        settings =  FluentInjection.getInjection(PianoConfig.class);
        if(!settings.isRunPianoPlayerServer())
        {
            FluentLogger.info("Piano server is disabled to changed that jump to  plugin/JW_Piano/settings.json");
        }
        if(settings.getCustomServerIp().equals(""))
        {
            settings.SERVER_IP = getServerPublicIP();
        }
        else
        {
            settings.SERVER_IP = settings.getCustomServerIp();
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

