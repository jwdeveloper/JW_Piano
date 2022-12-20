package jw.piano.factory.sounds;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.piano.data.PluginConsts;
import jw.piano.data.config.PluginConfig;

import java.util.HashMap;
import java.util.Map;

@Injection(lazyLoad = false)
public class SoundsMapper {
    private final Map<Integer, String> withPedal;
    private final Map<Integer, String> withoutPedal;

    @Inject
    public SoundsMapper(PluginConfig config)
    {
        var soundsPrefix = config.getResourcePackConfig().getSoundsPath();
        withPedal = new HashMap<>();
        withoutPedal = new HashMap<>();
        for (int i = PluginConsts.MIDI_KEY_OFFSET; i < PluginConsts.MIDI_KEY_OFFSET + PluginConsts.NOTES_NUMBER; i++) {
            withPedal.put(i, soundsPrefix+":1c." + i);
            withoutPedal.put(i, soundsPrefix+":1." + i);
        }
    }


    public String getSound(int noteId, boolean pedal) {
        return pedal ? withPedal.get(noteId) : withoutPedal.get(noteId);
    }
}
