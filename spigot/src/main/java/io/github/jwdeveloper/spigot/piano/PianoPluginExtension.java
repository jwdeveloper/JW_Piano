package io.github.jwdeveloper.spigot.piano;

import io.github.jwdeveloper.ff.plugin.api.FluentApiSpigotBuilder;
import io.github.jwdeveloper.ff.plugin.api.extention.FluentApiExtension;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApiSpigot;
import io.github.jwdeveoper.spigot.piano.core.services.SkinLoaderService;
import io.github.jwdeveoper.spigot.piano.core.services.SoundLoaderService;

public class PianoPluginExtension implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {
        var skinsLoader = fluentAPI.container().findInjection(SkinLoaderService.class);
        skinsLoader.load();

        var soundLoader = fluentAPI.container().findInjection(SoundLoaderService.class);
        soundLoader.load();
    }
}
