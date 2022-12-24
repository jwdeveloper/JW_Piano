package jw.piano.spigot.gui.piano;

import jw.fluent.api.spigot.gui.fluent_ui.observers.list.FluentListIndexNotifier;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.ListNotifierOptions;
import jw.piano.api.data.models.PianoSkin;

import java.util.List;

public class PianoSkinSelectObserver extends FluentListIndexNotifier<PianoSkin>
{

    public PianoSkinSelectObserver(List<PianoSkin> values, ListNotifierOptions<PianoSkin> options)
    {
        super(values, options);
    }
}
