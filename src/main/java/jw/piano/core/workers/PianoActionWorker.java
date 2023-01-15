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

package jw.piano.core.workers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.tasks.SimpleTaskTimer;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
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
        if(!piano.getPianoObserver().getDesktopClientAllowed().get())
        {
            return;
        }
        actions.add(new PianoAction.WorkerInfo(piano,request.velocity(),request.note(),request.eventType(), request.track()));
    }

    private void runWorker()
    {
        var taskTimer = new SimpleTaskTimer(0, (currentTick, fluentTaskTimer) ->
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
        });
        taskTimer.run();
    }
}
