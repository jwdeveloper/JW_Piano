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

package jw.piano.core.mediator.piano.create;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.PianoData;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.piano.core.services.ColorsService;
import jw.piano.core.services.PianoService;
import org.bukkit.util.Vector;

@Injection
public class CreatePianoHandler implements MediatorHandler<CreatePiano.Request, CreatePiano.Response> {

    private final PianoService pianoService;
    private final FluentTranslator translator;

    @Inject
    public CreatePianoHandler(PianoService pianoService, FluentTranslator translator)
    {
        this.pianoService = pianoService;
        this.translator = translator;
    }


    @Override
    public CreatePiano.Response handle(CreatePiano.Request request) {
        if(!pianoService.canCreate())
        {
            return new CreatePiano.Response(false,translator.get(PluginTranslations.PIANO.CREATE.ERROR_TOO_MUCH));
        }

        final var player = request.player();
        final var location = player.getLocation().setDirection(new Vector(0, 0, 1));
        final var pianoData = new PianoData();
        pianoData.setName(player.getName()+"'s piano");
        pianoData.setLocation(location);
        pianoData.setSkinName("grand piano");
        pianoData.setEffectName("midi player");
        pianoData.setSoundName("default");
        pianoData.setActive(true);
        pianoData.setColor(ColorsService.brown());
        final var result = pianoService.create(pianoData);
        if(result.isEmpty())
        {
            return new CreatePiano.Response(false,translator.get(PluginTranslations.PIANO.CREATE.ERROR));
        }

        return new CreatePiano.Response(true,"");
    }
}
