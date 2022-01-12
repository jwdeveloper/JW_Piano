package jw.piano.game_objects;

import jw.piano.game_objects.models.PianoKeyModel;
import jw.piano.game_objects.models.PianoModel;
import jw.piano.game_objects.models.PianoPedalModel;
import jw.spigot_fluent_api.fluent_tasks.FluentTaskTimer;
import jw.spigot_fluent_api.utilites.math.MathUtility;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class PianoInteractionHandler {
    private PianoKeyModel[] pianoKeys;
    private PianoPedalModel sustainPedal;

    private final int TICKS = 10;

    public PianoInteractionHandler(PianoModel pianoModel) {
        this.pianoKeys = pianoModel.getSortedKeys();
        this.sustainPedal = pianoModel.getSustainPedal();
    }

    public void onPlayerClick(Location location) {
        for (PianoKeyModel pianoKey : pianoKeys) {
            if (pianoKey.getHitBox().isCollider(location, 10)) {
                onHitKey(pianoKey);
                break;
            }
        }
    }

    private void onHitKey(PianoKeyModel pianoKey)
    {
        var particleSize = 0.3F;
        var particleColor = Color.fromRGB(MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255), MathUtility.getRandom(0, 255));
        var particle = new Particle.DustOptions(particleColor, particleSize);
        new FluentTaskTimer(TICKS, (time, taskTimer1) ->
        {
            pianoKey.setPedalPressed(sustainPedal.isPressed());
            pianoKey.press(pianoKey.getIndex(), 100, 1);
            pianoKey.getLocation()
                    .getWorld()
                    .spawnParticle(Particle.REDSTONE,
                            pianoKey.getLocation().clone().add(0, 2, 0),
                            1,
                            particle);
        }).stopAfterIterations(1).onStop(taskTimer1 ->
        {
            pianoKey.release(pianoKey.getIndex(), 0, 1);
            /*if(pianoKey == highlightedKey)
            {
                highlightedKey.setHighlighted(true);
            }*/
        }).run();
    }
}
