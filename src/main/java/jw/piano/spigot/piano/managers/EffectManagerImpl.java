/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot.piano.managers;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.piano.api.data.models.PianoData;
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

    public void create(PianoData pianoData)
    {
        currentEffect = new EmptyEffect();
        register(currentEffect);
        register(new MidiPlayerEffect());
        register(new WaterfallEffect());
        register(new FlyingNotesEffect(pianoData));
     //   register(new FlyingNotesWithParticleEffect(pianoData));
        register(new SimpleParticleEffect());
        register(new TestStuff());
       // register(new NoteNameEffect());
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
        return effectInvokers.values().stream().toList();
    }

    @Override
    public void setCurrent(String name)
    {
        if (!effectInvokers.containsKey(name)) {
            return;
        }

        if(currentEffect != null)
        {
            currentEffect.onDestroy();
        }
        currentEffect = effectInvokers.get(name);
        currentEffect.onCreate();
    }

    @Override
    public void setCurrent(EffectInvoker value) {
        if (!effectInvokers.containsValue(value)) {
            register(value);
        }
        setCurrent(value.getName());
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

    @Override
    public void refresh() {
          for(var effect : effectInvokers.values())
          {
              effect.refresh();
          }
    }
}
