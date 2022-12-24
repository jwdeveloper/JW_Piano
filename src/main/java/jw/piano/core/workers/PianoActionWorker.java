package jw.piano.core.workers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.piano.api.data.dto.PianoAction;
import jw.piano.core.services.PianoService;

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
        final var optional = pianoService.find(request.pianoId());
        if (optional.isEmpty())
        {
            return;
        }

        var piano = optional.get();
        if(!piano.getPianoObserver().getDesktopClientAllowedBind().get())
        {
            return;
        }
        actions.add(new PianoAction.WorkerInfo(piano,request.velocity(),request.note(),request.eventType()));
    }

    private void runWorker()
    {
        var taskTimer = new SimpleTaskTimer(1, (currentTick, fluentTaskTimer) ->
        {
            for (final var task : actions)
            {
                switch (task.eventType()) {
                    case 0 -> task.model().triggerNote(task.velocity(), task.note(), task.velocity());
                    case 1 -> task.model().triggerPedal(task.velocity(), task.note(),100);
                    case 2 -> task.model().getKeyboard().reset();
                }
            }
            actions.clear();
        });
        taskTimer.run();
    }
}
