package jw.piano.mediator.piano.info;

import jw.piano.services.PianoService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;

@Injection
public class PianoInfoHandler implements MediatorHandler<PianoInfo.Request, PianoInfo.Response> {


    private final PianoService pianoService;

    @Inject
    public PianoInfoHandler(PianoService pianoService)
    {
        this.pianoService = pianoService;
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
        response.setType(pianoData.getSkinId().toString());
        response.setName(pianoData.getName());
        response.setVolume(pianoData.getVolume());
        response.setLocation(pianoData.getLocation().toString());
        return response;
    }
}
