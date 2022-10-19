package jw.piano.websocket.packets;

import jw.piano.service.PianoService;
import jw.piano.websocket.models.PianoAction;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import jw.spigot_fluent_api.web_socket.WebSocketPacket;
import jw.spigot_fluent_api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

@Injection
public class PianoActionPacket extends WebSocketPacket {
    @Inject
    private PianoService pianoModelService;

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


    public PianoActionPacket()
    {
        consumeTasks();
    }

    final Queue<PianoAction> tasks = new LinkedBlockingQueue<>();


    public void consumeTasks()
    {
        var taskTimer = new FluentTaskTimer(1, (currentTick, fluentTaskTimer) ->
        {
            for (final var task : tasks)
            {
                switch (task.type()) {
                    case 0 -> task.model().invokeNote(task.vel(), task.note(), task.vel());
                    case 1 -> task.model().invokePedal(task.vel(), task.note(), task.vel());
                    case 2 -> task.model().refreshKeys();
                }
            }
            tasks.clear();
        });

        taskTimer.run();
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket) {

        final UUID uuid = new UUID(a,b);
        final var piano = pianoModelService.get(uuid);
        if (piano.isEmpty())
            return;
        if(!piano.get().isCreated())
        {
            return;
        }
        if(!piano.get().getPianoData().getDesktopClientAllowed())
        {
            return;
        }

        final var pianoModel = piano.get().getPianoModel();
        tasks.add(new PianoAction(pianoModel,velocity,nodeId,packetType));
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
