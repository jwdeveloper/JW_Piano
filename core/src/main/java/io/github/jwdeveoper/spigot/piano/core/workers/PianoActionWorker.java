/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package io.github.jwdeveoper.spigot.piano.core.workers;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveoper.spigot.piano.core.mediator.piano.action.PianoAction;
import io.github.jwdeveoper.spigot.piano.core.services.PianoService;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Injection(lazyLoad = false)
public class PianoActionWorker
{
    private final PianoService pianoService;
    private final Queue<PianoAction.WorkerInfo> actions;
    private final FluentTaskFactory taskManager;

    @Inject
    public PianoActionWorker(PianoService pianoService, FluentTaskFactory taskManager)
    {
        this.pianoService = pianoService;
        this.taskManager = taskManager;
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
        if(!piano.getPianoData().getDesktopClientAllowed())
        {
            return;
        }
        actions.add(new PianoAction.WorkerInfo(piano,request.velocity(),request.note(),request.eventType(), request.track()));
    }

    private void runWorker()
    {

        taskManager.taskTimer(0,(iteration, taskTimer) ->
        {
            for (final var task : actions)
            {
                switch (task.eventType()) {
                    case 0 -> task.model().triggerNote(task.velocity(), task.note(), task.velocity(),task.track());
                    case 1 -> task.model().triggerPedal(task.velocity(), task.note(),100);
                    case 2 -> task.model().getKeyboard().reset();
                }
            }
            actions.clear();
        }).run();
    }
}
