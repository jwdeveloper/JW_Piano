package jw.piano.factory.sounds;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.piano.data.config.PluginConfig;
import jw.piano.data.config.ResourcePackConfig;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import org.bukkit.Location;

@Injection(lazyLoad = false)
public class SoundPlayerFactory {
    private final SoundsMapper soundsMapper;
    private final ResourcePackConfig resourcePackConfig;

    @Inject
    public SoundPlayerFactory(SoundsMapper soundsMapper, PluginConfig config) {
        this.soundsMapper = soundsMapper;
        this.resourcePackConfig = config.getResourcePackConfig();
    }

    public void play(final Location location,
                     final int note,
                     final float volume,
                     final boolean pressed) {
        final var world = location.getWorld();
        if (world == null) {
            return;
        }

        world.playSound(location,
                soundsMapper.getSound(note, pressed),
                resourcePackConfig.getSoundCategory(),
                volume,
                1);
    }
}
