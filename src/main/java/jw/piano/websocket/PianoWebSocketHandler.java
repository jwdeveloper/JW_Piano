package jw.piano.websocket;

import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.fluent_events.EventBase;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.io.IOException;

@SpigotBean(lazyLoad = false)
public class PianoWebSocketHandler extends EventBase {

    private PianoWebSocket webSocket;

    public PianoWebSocketHandler(PianoWebSocket webSocket) {
        this.webSocket = webSocket;
    }


    @Override
    public void onPluginStart(PluginEnableEvent event) {
        webSocket.start();
        FluentPlugin.logSuccess("Web socket server");
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {

        try {
            FluentPlugin.logSuccess("Close web socket server");
            webSocket.stop();
        } catch (IOException | InterruptedException e) {
            FluentPlugin.logException("Socket exception", e);
        }

    }

}

