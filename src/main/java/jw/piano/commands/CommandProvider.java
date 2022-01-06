package jw.piano.commands;

import jw.piano.data.Settings;
import jw.piano.gui.MenuGUI;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.simple_commands.SimpleCommand;

@SpigotBean(lazyLoad = false)
public class CommandProvider
{

    private final Settings settings;

    public CommandProvider(Settings settings) {
        this.settings = settings;
        var main = mainCommand();
        main.addSubCommand(textureCommand());
    }


    public SimpleCommand mainCommand() {
        return SimpleCommand.newCommand("piano_model")
                .onPlayerExecute(event ->
                {
                    var gui = InjectionManager.getObjectPlayer(MenuGUI.class, event.getPlayerSender().getUniqueId());
                    gui.open(event.getPlayerSender());
                })
                .register();
    }

    public SimpleCommand textureCommand() {
        return SimpleCommand.newCommand("texturepack")
                .setDescription("use this command to get latest texturepack")
                .onPlayerExecute(event ->
                {
                    event.getPlayerSender().setTexturePack(settings.getTexturesURL());
                })
                .build();
    }

}
