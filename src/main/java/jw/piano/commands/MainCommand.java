package jw.piano.commands;


import jw.piano.data.PianoPermission;
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
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.updates.implementation.SimpleUpdater;
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
                }).subCommandsConfig(subCommandConfig ->
                {

                    subCommandConfig.addSubCommand(FluentCommand.create("update")
                            .propertiesConfig(propertiesConfig ->
                            {
                                propertiesConfig.addPermissions(PianoPermission.PIANO);
                                propertiesConfig.addPermissions(PianoPermission.UPDATE);
                            })
                            .eventsConfig(eventConfig ->
                            {
                                eventConfig.onConsoleExecute(consoleCommandEvent ->
                                {
                                    var simpleUpdater = FluentInjection.getInjection(SimpleUpdater.class);
                                    simpleUpdater.downloadUpdate(consoleCommandEvent.getConsoleSender());
                                });
                                eventConfig.onPlayerExecute(event ->
                                {
                                    var simpleUpdater = FluentInjection.getInjection(SimpleUpdater.class);
                                    simpleUpdater.downloadUpdate(event.getSender());
                                });
                            })

                    );

                }).build();
        return cmd;
    }
}
