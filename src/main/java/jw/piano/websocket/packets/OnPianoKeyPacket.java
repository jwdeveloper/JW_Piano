package jw.piano.websocket.packets;


import jw.piano.management.PianoManager;
import jw.piano.model.PianoModel;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;


@SpigotBean
public class OnPianoKeyPacket extends WebSocketPacket
{
    private PianoModel pianoModel;
    private final PianoManager pianoManager;

    @PacketProperty
    public byte noteId;

    @PacketProperty
    public byte pianoId;

    @PacketProperty
    public byte velocity;

    public OnPianoKeyPacket(PianoManager pianoManager)
    {
        this.pianoManager = pianoManager;

    }

    @Override
    public void onPacketTriggered(WebSocket webSocket)
    {
        if(pianoModel == null)
            pianoModel = pianoManager.getPiano();
        int vel = velocity;
        int note = noteId;
        if(note<0)
            return;

        this.addSpigotTask(webSocket1 ->
        {
            pianoModel.invokeNote(vel,note,vel);
        });
    }

    @Override
    public int getPacketId() {
        return 69;
    }


}