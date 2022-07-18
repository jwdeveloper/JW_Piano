package jw.piano.listeners;

import jw.piano.data.Settings;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_events.EventBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

@Injection(lazyLoad = false)
public class PlayerJoinListener extends EventBase
{
    private final Settings settings;

    @Inject
    public PlayerJoinListener(Settings settings)
    {
        this.settings = settings;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        if(!settings.isAutoDownloadTexturepack())
        {
            return;
        }
        event.getPlayer().setTexturePack(settings.getTexturesURL());
    }
}
