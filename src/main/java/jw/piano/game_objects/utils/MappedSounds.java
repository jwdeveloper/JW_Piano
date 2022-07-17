package jw.piano.game_objects.utils;

import java.util.HashMap;
import java.util.Map;

public class MappedSounds
{


    private static Map<Integer, String> withPedal;
    private static Map<Integer, String> withoutPedal;
    static
    {
     withPedal = new HashMap<>();
     withoutPedal = new HashMap<>();
     for(int i= Consts.MIDI_KEY_OFFSET;i< Consts.MIDI_KEY_OFFSET+Consts.NOTES_NUMBER;i++)
     {
         withPedal.put(i,"minecraft:1c."+ i);
         withoutPedal.put(i,"minecraft:1."+ i);
     }
    }

    public static String getSound(int noteId,boolean pedal)
    {
       return pedal?withPedal.get(noteId): withoutPedal.get(noteId);
    }
}
