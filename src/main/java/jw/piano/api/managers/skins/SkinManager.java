package jw.piano.api.managers.skins;

import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.managers.AbstractManager;

import java.util.List;

public interface SkinManager extends AbstractManager<PianoSkin>
{
   public List<String> getNames();
}
