package jw.piano.service;

import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import java.util.List;
import java.util.UUID;

@Injection
public class PianoDataService
{
    @Inject
    private  PianoService pianoService;
    @Inject
    private  PianoDataRepository pianoDataRepository;

    public List<PianoData> get()
    {
        return pianoDataRepository.getMany();
    }

    public boolean insert(PianoData pianoData)
    {
        if(!pianoDataRepository.insertOne(pianoData))
            return false;

        pianoService.create(pianoData);
        return true;
    }
    public boolean delete(UUID uuid)
    {
        if(!pianoDataRepository.deleteOneById(uuid))
            return false;

        pianoService.delete(uuid);
        return true;
    }


}
