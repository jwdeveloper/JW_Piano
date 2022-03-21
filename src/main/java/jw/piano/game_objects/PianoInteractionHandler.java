package jw.piano.game_objects;

import jw.piano.game_objects.models.PianoKeyModel;
import jw.piano.game_objects.models.PianoModel;
import jw.piano.game_objects.models.PianoPedalModel;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import jw.spigot_fluent_api.fluent_tasks.FluentTasks;
import jw.spigot_fluent_api.utilites.math.MathUtility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class PianoInteractionHandler {

    private final PianoModel pianoModel;
    private final int TICKS = 10;
    private final float PARTICLE_SIZE = 0.3f;
    private final Color PARTICLE_COLOR = Color.fromRGB(MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255));

    public PianoInteractionHandler(PianoModel pianoModel) {
        this.pianoModel = pianoModel;
    }

    public void onPlayerClick(Location location) {
        for (PianoKeyModel pianoKey : pianoModel.getSortedKeys()) {
            if (pianoKey.getHitBox().isCollider(location, 10)) {
                onHitKey(pianoKey);
                break;
            }
        }
    }

    private void onHitKey(PianoKeyModel pianoKey) {
        final var particle = new Particle.DustOptions(PARTICLE_COLOR, PARTICLE_SIZE);
        FluentTasks.taskTimer(TICKS,(iteration, task) ->
        {
            pianoKey.setPedalPressed(pianoModel.getSustainPedal().isPressed());
            pianoKey.press();
            pianoKey.getLocation()
                    .getWorld()
                    .spawnParticle(Particle.REDSTONE,
                            pianoKey.getLocation().clone().add(0, 2, 0),
                            1,
                            particle);
        }).stopAfterIterations(1)
                .onStop(task ->
                {
                    pianoKey.release();
            /*if(pianoKey == highlightedKey)
            {
                highlightedKey.setHighlighted(true);
            }*/
                }).run();
    }
}
