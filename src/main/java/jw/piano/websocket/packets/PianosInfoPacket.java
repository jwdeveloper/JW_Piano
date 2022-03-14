package jw.piano.websocket.packets;

import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.utilites.ActionResult;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.UUID;

@Injection
public class PianosInfoPacket extends WebSocketPacket {

    @Inject
    private PianoService pianoService;

    @PacketProperty
    public UUID pianoId;

    @Override
    public void onPacketTriggered(WebSocket webSocket) {
        final UUID uuid = pianoId;
        var pianoOptional = pianoService.get(uuid);

        if (pianoOptional.isEmpty()) {
            sendJson(webSocket, new ActionResult<>(null, false));
        } else {
            sendJson(webSocket, new ActionResult<>(pianoOptional, true));
        }
    }

    @Override
    public int getPacketId() {
        return 1;
    }
}
