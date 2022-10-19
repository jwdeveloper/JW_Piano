package jw.piano.awake_actions;
import jw.piano.data.PluginConfig;
import jw.piano.websocket.PianoWebSocket;
import jw.fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.fluent_api.minecraft.logger.FluentLogger;
import jw.fluent_plugin.FluentPlugin;
import jw.fluent_plugin.starup_actions.api.PluginPipeline;
import jw.fluent_plugin.starup_actions.data.PipelineOptions;

import java.io.*;
import java.net.URL;

public class WebSocketAction implements PluginPipeline {

    private PianoWebSocket webSocket;
    private PluginConfig settings;

    @Override
    public void pluginEnable(PipelineOptions options) throws Exception {

        settings =  FluentInjection.findInjection(PluginConfig.class);
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

        webSocket = FluentInjection.findInjection(PianoWebSocket.class);
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

