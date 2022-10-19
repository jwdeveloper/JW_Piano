package jw.piano.game_objects.models.effects;

import jw.piano.data.PluginConfig;
import jw.fluent_api.minecraft.tasks.FluentTaskTimer;
import jw.fluent_api.minecraft.tasks.FluentTasks;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WaterfallEffect implements PianoEffectInvoker {
    private FluentTaskTimer taskTimer;
    private final List<AdvancedParticleDto> particles;
    private final int maxTime = 60;
    private final Particle.DustOptions dustOptions;
    private final double particleHeight = 3.5;
    private final double particleStartY = 1.7;
    private final double particleWidthZ =0.5;
    private final double particleSpeedZ = 0.005;
    public WaterfallEffect() {
        particles = new ArrayList<>(100);
        var color = PluginConfig.PARTICLE_COLOR;
        dustOptions = new Particle.DustOptions(color, 0.15F);
    }

    @Override
    public void invoke(Location location, int noteIndex, int sensivity) {

        location = location.clone().add(0, 0, 0);
        var note = particles.stream().filter(c -> c.free).findFirst();
        if (note.isPresent()) {
            note.get().enable(location);
            return;
        }

        var particle = new AdvancedParticleDto();
        particle.enable(location);
        particles.add(particle);
    }

    @Override
    public void destory() {
        particles.clear();
        taskTimer.stop();
    }

    @Override
    public void create() {
        taskTimer = FluentTasks.taskTimer(1, (iteration, task) ->
        {
            for (var particle : particles) {
                if (particle.free) {
                    continue;
                }
                if (particle.time > maxTime) {
                    particle.free = true;
                    continue;
                }
                particle.next();
            }
        }).run();
    }

    class AdvancedParticleDto {
        public boolean free;
        private Location location;
        private World world;
        private int time;

        public void enable(Location location) {
            this.location = location.clone();
            world = location.getWorld();
            time = 0;
            free = false;
        }

        public void next() {
            world.spawnParticle(Particle.REDSTONE,
                    location.clone().add(
                            0,
                            particleStartY + Math.sin(Math.toRadians(time) * particleHeight) * particleWidthZ,
                            -time * particleSpeedZ),
                    1,
                    dustOptions);
            time++;
        }
    }
}
