package jw.piano.spigot.extentions;

import jw.fluent.api.spigot.gameobjects.implementation.GameObjectManager;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.piano.api.data.PluginPermission;
import jw.piano.api.data.models.PianoData;
import jw.piano.core.services.PianoService;
import jw.piano.spigot.gui.MenuGUI;
import jw.piano.spigot.piano.PianoImpl;

public class CommandsExtension implements FluentApiExtension {
    @Override
    public void onConfiguration(FluentApiSpigotBuilder builder) {

        builder.defaultCommand()
                .setName(PluginPermission.PIANO)
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.addPermissions(PluginPermission.PIANO_CMD);
                    propertiesConfig.setDescription("opens GUI where you can Create/Edit/Delete pianos");
                    propertiesConfig.setUsageMessage("/piano");
                })
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand("Create",commandBuilder ->
                    {
                        commandBuilder.eventsConfig(eventConfig ->
                        {
                            eventConfig.onPlayerExecute(playerCommandEvent ->
                            {
                                var pianoData = new PianoData();
                                pianoData.setLocation(playerCommandEvent.getPlayer().getLocation());
                                pianoData.setName("test piano");
                                var service = FluentApi.container().findInjection(PianoService.class);
                                service.create(pianoData);
                            });
                        });
                    });
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
