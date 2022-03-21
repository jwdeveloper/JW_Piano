package jw.piano.commands;


import jw.piano.gui.MenuGUI;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.builders.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.builders.SimpleCommandBuilder;
import jw.spigot_fluent_api.fluent_commands.interfaces.SimpleCommandConfig;

@Injection(lazyLoad = false)
public class MainCommand implements SimpleCommandConfig
{

    public MainCommand()
    {
        configureCommand();
    }

    @Override
    public SimpleCommand configureCommand()
    {
        var cmd = FluentCommand.create("piano_model")
                .nextStep()
                .nextStep()
                .onPlayerExecute(event ->
                {
                    var gui = FluentInjection.getPlayerInjection(MenuGUI.class, event.getPlayerSender());
                    gui.open(event.getPlayerSender());
                })
                .nextStep()
                .buildAndRegister();
        cmd.addSubCommand(FluentInjection.getInjection(TexturePackCommand.class));
        return cmd;
    }
}
