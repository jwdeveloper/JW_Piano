package jw.piano.service;

import jw.piano.data.PianoSkin;
import jw.piano.data.PluginConfig;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_plugin.languages.Lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Injection
public class PianoSkinService
{

    private final List<PianoSkin> skinList;

    @Inject
    public PianoSkinService(PluginConfig settings)
    {
        skinList = new ArrayList<>();
        skinList.addAll(defaultSkins());
        skinList.addAll(settings.getSkins());

    }

    private List<PianoSkin> defaultSkins(){
        var result = new ArrayList<PianoSkin>();
        result.add(new PianoSkin(0, Lang.get("skins.none")));
        result.add(new PianoSkin(108,Lang.get("skins.upright-piano")));
        result.add(new PianoSkin(109,Lang.get("skins.grand-piano")));
        result.add(new PianoSkin(110,Lang.get("skins.electric-piano")));
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
