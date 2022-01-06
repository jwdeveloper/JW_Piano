package jw.piano;

import jw.InitializerAPI;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable()
    {
        InitializerAPI.attachePlugin(this);
        InitializerAPI.useDependencyInjection();
        InitializerAPI.getDataManager().load();
    }

    @Override
    public void onDisable()
    {
        InitializerAPI.getDataManager().save();
    }

    public static Main getInstance()
    {
        return Main.getPlugin(Main.class);
    }
}
