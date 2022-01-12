package jw.piano.service;

import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.utilites.disposing.Disposable;

import java.util.List;
import java.util.UUID;

@SpigotBean
public class PianoDataService
{
    private final PianoService pianoService;
    private final PianoDataRepository pianoDataRepository;

    public PianoDataService(PianoService pianoService, PianoDataRepository pianoDataRepository)
    {
        this.pianoService = pianoService;
        this.pianoDataRepository = pianoDataRepository;
    }

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
