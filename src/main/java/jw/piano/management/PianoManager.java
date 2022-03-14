package jw.piano.management;

import jw.dependency_injection.Injectable;
import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.piano.model.PianoModel;

import java.util.Collection;
import java.util.HashMap;

@Injectable(autoInit = true)
public class PianoManager
{
    private final PianoDataRepository pianoDataRepository;
    private final HashMap<String, PianoModel> pianoModelHashMap = new HashMap<>();

    public PianoManager(PianoDataRepository pianoDataRepository)
    {
        this.pianoDataRepository = pianoDataRepository;
        this.pianoDataRepository.setOnInsert(this::registerPiano);
        this.pianoDataRepository.setOnDelete(this::unregisterPiano);
    }

    public Collection<PianoModel> getPianos()
    {
        return pianoModelHashMap.values();
    }


    public PianoModel getPiano()
    {
        return pianoModelHashMap.values().stream().findFirst().get();
    }

    public void createPianos()
    {
       for(PianoData pianoData:pianoDataRepository.getMany())
       {
           registerPiano(pianoData);
       }
    }
    public void destroyPianos()
    {
        for(PianoData pianoData:pianoDataRepository.getMany())
        {
            unregisterPiano(pianoData);
        }
    }
    public void registerPiano(PianoData pianoData)
    {
        if(!pianoModelHashMap.containsKey(pianoData.id))
        {
            PianoModel pianoModel = new PianoModel(pianoData);
            pianoModelHashMap.put(pianoData.id,pianoModel);
            pianoModel.create();
        }
    }
    public void unregisterPiano(PianoData pianoData)
    {
        if(pianoModelHashMap.containsKey(pianoData.id))
        {
            PianoModel pianoModel = pianoModelHashMap.get(pianoData.id);
            pianoModel.destroy();
            pianoModelHashMap.remove(pianoData.id);
        }
    }
}
