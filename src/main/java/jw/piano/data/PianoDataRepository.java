package jw.piano.data;

import jw.spigot_fluent_api.data.repositories.RepositoryBase;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;

@SpigotBean
public class PianoDataRepository extends RepositoryBase<PianoData>
{
    public PianoDataRepository()
    {
        super(PianoData.class);
    }
}
