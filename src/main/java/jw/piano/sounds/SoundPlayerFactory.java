package jw.piano.sounds;

import jw.piano.sounds.v1_18_R1.SoundPlayer_1_18_R1;
import jw.fluent_api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent_api.minecraft.logger.FluentLogger;
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
