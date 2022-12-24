package jw.piano.api.managers.effects;

import jw.piano.api.managers.AbstractManager;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EffectManager extends AbstractManager<EffectInvoker>
{
     public Set<String> getNames();
}
