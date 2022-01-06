package jw.piano.data;

import jw.spigot_fluent_api.data.repositories.RepositoryBase;
import jw.spigot_fluent_api.dependency_injection.InjectionType;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import lombok.Getter;
import lombok.Setter;
import java.util.function.Consumer;


@Getter
@Setter
@SpigotBean(injectionType = InjectionType.SINGLETON)
public class PianoDataRepository extends RepositoryBase<PianoData>
{
    public PianoDataRepository()
    {
        super(PianoData.class);
    }
    private Consumer<PianoData> onInsert;
    private Consumer<PianoData> onDelete;
    @Override
    public boolean insertOne(PianoData data)
    {
       super.insertOne(data);
       onInsert.accept(data);
       return true;
    }

    @Override
    public boolean deleteOne(PianoData data)
    {
        onDelete.accept(data);
        super.deleteOne(data);
        return true;
    }
}
