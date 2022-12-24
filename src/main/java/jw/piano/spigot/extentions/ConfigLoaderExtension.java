package jw.piano.spigot.extentions;

import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.core.services.SkinLoaderService;
import jw.piano.core.services.SoundLoaderService;

public class ConfigLoaderExtension implements FluentApiExtension {

    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder)
    {

    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception
    {

        var skinsLoader = fluentAPI.container().findInjection(SkinLoaderService.class);
        skinsLoader.load();

        var soundLoader = fluentAPI.container().findInjection(SoundLoaderService.class);
        soundLoader.load();
    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
