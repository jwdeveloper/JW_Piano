package jw.piano.service;

import jw.piano.data.Settings;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import org.bukkit.entity.Player;

@Injection(lazyLoad = false)
public class WebClientLinkGeneratorService
{
    @Inject
    private Settings settings;

    public String generateLink(Player player)
    {
      return settings.getWebClientURL();
    }
}
