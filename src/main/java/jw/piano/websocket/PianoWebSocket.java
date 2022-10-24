package jw.piano.websocket;


import jw.fluent_api.logger.OldLogger;
import jw.piano.data.PluginConfig;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent_api.web_socket.WebSocketBase;
import jw.fluent_api.web_socket.WebSocketPacket;

@Injection
public class PianoWebSocket extends WebSocketBase
{
    @Inject
    public PianoWebSocket(PluginConfig settings)
    {
        super(settings.getPort());
        OldLogger.info("Piano server is running on "+settings.SERVER_IP+":"+settings.getPort());
        var packets = FluentInjection.getContainer().findAllBySuperClass(WebSocketPacket.class);
        this.registerPackets(packets);
    }
}
