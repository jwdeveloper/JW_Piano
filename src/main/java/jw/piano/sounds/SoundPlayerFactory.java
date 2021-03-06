package jw.piano.sounds;

import jw.piano.sounds.v1_18_R1.SoundPlayer_1_18_R1;
import jw.piano.sounds.v1_19.SoundPlayer_1_19;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import org.bukkit.Bukkit;
import org.bukkit.Location;


@Injection(lazyLoad = false)
public class SoundPlayerFactory
{
    private NmsSoundPlayer instance;

    public SoundPlayerFactory()
    {
        instance = getInstance();
    }

    public void play(Location location, int note, float volume, boolean pressed)
    {
        instance.play(location,note,volume,pressed);
    }
    private NmsSoundPlayer getInstance()
    {
        try
        {
            var version =  Bukkit.getVersion();
            if(version.contains("1.18"))
            {
                return new SoundPlayer_1_18_R1();
            }
        }
        catch (Exception e)
        {
            FluentLogger.info("Default note player in use");
        }
        return new DefaultPlayer();
    }
}
