package jw.piano.spigot.piano.managers.effects;

import jw.piano.api.data.PluginConsts;
import jw.piano.api.managers.effects.EffectInvoker;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class SimpleParticleEffect implements EffectInvoker {


    private World world;
    private Particle.DustOptions dustOptions;

    public SimpleParticleEffect()
    {
        var color  = PluginConsts.PARTICLE_COLOR;
        dustOptions = new Particle.DustOptions(color, 0.3F);
    }

    @Override
    public String getName() {
        return "simple particles";
    }

    @Override
    public void onNote(Location location, int noteIndex, int sensivity)
    {
        if(world == null)
            world = location.getWorld();
        world.spawnParticle(Particle.REDSTONE, location.clone().add( 0,1.8f,0), 2, dustOptions);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {

    }
}
