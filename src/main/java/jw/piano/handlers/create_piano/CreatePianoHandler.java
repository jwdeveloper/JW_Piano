package jw.piano.handlers.create_piano;

import jw.piano.data.PianoData;
import jw.piano.data.PluginConfig;
import jw.piano.service.PianoDataService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import org.bukkit.util.Vector;

@Injection
public class CreatePianoHandler implements MediatorHandler<CreatePianoRequest, CreatePianoResponse> {

    @Inject
    private PianoDataService pianoDataService;

    @Inject
    private PluginConfig settings;

    @Override
    public CreatePianoResponse handle(CreatePianoRequest request)
    {
        final var limit = settings.getPianoInstancesLimit();
        if(pianoDataService.get().size()+1>settings.getPianoInstancesLimit())
        {
            return new CreatePianoResponse(false,"Can't add more pianos on the server limit is: "+limit);
        }

        final var player = request.player();
        final var location = player.getLocation().setDirection(new Vector(0, 0, 1));
        final var pianoData = new PianoData();
        pianoData.setName(player.getName()+" piano");
        pianoData.setLocation(location);
        pianoData.setEnable(true);
        pianoData.setSkinId(109);
        final  var result = pianoDataService.insert(pianoData);
        if(!result)
        {
            return new CreatePianoResponse(false,"Unable to add new piano");
        }

        return new CreatePianoResponse(true,"");
    }
}
