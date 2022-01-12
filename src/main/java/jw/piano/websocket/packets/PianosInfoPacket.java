package jw.piano.websocket.packets;

import jw.piano.service.PianoService;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.utilites.OperationResult;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.UUID;

@SpigotBean
public class PianosInfoPacket  extends WebSocketPacket
{

    private final PianoService pianoService;

    public PianosInfoPacket(PianoService pianoService)
    {
         this.pianoService = pianoService;
    }

    @PacketProperty
    public UUID pianoId;

    @Override
    public void onPacketTriggered(WebSocket webSocket)
    {
        final UUID uuid = pianoId;
        var pianoOptional = pianoService.get(uuid);

        if(pianoOptional.isEmpty())
        {
            sendJson(webSocket, new OperationResult<>(null,false));
        }
        else
        {
            sendJson(webSocket,new OperationResult<>(pianoOptional,true));
        }
    }

    @Override
    public int getPacketId() {
        return 1;
    }
}
