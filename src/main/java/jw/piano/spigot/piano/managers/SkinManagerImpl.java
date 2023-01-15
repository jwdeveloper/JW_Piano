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
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.managers.skins.SkinManager;
import jw.piano.core.services.SkinLoaderService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;

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
