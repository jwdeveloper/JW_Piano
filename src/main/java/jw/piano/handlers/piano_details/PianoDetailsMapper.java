package jw.piano.handlers.piano_details;

import jw.piano.game_objects.Piano;
import jw.spigot_fluent_api.fluent_mapper.api.MapperProfile;

public class PianoDetailsMapper implements MapperProfile<Piano,PianoDetailsResponse> {
    @Override
    public PianoDetailsResponse configureMapping(Piano piano)
    {
        var result = new PianoDetailsResponse();
        var data = piano.getPianoData();
        result.setName(data.getName());
        result.setType(data.getPianoType().name());
        result.setVolume(data.getVolume());
        result.setLocation(data.getLocation().toString());
        return result;
    }
}
