package jw.piano.data.models;


import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.enums.PianoEffect;
import jw.fluent.api.files.api.models.DataModel;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import lombok.Data;

import org.bukkit.Location;

@Data
public class PianoData extends DataModel {

    private Location location;
    private Integer volume = 100;
    private Boolean enable = false;
    private PianoEffect effect = PianoEffect.FLYING_NOTE;

    private Boolean interactivePedal = true;

    private Boolean desktopClientAllowed = true;

    private Boolean detectKeyPress = true;

    private Integer skinId = 109;

    private Boolean showGuiHitBox = true;

    private BenchSettings benchSettings = new BenchSettings();

    private PianoMidiSettings pianoMidiSettings = new PianoMidiSettings();

    public String[] getDescriptionLines() {

        var lang = FluentApi.translator();
        return new MessageBuilder()
                .field(lang.get("gui.piano.piano-active.title"), enable.toString())
                .newLine()
                .field(lang.get("gui.piano.volume.title"), volume)
                .newLine()
                .field(lang.get("gui.piano.effect.title"), effect.toString())
                .toArray();
    }
}
