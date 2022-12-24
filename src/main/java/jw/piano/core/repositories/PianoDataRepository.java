package jw.piano.core.repositories;

import jw.fluent.api.files.implementation.RepositoryBase;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.piano.api.data.models.PianoData;

import java.util.UUID;

@Injection
public class PianoDataRepository extends RepositoryBase<PianoData>
{
    public PianoDataRepository()
    {
        super(PianoData.class);
    }

    public boolean delete(UUID uuid)
    {
        return deleteOneById(uuid);
    }
}
