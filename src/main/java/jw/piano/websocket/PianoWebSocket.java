package jw.piano.websocket;


import jw.piano.data.Settings;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.web_socket.WebSocketBase;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;

@SpigotBean
public class PianoWebSocket extends WebSocketBase
{
    public PianoWebSocket(Settings settings)
    {

        super(settings.getWebSocketPort());
        FluentPlugin.logSuccess("Web socket trying to run on port "+settings.getWebSocketPort()+"");
        this.registerPackets(InjectionManager.getObjectsWithParentType(WebSocketPacket.class));
    }
}
