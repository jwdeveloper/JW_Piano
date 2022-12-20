package jw.piano.websocket;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.piano.data.dto.PianoAction;
import jw.piano.workers.PianoActionWorker;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.web_socket.WebSocketPacket;
import jw.fluent.api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.UUID;

@Injection
public class PianoActionPacket extends WebSocketPacket {

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


    @Override
    public int getPacketId() {
        return 0;
    }

    private final PianoActionWorker pianoActionWorker;

    @Inject
    public PianoActionPacket(PianoActionWorker pianoActionWorker) {
        this.pianoActionWorker = pianoActionWorker;
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket) {
        pianoActionWorker.handle(new PianoAction.PianoEvent(new UUID(a, b), velocity, nodeId, packetType));
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
