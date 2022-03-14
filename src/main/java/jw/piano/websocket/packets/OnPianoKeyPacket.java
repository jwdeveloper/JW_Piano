package jw.piano.websocket.packets;

import jw.InitializerAPI;
import jw.dependency_injection.Injectable;
import jw.piano.management.PianoManager;
import jw.piano.model.PianoModel;
import jw.task.TaskTimer;
import jw.web_socket.WebSocketPacket;
import jw.web_socket.packet.PacketProperty;
import org.bukkit.Bukkit;
import org.java_websocket.WebSocket;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

@Injectable
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