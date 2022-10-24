package jw.piano.listeners;

import jw.piano.data.PluginConfig;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent_api.spigot.events.EventBase;
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
