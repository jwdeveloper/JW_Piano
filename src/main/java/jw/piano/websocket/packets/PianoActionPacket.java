package jw.piano.websocket.packets;

import jw.piano.services.PianoService;
import jw.piano.websocket.models.PianoAction;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.api.web_socket.WebSocketPacket;
import jw.fluent.api.web_socket.annotations.PacketProperty;
import org.java_websocket.WebSocket;

import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

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

    private final PianoService pianoService;
    private final Queue<PianoAction> tasks = new LinkedBlockingQueue<>();

    public PianoActionPacket(PianoService pianoService)
    {
        this.pianoService = pianoService;
        consumeTasks();
    }

    @Override
    public void onPacketTriggered(WebSocket webSocket) {

        final var uuid = new UUID(a,b);
        final var piano = pianoService.find(uuid);
        if (piano.isEmpty())
        {
            return;
        }

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

    private void consumeTasks()
    {
        var taskTimer = new SimpleTaskTimer(1, (currentTick, fluentTaskTimer) ->
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
    public String toString() {
        return "OnPianoPacket{" +
                ", packetType=" + packetType +
                ", nodeId=" + nodeId +
                ", velocity=" + velocity +
                ", uuid=" + a+
                '}';
    }
}
