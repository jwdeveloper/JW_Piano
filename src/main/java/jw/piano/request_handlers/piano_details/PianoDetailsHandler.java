package jw.piano.request_handlers.piano_details;

import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.MediatorHandler;
import jw.spigot_fluent_api.fluent_mapper.FluentMapper;
import jw.spigot_fluent_api.utilites.ActionResult;

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

        FluentMapper.map(pianoId,  new ActionResult<String>().getClass());
        final var response = FluentMapper.map(pianoOptional.get(), PianoDetailsResponse.class);
        return response;
    }
}
