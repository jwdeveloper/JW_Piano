package jw.piano.websocket.packets;
import jw.piano.handlers.piano_details.PianoDetailsResponse;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.utilites.ActionResult;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.UUID;

@Injection
public class PianosInfoPacket extends WebSocketPacket {

    @PacketProperty
    public long a;
    @PacketProperty
    public long b;

    @Override
    public void onPacketTriggered(final WebSocket webSocket)
    {
        final UUID uuid = new UUID(a,b);
        final var data = FluentMediator.resolve(uuid,PianoDetailsResponse.class);
        final var response = new ActionResult(data,data != null);
        sendJson(webSocket, response);
    }

    @Override
    public int getPacketId() {
        return 1;
    }
}
