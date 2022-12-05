package jw.piano.websocket;


import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginConfig;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.web_socket.WebSocketBase;
import jw.fluent.api.web_socket.WebSocketPacket;

@Injection
public class PianoWebSocket extends WebSocketBase
{
    @Inject
    public PianoWebSocket(PluginConfig settings)
    {
        super(settings.getPort());
        FluentApi.logger().info("Piano server is running on "+settings.SERVER_IP+":"+settings.getPort());
        var packets =  FluentApi.injection().findAllBySuperClass(WebSocketPacket.class);
        this.registerPackets(packets);
    }
}
