package jw.piano.websocket;


import jw.piano.data.PluginConfig;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.web_socket.WebSocketBase;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;

@Injection
public class PianoWebSocket extends WebSocketBase
{
    @Inject
    public PianoWebSocket(PluginConfig settings)
    {
        super(settings.getPort());
        FluentLogger.info("Piano server is running on "+settings.SERVER_IP+":"+settings.getPort());
        var packets = FluentInjection.getContainer().findAllBySuperClass(WebSocketPacket.class);
        this.registerPackets(packets);
    }
}
