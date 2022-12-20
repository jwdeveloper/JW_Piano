package jw.piano.services;

import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.models.PianoSkin;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Injection
public class PianoSkinService
{
    private final List<PianoSkin> skins;
    private final FluentTranslator lang;

    @Inject
    public PianoSkinService(FluentTranslator lang)
    {
        this.lang =lang;
        skins = new ArrayList<>();
        skins.addAll(defaultSkins());
    }

    private List<PianoSkin> defaultSkins(){
        var result = new ArrayList<PianoSkin>();
        result.add(new PianoSkin(0, lang.get("skins.none")));
        result.add(new PianoSkin(108,lang.get("skins.upright-piano")));
        result.add(new PianoSkin(109,lang.get("skins.grand-piano")));
        result.add(new PianoSkin(110,lang.get("skins.electric-piano")));
        return result;
    }

    public Optional<PianoSkin> getSkinById(int customId)
    {
        return skins.stream().filter(e -> e.getCustomModelId() == customId).findFirst();
    }

    public Integer getSkinIndex(int skinModelId)
    {
       var i=0;
       for(var skin : skins)
       {
           if(skin.getCustomModelId() == skinModelId)
           {
               return i;
           }
           i++;
       }
       return -1;
    }

    public PianoSkin grandPiano()
    {
        return getSkinById(109).get();
    }

    public List<PianoSkin> skins()
    {
        return skins;
    }

}
