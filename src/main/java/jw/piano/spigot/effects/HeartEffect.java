package jw.piano.spigot.effects;

import jw.piano.spigot.effects.api.PianoEffectInvoker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class HeartEffect implements PianoEffectInvoker {


    private World world;

    public HeartEffect()
    {

    }

    @Override
    public void invoke(Location location, int noteIndex, int sensivity)
    {
        if(world == null)
            world = location.getWorld();
        world.spawnParticle(Particle.HEART, location.clone().add( 0,2f,-1),1);
    }

    @Override
    public void destroy() {

    }

    @Override
    public void create() {

    }
}