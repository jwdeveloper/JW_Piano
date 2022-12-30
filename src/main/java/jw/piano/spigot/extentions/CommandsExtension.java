package jw.piano.spigot.extentions;

import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.PianoData;
import jw.piano.core.services.PianoService;
import jw.piano.spigot.gui.MenuGUI;

public class CommandsExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.defaultCommand()
                .setName("piano")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(PluginPermissions.COMMANDS.PIANO);
                    propertiesConfig.setDescription(PluginTranslations.COMMANDS.PIANO.DESC);
                    propertiesConfig.setUsageMessage("/piano");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var gui = FluentApi.playerContext().find(MenuGUI.class, event.getPlayer());
                        gui.open(event.getPlayer());
                    });
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }
}
