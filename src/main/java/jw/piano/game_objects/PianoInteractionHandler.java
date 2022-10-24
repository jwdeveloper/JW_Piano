package jw.piano.game_objects;

import jw.piano.game_objects.models.PianoKeyModel;
import jw.piano.game_objects.models.PianoModel;
import jw.fluent_api.spigot.tasks.FluentTasks;
import org.bukkit.Location;

public class PianoInteractionHandler {

    private final PianoModel pianoModel;
    private final int TICKS = 10;

    public PianoInteractionHandler(PianoModel pianoModel) {
        this.pianoModel = pianoModel;
    }

    public boolean handleClick(Location location) {
        for (final var piano : pianoModel.getSortedKeys())
        {
            if (piano.getHitBox().isCollider(location, piano.getRadious())) {
               onHitKey(piano);
               return true;
            }
        }
        return false;
    }

    private void onHitKey(PianoKeyModel pianoKey) {
        FluentTasks.taskTimer(TICKS, (iteration, task) ->
                {
                    pianoKey.setPedalPressed(pianoModel.getSustainPedal().isPressed());
                    pianoKey.press();
                }).stopAfterIterations(1)
                .onStop(task ->
                {
                    pianoKey.release();
                }).run();
    }
}
