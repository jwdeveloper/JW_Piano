package jw.piano.extentions;

import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.api.enums.AccessType;
import jw.fluent.plugin.api.FluentApiExtension;
import jw.fluent.plugin.api.FluentApiSpigotBuilder;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiSpigot;
import jw.piano.api.data.PluginPermission;
import jw.piano.services.PianoService;
import jw.piano.spigot.gui.MenuGUI;

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
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var gui = FluentApi.playerContext().find(MenuGUI.class, event.getPlayer());
                        gui.open(event.getPlayer());
                    });
                })
                .subCommandsConfig(subCommandConfig ->
                {
                //  subCommandConfig.addSubCommand(clearCommand());
                });
    }

    @Override
    public void onFluentApiEnable(FluentApiSpigot fluentAPI) throws Exception {

    }

    @Override
    public void onFluentApiDisabled(FluentApiSpigot fluentAPI) throws Exception {

    }

    private CommandBuilder clearCommand() {
       return FluentApi.commands("clear")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("clears models for all pianos");
                    propertiesConfig.setUsageMessage("/" + PluginPermission.PIANO + " clear");
                   //propertiesConfig.addPermissions(PluginPermission.CLEAR_CMD);
                    propertiesConfig.setAccess(AccessType.CONSOLE);
                    propertiesConfig.setAccess(AccessType.PLAYER);
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onExecute(commandEvent ->
                    {
                        var pianoService = FluentApi.container().findInjection(PianoService.class);
                        var clearCount = pianoService.clear();
                        FluentApi.messages().chat().info().text(clearCount).text("Pianos has been cleared").send(commandEvent.getSender());
                    });
                });
    }
}
