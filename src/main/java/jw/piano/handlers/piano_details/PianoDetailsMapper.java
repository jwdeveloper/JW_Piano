package jw.piano.handlers.piano_details;

import jw.piano.gameobjects.Piano;
import jw.fluent.api.mapper.api.MapperProfile;

public class PianoDetailsMapper implements MapperProfile<Piano,PianoDetailsResponse> {
    @Override
    public PianoDetailsResponse configureMapping(Piano piano)
    {
        var result = new PianoDetailsResponse();
        var data = piano.getPianoData();
        result.setName(data.getName());
        result.setVolume(data.getVolume());
        result.setLocation(data.getLocation().toString());
        return result;
    }
}
