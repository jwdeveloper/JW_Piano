package jw.piano.api.piano.token;

import org.bukkit.entity.Player;

public interface TokenGenerator
{
    public String generateAndSend(Player player);

    public String generate();
}
