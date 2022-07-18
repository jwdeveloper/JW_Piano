package jw.piano.commands;


import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.old.interfaces.SimpleCommandConfig;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

@Injection(lazyLoad = false)
public class MainCommand implements SimpleCommandConfig {
    private final Settings settings;
    @Inject
    public MainCommand(Settings settings) {
        this.settings = settings;
        configureCommand();
    }

    @Override
    public SimpleCommand configureCommand() {
        var cmd = FluentCommand.create("piano")
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        var gui = FluentInjection.getPlayerInjection(MenuGUI.class, event.getPlayerSender());
                        gui.open(event.getPlayerSender());
                    });
                })
                .subCommandsConfig(subCommandConfig ->
                {
                    subCommandConfig.addSubCommand(textureCommand());
                })
                .build();
        return cmd;
    }

    public CommandBuilder textureCommand() {
        return FluentCommand.create("get-texturepack")
                .propertiesConfig(propertiesConfig ->
                {
                    propertiesConfig.setDescription("use this command to get latest texturepack");
                })
                .eventsConfig(eventConfig ->
                {
                    eventConfig.onPlayerExecute(event ->
                    {
                        event.getPlayerSender().setTexturePack(settings.getTexturesURL());
                    });
                });
    }
}
