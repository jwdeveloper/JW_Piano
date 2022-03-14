package jw.piano.managers;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

public class PianoWebSocketManager implements PluginPipeline {

    private PianoWebSocket webSocket;

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        webSocket = FluentInjection.getInjection(PianoWebSocket.class);
        try
        {
            webSocket.start();
        }
        catch (Exception e)
        {
            FluentPlugin.logException("Web socket error",e);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
        FluentPlugin.logSuccess("Closing web socket server");
        webSocket.stop();
    }
}

