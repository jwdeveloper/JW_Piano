package jw.piano.workers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.dto.PianoAction;
import jw.piano.services.PianoService;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Injection(lazyLoad = false)
public class PianoActionWorker
{
    private final PianoService pianoService;
    private final Queue<PianoAction.WorkerInfo> actions;

    @Inject
    public PianoActionWorker(PianoService pianoService)
    {
        this.pianoService = pianoService;
        actions = new LinkedBlockingQueue<>();
        runWorker();
    }

    public void handle(PianoAction.PianoEvent request)
    {
        final var piano = pianoService.find(request.pianoId());
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

        actions.add(new PianoAction.WorkerInfo(pianoModel,request.velocity(),request.note(),request.eventType()));
    }

    private void runWorker()
    {
        var taskTimer = new SimpleTaskTimer(1, (currentTick, fluentTaskTimer) ->
        {
            for (final var task : actions)
            {
                switch (task.eventType()) {
                    case 0 -> task.model().invokeNote(task.velocity(), task.note(), task.velocity());
                    case 1 -> task.model().invokePedal(task.velocity(), task.note());
                    case 2 -> task.model().refreshKeys();
                }
            }
            actions.clear();
        });
        taskTimer.run();
    }
}
