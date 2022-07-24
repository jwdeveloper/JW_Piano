package jw.piano.data;


import jw.piano.enums.PianoEffect;
import jw.piano.enums.PianoType;
import jw.spigot_fluent_api.data.implementation.DataModel;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import lombok.Data;

import org.bukkit.Location;

@Data
public class PianoData extends DataModel
{
    private PianoType pianoType = PianoType.NONE;
    private Location location;
    private Integer volume = 100;
    private Boolean enable = false;
    private PianoEffect effect = PianoEffect.SIMPLE_PARTICLE;


    public String[] getDescriptionLines() {
        return new MessageBuilder()
                .field("Active ",enable.toString())
                .newLine()
                .field("Volume", volume)
                .newLine()
                .field("Effect", effect.toString())
                .toArray();

    }
}
