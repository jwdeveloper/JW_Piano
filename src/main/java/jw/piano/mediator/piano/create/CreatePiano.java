package jw.piano.mediator.piano.create;

import org.bukkit.entity.Player;

public class CreatePiano
{
    public record Request(Player player){}

    public record Response(boolean created, String message){}
}
