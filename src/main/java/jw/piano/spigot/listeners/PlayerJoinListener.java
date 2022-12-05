package jw.piano.spigot.listeners;

import jw.piano.api.data.PluginConfig;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.events.EventBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

@Injection(lazyLoad = false)
public class PlayerJoinListener extends EventBase
{
    private final PluginConfig settings;

    @Inject
    public PlayerJoinListener(PluginConfig settings)
    {
        this.settings = settings;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(!settings.isDownloadResourcePack())
        {
            return;
        }
        event.getPlayer().setTexturePack(settings.TEXTURES_URL);
    }
}
