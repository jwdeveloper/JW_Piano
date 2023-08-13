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

package io.github.jwdeveoper.spigot.piano.core.mediator.piano.info;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.mediator.api.MediatorHandler;
import io.github.jwdeveoper.spigot.piano.core.services.PianoService;
import org.bukkit.plugin.Plugin;

@Injection
public class PianoInfoHandler implements MediatorHandler<PianoInfo.Request, PianoInfo.Response> {


    private final PianoService pianoService;
    private final Plugin plugin;

    @Inject
    public PianoInfoHandler(PianoService pianoService, Plugin plugin)
    {
        this.pianoService = pianoService;
        this.plugin = plugin;
    }

    @Override
    public PianoInfo.Response handle(PianoInfo.Request request) {
        final var pianoId = request.pianoId();
        final var pianoOptional = pianoService.find(pianoId);
        if (pianoOptional.isEmpty())
            return null;

        final var piano = pianoOptional.get();
        var pianoData = piano.getPianoData();

        var response = new PianoInfo.Response();
        response.setId(pianoData.getUuid().toString());
        response.setType("none");
        response.setName(pianoData.getName());
        response.setVolume(pianoData.getVolume());
        response.setLocation(pianoData.getLocation().toString());
        response.setPluginVersion(plugin.getDescription().getVersion());
        return response;
    }
}
