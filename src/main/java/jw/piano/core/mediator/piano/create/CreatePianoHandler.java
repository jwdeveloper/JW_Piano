package jw.piano.core.mediator.piano.create;

import jw.piano.api.data.models.PianoData;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.piano.core.services.PianoService;
import org.bukkit.util.Vector;

@Injection
public class CreatePianoHandler implements MediatorHandler<CreatePiano.Request, CreatePiano.Response> {

    private final PianoService pianoService;
    public CreatePianoHandler(PianoService pianoService)
    {
        this.pianoService = pianoService;
    }


    @Override
    public CreatePiano.Response handle(CreatePiano.Request request) {
        if(!pianoService.canCreate())
        {
            return new CreatePiano.Response(false,"Can't add more pianos on the server");
        }

        final var player = request.player();
        final var location = player.getLocation().setDirection(new Vector(0, 0, 1));
        final var pianoData = new PianoData();
        pianoData.setName(player.getName()+"'s piano");
        pianoData.setLocation(location);
        pianoData.setSkinName("none");
        pianoData.setEffectName("none");
        pianoData.setActive(true);
        final var result = pianoService.create(pianoData);
        if(result.isEmpty())
        {
            return new CreatePiano.Response(false,"Unable to add new piano");
        }

        return new CreatePiano.Response(true,"");
    }
}
