package jw.piano.data;

import jw.fluent_api.files.implementation.RepositoryBase;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;

@Injection
public class PianoDataRepository extends RepositoryBase<PianoData>
{
    public PianoDataRepository()
    {
        super(PianoData.class);
    }
}
