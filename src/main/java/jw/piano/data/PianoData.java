package jw.piano.data;


import jw.piano.enums.PianoEffect;
import jw.piano.enums.PianoType;
import jw.spigot_fluent_api.data.implementation.DataModel;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import jw.spigot_fluent_api.fluent_plugin.languages.Lang;
import jw.spigot_fluent_api.utilites.files.json.annotations.JsonIgnore;
import lombok.Data;

import org.bukkit.Location;

@Data
public class PianoData extends DataModel
{
    private Location location;
    private Integer volume = 100;
    private Boolean enable = false;
    private PianoEffect effect = PianoEffect.FLYING_NOTE;
    private Boolean interactivePedal = true;
    private Boolean benchActive =true;
    private Boolean desktopClientAllowed = true;
    private Boolean detectKeyPress = true;
    private Integer skinId = 109;

    public String[] getDescriptionLines() {
        return new MessageBuilder()
                .field(Lang.get("gui.piano.piano-active.title"),enable.toString())
                .newLine()
                .field(Lang.get("gui.piano.volume.title"), volume)
                .newLine()
                .field(Lang.get("gui.piano.effect.title"), effect.toString())
                .toArray();

    }
}
