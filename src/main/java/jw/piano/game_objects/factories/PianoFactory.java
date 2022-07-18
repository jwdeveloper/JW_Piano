package jw.piano.game_objects.factories;

import jw.piano.game_objects.models.PianoKeyModel;
import jw.piano.game_objects.models.PianoPedalModel;
import org.bukkit.Location;

public class PianoFactory {


    public static PianoKeyModel getPianoKey(PianoPedalModel pedalModel, Location location, boolean isBlack, int index) {
        final var result = new PianoKeyModel(location,isBlack,index);

        result.setPedalModel(pedalModel);
        result.setRadious(10);

        return result;
    }

}
