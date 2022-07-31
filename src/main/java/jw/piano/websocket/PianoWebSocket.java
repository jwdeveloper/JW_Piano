package jw.piano.websocket;


import jw.piano.data.PianoConfig;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.web_socket.WebSocketBase;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;

@Injection
public class PianoWebSocket extends WebSocketBase
{
    @Inject
    public PianoWebSocket(PianoConfig settings)
    {
        super(settings.getWebSocketPort());
        FluentLogger.info("Piano server is running on port "+settings.getWebSocketPort());
        var packets = FluentInjection.getInjectionContainer().getAllByParentType(WebSocketPacket.class);
        this.registerPackets(packets);
    }
}
