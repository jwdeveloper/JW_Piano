package jw.piano.spigot.piano.managers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.managers.effects.EffectManager;
import jw.piano.spigot.piano.managers.effects.*;


import java.util.*;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class EffectManagerImpl implements EffectManager {
    private final HashMap<String, EffectInvoker> effectInvokers;
    private EffectInvoker currentEffect;

    @Inject
    public EffectManagerImpl() {
        effectInvokers = new LinkedHashMap<>();
    }

    public void create() {
        currentEffect = new EmptyEffect();
        register(currentEffect);
        register(new FlyingNotesEffect());
        register(new SimpleParticleEffect());
        register(new WaterfallEffect());
        register(new HeartEffect());
    }

    public void destroy() {
        for (var effect : effectInvokers.values()) {
            effect.onDestroy();
        }
        effectInvokers.clear();
    }

    @Override
    public EffectInvoker getCurrent() {
        return currentEffect;
    }

    @Override
    public List<EffectInvoker> getItems() {
        var values = effectInvokers.values().stream().toList();
        return effectInvokers.values().stream().toList();
    }

    @Override
    public void setCurrent(String name)
    {
        if (!effectInvokers.containsKey(name)) {
            return;
        }
        currentEffect = effectInvokers.get(name);
    }

    @Override
    public void setCurrent(EffectInvoker value) {
        if (!effectInvokers.containsValue(value)) {
            register(value);
        }
        currentEffect = value;
    }

    @Override
    public void register(EffectInvoker effectInvoker) {
        if (effectInvokers.containsValue(effectInvoker)) {
            return;
        }
        effectInvokers.put(effectInvoker.getName(), effectInvoker);
        effectInvoker.onCreate();
    }

    @Override
    public void unregister(EffectInvoker effectInvoker) {

        if (!effectInvokers.containsValue(effectInvoker)) {
            return;
        }
        effectInvokers.remove(effectInvoker.getName());
        effectInvoker.onDestroy();
    }

    @Override
    public Set<String> getNames() {
        return effectInvokers.keySet();
    }
}
