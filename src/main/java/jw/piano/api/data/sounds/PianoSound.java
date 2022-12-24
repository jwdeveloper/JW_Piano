package jw.piano.api.data.sounds;

import lombok.Data;
import org.bukkit.SoundCategory;

import java.util.HashMap;
import java.util.Map;

@Data
public class PianoSound
{
    private String name;
    private String namespace = "minecraft";
    private SoundCategory soundCategory = SoundCategory.VOICE;
    private Map<Integer,String> soundsWithoutPedal = new HashMap<>();
    private Map<Integer, String> soundsWithPedal = new HashMap<>();


    public String getSound(int midiIndex, boolean isPedalPressed) {
        return isPedalPressed ? soundsWithPedal.get(midiIndex) : soundsWithoutPedal.get(midiIndex);
    }
}
