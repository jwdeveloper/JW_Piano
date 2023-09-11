package io.github.jwdeveloper.spigot.piano.api.data;

import io.github.jwdeveloper.ff.extension.files.api.DataModel;

import io.github.jwdeveloper.spigot.piano.api.data.keyboard.KeyboardData;
import io.github.jwdeveloper.spigot.piano.api.data.midi.MidiPlayerData;
import lombok.Data;
import org.bukkit.Color;
import org.bukkit.Location;

@Data
public class PianoData extends DataModel
{
    private Location location;
    private Integer volume = 100;
    private Boolean desktopClientAllowed = true;
    private Boolean interactiveKeyboard = true;
    private Boolean showPianist = false;
    private Boolean active = true;
    private String skinName = "grand piano";
    private String effectName = "none";
    private String soundName = "default";
    private Color color = Color.WHITE;

   // @ObserverField
    private KeyboardData keyboard = new KeyboardData();

   // @ObserverField
    private PedalsData pedals = new PedalsData();

 //   @ObserverField
    private BenchData bench = new BenchData();

  //  @ObserverField
    private MidiPlayerData midiPlayer = new MidiPlayerData();
}
