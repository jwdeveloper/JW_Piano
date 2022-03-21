package jw.piano.websocket.packets;


import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.UUID;

@Injection
public class PianoActionPacket extends WebSocketPacket {
    @Inject
    private PianoService pianoModelService;

    @PacketProperty
    public long a;

    @PacketProperty
    public long b;

    //Note or pedal
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

    @Override
    public void onPacketTriggered(WebSocket webSocket) {

        final UUID uuid = new UUID(a,b );
        final int vel = velocity;
        final int note = nodeId;
        final int type = packetType;
        this.addSpigotTask(webSocket1 ->
        {
            var piano = pianoModelService.get(uuid);
            if (piano.isEmpty())
                return;

            var pianoModel = piano.get().getPianoModel();
            switch (type) {
                case 0 -> pianoModel.invokeNote(vel, note, vel);
                case 1 -> pianoModel.invokePedal(vel, note, vel);
            }
        });
    }

    @Override
    public String toString() {
        return "OnPianoPacket{" +
                "pianoModelService=" + pianoModelService +
                ", packetType=" + packetType +
                ", nodeId=" + nodeId +
                ", velocity=" + velocity +
                ", uuid=" + a+
                '}';
    }
}
