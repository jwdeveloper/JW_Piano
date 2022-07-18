package jw.piano.websocket;


import jw.piano.data.Settings;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.ActionResult;
import jw.spigot_fluent_api.web_socket.WebSocketBase;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;

@Injection
public class PianoWebSocket extends WebSocketBase
{
    @Inject
    public PianoWebSocket(Settings settings)
    {
        super(settings.getWebSocketPort());
        FluentPlugin.logInfo("Piano server is running on port "+settings.getWebSocketPort());
        var packets = FluentInjection.getInjectionContainer().getAllByParentType(WebSocketPacket.class);
        this.registerPackets(packets);
    }
}
