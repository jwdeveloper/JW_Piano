package jw.piano.factory;

import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.builders.FluentCommand;

@Injection(lazyLoad = false)
public class CommandsFactory {

    @Inject
    private Settings settings;

    public CommandsFactory()
    {
        var main = mainCommand();
        main.addSubCommand(textureCommand());
    }

    public SimpleCommand mainCommand() {
        return FluentCommand.create("piano_model")
                .nextStep()
                .nextStep()
                .onPlayerExecute(event ->
                {
                    var gui = FluentInjection.getPlayerInjection(MenuGUI.class, event.getPlayerSender());
                    gui.open(event.getPlayerSender());
                })
                .nextStep().buildAndRegister();
    }

    public SimpleCommand textureCommand()
    {
        return FluentCommand.create("texturepack")
                .setDescription("use this command to get latest texturepack")
                .nextStep()
                .nextStep()
                .onPlayerExecute(event ->
                {
                    event.getPlayerSender().setTexturePack(settings.getTexturesURL());
                })
                .nextStep()
                .build();
    }

}
