package jw.piano.managers;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;
import jw.spigot_fluent_api.fluent_tasks.FluentTasks;

import java.io.IOException;

public class PianoWebSocketManager implements PluginPipeline {

    private PianoWebSocket webSocket;

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {

        webSocket = FluentInjection.getInjection(PianoWebSocket.class);
        webSocket.start();
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
        webSocket.stop();
        FluentPlugin.logSuccess("Closing web socket server");

    }
}

