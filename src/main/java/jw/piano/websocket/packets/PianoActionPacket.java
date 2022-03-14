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

    //id of the packet
    @PacketProperty
    public int packetId;

    //Note or pedal
    @PacketProperty
    public byte packetType;

    //0 - 88
    @PacketProperty
    public byte nodeId;

    //0 - 124    //0 means note off otherwise note on
    @PacketProperty
    public byte velocity;

    //ID of the piano that need to played
    @PacketProperty
    public UUID uuid;

    @Override
    public int getPacketId() {
        return 0;
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket) {
        int vel = velocity;
        int note = nodeId;
        int type = packetType;
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
                ", uuid=" + uuid +
                '}';
    }
}
