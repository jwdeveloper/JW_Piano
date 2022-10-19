package jw.piano.data;

import jw.spigot_fluent_api.data.implementation.RepositoryBase;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;

@Injection
public class PianoDataRepository extends RepositoryBase<PianoData>
{
    public PianoDataRepository()
    {
        super(PianoData.class);
    }
}
