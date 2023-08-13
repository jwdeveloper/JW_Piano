package io.github.jwdeveloper.spigot.piano.commands;

import io.github.jwdeveloper.ff.extension.commands.api.annotations.Command;
import org.bukkit.entity.Player;

@Command(name = "piano",
        description = "Base plugin commands, /piano opens piano list",
        usage = "/piano",
        permissions = {"piano.commands.piano"}
)
//PluginPermissions.COMMANDS.PIANO)
public class PluginPianoCommand {
    @Command
    public void onPlayerTrigger(Player player) {
        /*
         var gui = FluentApi.playerContext().find(PianoListGUI.class, event.getPlayer());
                       // gui.open(event.getPlayer());
         */
    }
}
