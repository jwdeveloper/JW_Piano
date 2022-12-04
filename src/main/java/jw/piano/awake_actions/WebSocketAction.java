package jw.piano.awake_actions;
import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.PluginConfig;
import jw.piano.websocket.PianoWebSocket;

import java.io.*;
import java.net.URL;

public class WebSocketAction implements FluentApiExtention {

    private PianoWebSocket webSocket;
    private PluginConfig settings;

    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        settings =   FluentApi.injection().findInjection(PluginConfig.class);
        if(!settings.isRunPianoPlayerServer())
        {
            fluentAPI.getFluentLogger().info("Piano server is disabled to changed that jump to  plugin/JW_Piano/settings.json");
        }
        if(settings.getCustomServerIp().equals(""))
        {
            settings.SERVER_IP = getServerPublicIP();
        }
        else
        {
            settings.SERVER_IP = settings.getCustomServerIp();
        }

        webSocket =  FluentApi.injection().findInjection(PianoWebSocket.class);
        webSocket.start();
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {
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

