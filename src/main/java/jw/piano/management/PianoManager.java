package jw.piano.management;

import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.piano.model.PianoModel;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@SpigotBean(lazyLoad = false)
public class PianoManager
{
    private final PianoDataRepository pianoDataRepository;
    private final HashMap<UUID, PianoModel> pianoModelHashMap = new HashMap<>();

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
        if(!pianoModelHashMap.containsKey(pianoData.uuid))
        {
            PianoModel pianoModel = new PianoModel(pianoData);
            pianoModelHashMap.put(pianoData.uuid,pianoModel);
            pianoModel.create();
        }
    }
    public void unregisterPiano(PianoData pianoData)
    {
        if(pianoModelHashMap.containsKey(pianoData.uuid))
        {
            PianoModel pianoModel = pianoModelHashMap.get(pianoData.uuid);
            pianoModel.destroy();
            pianoModelHashMap.remove(pianoData.uuid);
        }
    }
}
