package jw.piano.gameobjects.models.effects;

import jw.piano.api.data.PluginConfig;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class SimpleParticleEffect implements PianoEffectInvoker {


    private World world;
    private Particle.DustOptions dustOptions;

    public SimpleParticleEffect()
    {
        var color  = PluginConfig.PARTICLE_COLOR;
        dustOptions = new Particle.DustOptions(color, 0.3F);
    }

    @Override
    public void invoke(Location location, int noteIndex, int sensivity)
    {
        if(world == null)
            world = location.getWorld();
        world.spawnParticle(Particle.REDSTONE, location.clone().add( 0,1.8f,0), 2, dustOptions);
    }

    @Override
    public void destory() {

    }

    @Override
    public void create() {

    }
}
