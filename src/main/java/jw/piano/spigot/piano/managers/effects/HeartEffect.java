package jw.piano.spigot.piano.managers.effects;

import jw.piano.api.managers.effects.EffectInvoker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class HeartEffect implements EffectInvoker {


    private World world;



    @Override
    public String getName() {
        return "heart";
    }

    @Override
    public void onNote(Location location, int noteIndex, int sensivity) {
        if (world == null)
            world = location.getWorld();
        world.spawnParticle(Particle.HEART, location.clone().add(0, 2f, -1), 1);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {

    }
}