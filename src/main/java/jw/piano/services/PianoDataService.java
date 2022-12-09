package jw.piano.services;

import jw.piano.api.data.models.PianoData;
import jw.piano.api.data.PianoDataRepository;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;

import java.util.List;
import java.util.UUID;

@Injection
public class PianoDataService {


    private final PianoDataRepository pianoDataRepository;

    @Inject
    public PianoDataService(PianoDataRepository repository) {

        this.pianoDataRepository = repository;
    }


    public List<PianoData> findAll() {
        return pianoDataRepository.findAll();
    }

    public boolean insert(PianoData pianoData) {
        return pianoDataRepository.insertOne(pianoData);
    }

    public boolean delete(UUID uuid) {
        return pianoDataRepository.deleteOneById(uuid);
    }


}
