package jw.piano.service;

import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PianoSkin;
import jw.piano.data.PluginConfig;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Injection
public class PianoSkinService
{

    private final List<PianoSkin> skinList;
    private final FluentTranslator lang;

    @Inject
    public PianoSkinService(PluginConfig settings, FluentTranslator lang)
    {
        this.lang =lang;
        skinList = new ArrayList<>();
        skinList.addAll(defaultSkins());
        skinList.addAll(settings.getSkins());
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
        return skinList.stream().filter(e -> e.getCustomModelId() == customId).findFirst();
    }

    public PianoSkin grandPiano()
    {
        return getSkinById(109).get();
    }

    public List<PianoSkin> skins()
    {
        return skinList;
    }

}
