package jw.piano.websocket.packets;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.utilites.ActionResult;
import jw.fluent.api.web_socket.WebSocketPacket;
import jw.fluent.api.web_socket.annotations.PacketProperty;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.piano.handlers.piano.info.PianoInfo;
import org.java_websocket.WebSocket;

import java.util.UUID;

@Injection
public class PianosInfoPacket extends WebSocketPacket {

    @PacketProperty
    public long a;
    @PacketProperty
    public long b;

    @Override
    public int getPacketId() {
        return 1;
    }

    private FluentMediator mediator;

    public PianosInfoPacket(FluentMediator mediator)
    {
        this.mediator = mediator;
    }

    @Override
    public void onPacketTriggered(final WebSocket webSocket)
    {
        final var request = new PianoInfo.Request(new UUID(a,b));
        final var data = mediator.resolve(request, PianoInfo.Request.class);
        final var response = new ActionResult<>(data,data != null);
        sendJson(webSocket, response);
    }


}
