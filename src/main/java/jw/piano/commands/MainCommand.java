package jw.piano.commands;


import jw.piano.data.Settings;
import jw.piano.game_objects.utils.MappedSounds;
import jw.piano.game_objects.utils.PlaySound;
import jw.piano.gui.MenuGUI;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.old.interfaces.SimpleCommandConfig;
import jw.spigot_fluent_api.utilites.benchmark.Benchmarker;


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
                }).build();
        return cmd;
    }
}
