package jw.piano.api.data;

import jw.fluent.api.files.implementation.RepositoryBase;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.piano.api.data.models.PianoData;

@Injection
public class PianoDataRepository extends RepositoryBase<PianoData>
{
    public PianoDataRepository()
    {
        super(PianoData.class);
    }
}
