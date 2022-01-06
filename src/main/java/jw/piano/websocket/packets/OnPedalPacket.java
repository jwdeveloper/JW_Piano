package jw.piano.websocket.packets;


import jw.piano.management.PianoManager;
import jw.piano.model.PianoModel;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

@SpigotBean
public class OnPedalPacket extends WebSocketPacket
{
    PianoModel pianoModel;
    PianoManager pianoManager;
    public OnPedalPacket(PianoManager pianoManager)
    {
        this.pianoManager = pianoManager;
    }

    @PacketProperty
    public byte pedalId;

    @PacketProperty
    public byte pianoId;

    @PacketProperty
    public byte velocity;

    @Override
    public int getPacketId() {
        return 70;
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket)
    {
        if(pianoModel == null)
            pianoModel = pianoManager.getPiano();
        int vel = velocity;
        int pedal = pedalId;
        this.addSpigotTask(webSocket1 ->
        {
            pianoModel.invokePedal(vel,pedal,vel);
        });
    }


    @Override
    public String toString() {
        return "OnPedalPacket{" +
                ", pedalId=" + pedalId +
                ", pianoId=" + pianoId +
                ", velocity=" + velocity +
                '}';
    }
}
