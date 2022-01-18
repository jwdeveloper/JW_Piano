package jw.piano.managers;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

public class PianoWebSocketManager implements PluginPipeline {

    private PianoWebSocket webSocket;

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        try
        {
            webSocket = InjectionManager.getObject(PianoWebSocket.class);
            webSocket.start();
            FluentPlugin.logSuccess("Web socket server");
        }
        catch (Exception e)
        {
            FluentPlugin.logException("Web socket error",e);
        }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
        FluentPlugin.logSuccess("Close web socket server");
        webSocket.stop();
    }
}

