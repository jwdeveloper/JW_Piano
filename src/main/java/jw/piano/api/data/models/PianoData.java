package jw.piano.api.data.models;


import jw.fluent.api.files.api.models.DataModel;
import jw.piano.api.data.models.midi.MidiPlayerSettings;
import lombok.Data;

import org.bukkit.Location;

@Data
public class PianoData extends DataModel {

    private Location location;
    private Integer volume = 100;
    private Boolean desktopClientAllowed = true;
    private Boolean interactiveKeyboard = true;
    private Boolean active = true;
    private String skinName = "grand piano";
    private String effectName = "none";

    private PedalsSettings pedalsSettings = new PedalsSettings();
    private BenchSettings benchSettings = new BenchSettings();
    private MidiPlayerSettings midiPlayerSettings = new MidiPlayerSettings();
}
