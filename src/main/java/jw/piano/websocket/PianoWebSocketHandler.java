package jw.piano.websocket;

import jw.InitializerAPI;
import jw.dependency_injection.Injectable;
import jw.dependency_injection.InjectionManager;
import jw.events.EventBase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.io.IOException;

@Injectable(autoInit = true)
public class PianoWebSocketHandler extends EventBase {

    private PianoWebSocket testWebSocket;

    public PianoWebSocketHandler()
    {

    }

    @Override
    public void onPluginStart(PluginEnableEvent event)
    {
        Bukkit.getPluginManager().registerEvents(this, InitializerAPI.getPlugin());
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+" Try run Piano socket");
        testWebSocket = new PianoWebSocket();
        testWebSocket.start();
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
            try
            {
                testWebSocket.stop();
            }
            catch (IOException | InterruptedException e)
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED+e.getMessage());
            }
    }

}