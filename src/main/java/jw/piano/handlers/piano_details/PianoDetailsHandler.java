package jw.piano.handlers.piano_details;

import jw.fluent_plugin.implementation.FluentApi;
import jw.piano.service.PianoService;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent_api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent_api.utilites.ActionResult;

import java.util.UUID;

@Injection
public class PianoDetailsHandler implements MediatorHandler<UUID, PianoDetailsResponse> {

    @Inject
    private PianoService pianoService;

    @Override
    public PianoDetailsResponse handle(UUID pianoId) {

        final var pianoOptional = pianoService.get(pianoId);
        if (pianoOptional.isEmpty())
            return null;

        FluentApi.mapper().map(pianoId,  new ActionResult<String>().getClass());
        final var response = FluentApi.mapper().map(pianoOptional.get(), PianoDetailsResponse.class);
        return response;
    }
}
