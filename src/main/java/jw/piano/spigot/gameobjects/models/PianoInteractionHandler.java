package jw.piano.spigot.gameobjects.models;

import jw.fluent.plugin.implementation.modules.tasks.FluentTasks;
import org.bukkit.Location;

public class PianoInteractionHandler {

    private final PianoGameObject pianoModel;
    private final int TICKS = 10;

    public PianoInteractionHandler(PianoGameObject pianoModel) {
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
