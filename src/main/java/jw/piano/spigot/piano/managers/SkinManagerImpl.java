package jw.piano.spigot.piano.managers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.managers.skins.SkinManager;
import jw.piano.core.services.SkinLoaderService;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Consumer;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class SkinManagerImpl implements SkinManager
{
    private final HashMap<String, PianoSkin> values;
    private PianoSkin currentEffect;

    @Setter
    private Consumer<PianoSkin> onSkinSet;

    @Inject
    public SkinManagerImpl(SkinLoaderService skinService)
    {
        values = new LinkedHashMap<>();
        var skins = skinService.skins();
        for(var skin : skins)
        {
            register(skin);
        }
        currentEffect = skinService.grandPianoSkin();
    }

    @Override
    public PianoSkin getCurrent() {
        return currentEffect;
    }

    @Override
    public List<PianoSkin> getItems() {
        return values.values().stream().toList();
    }

    @Override
    public void setCurrent(String name)
    {
        if (!values.containsKey(name)) {
            return;
        }
        currentEffect = values.get(name);
        onSkinSet.accept(currentEffect);
    }

    @Override
    public void setCurrent(PianoSkin value) {
        if (!values.containsValue(value)) {
            register(value);
        }
        currentEffect = value;
        onSkinSet.accept(currentEffect);
    }

    @Override
    public void register(PianoSkin value) {
        if (values.containsValue(value)) {
            return;
        }
        values.put(value.getName(), value);
    }

    @Override
    public void unregister(PianoSkin value) {
        if (!values.containsValue(value)) {
            return;
        }
        values.remove(value.getName());
    }

    @Override
    public List<String> getNames() {
        return values.keySet().stream().toList();
    }
}
