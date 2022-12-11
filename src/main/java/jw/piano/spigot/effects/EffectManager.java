package jw.piano.spigot.effects;

import jw.piano.data.enums.PianoEffect;

import jw.piano.spigot.effects.api.PianoEffectInvoker;
import org.bukkit.Location;
import java.util.HashMap;

public class EffectManager implements PianoEffectInvoker
{

    private HashMap<PianoEffect, PianoEffectInvoker> effectInvokers;
    private PianoEffectInvoker current = null;

    public EffectManager()
    {
        effectInvokers = new HashMap<>();
        effectInvokers.put(PianoEffect.FLYING_NOTE,new FlyingNotesEffect());
        effectInvokers.put(PianoEffect.SIMPLE_PARTICLE,new SimpleParticleEffect());
        effectInvokers.put(PianoEffect.WATERFALL,new WaterfallEffect());
        effectInvokers.put(PianoEffect.HEART,new HeartEffect());
    }

    @Override
    public void invoke(Location location, int noteIndex, int sensivity) {

        if(current == null)
        {
            return;
        }
        current.invoke(location,noteIndex,sensivity);
    }

    @Override
    public void destroy() {
        for(var effect : effectInvokers.values())
        {
            effect.destroy();
        }
    }

    @Override
    public void create() {
        for(var effect : effectInvokers.values())
        {
            effect.create();
        }
    }

    public void setEffect(PianoEffect effect)
    {
        if(effect == PianoEffect.NONE)
        {
            current = null;
            return;
        }
        current = effectInvokers.get(effect);
    }

}
