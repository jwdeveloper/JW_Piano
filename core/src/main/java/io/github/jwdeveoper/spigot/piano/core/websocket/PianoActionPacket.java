package io.github.jwdeveoper.spigot.piano.core.websocket;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.extension.websocket.api.annotations.PacketProperty;
import io.github.jwdeveloper.ff.extension.websocket.implementation.packet.WebSocketPacket;
import io.github.jwdeveoper.spigot.piano.core.mediator.piano.action.PianoAction;
import io.github.jwdeveoper.spigot.piano.core.workers.PianoActionWorker;
import org.java_websocket.WebSocket;

import java.util.UUID;



public class PianoActionPacket extends WebSocketPacket
{
    @PacketProperty
    public long a;

    @PacketProperty
    public long b;

    //0 node 1 pedal 2 refresh keys
    @PacketProperty
    public byte packetType;

    //0 - 88
    @PacketProperty
    public byte nodeId;

    //0 - 124    //0 means note off otherwise note on
    @PacketProperty
    public byte velocity;

    //0 - 124    //0 means default track
    @PacketProperty
    public byte track;

    @Override
    public int getPacketId() {
        return 0;
    }

    private final PianoActionWorker pianoActionWorker;


    public PianoActionPacket(PianoActionWorker pianoActionWorker, FluentTaskFactory taskManager) {
        super(taskManager);
        this.pianoActionWorker = pianoActionWorker;
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket) {

        pianoActionWorker.handle(new PianoAction.PianoEvent(new UUID(a, b), velocity, nodeId, packetType,track));
    }

    @Override
    public String toString() {
        return "OnPianoPacket{" +
                ", packetType=" + packetType +
                ", nodeId=" + nodeId +
                ", velocity=" + velocity +
                ", uuid=" + a +
                '}';
    }
}
