package jw.piano.data;

import jw.data.repositories.RepositoryBase;
import jw.dependency_injection.Injectable;

import java.util.function.Consumer;

@Injectable
public class PianoDataRepository extends RepositoryBase<PianoData>
{

    public PianoDataRepository()
    {
        super(PianoData.class);
    }
    private Consumer<PianoData> onInsert;
    private Consumer<PianoData> onDelete;
    public void setOnInsert(Consumer<PianoData> onInsert)
    {
        this.onInsert = onInsert;
    }
    public void setOnDelete(Consumer<PianoData> onDelete)
    {
        this.onDelete = onDelete;
    }
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
