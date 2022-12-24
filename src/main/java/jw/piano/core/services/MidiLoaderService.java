package jw.piano.core.services;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.midi.reader.MidiData;
import jw.piano.api.data.midi.reader.MidiRawData;
import jw.piano.api.data.midi.reader.MidiCraftEvent;
import jw.piano.api.midiplayer.midiparser.MidiParser;
import jw.piano.api.midiplayer.midiparser.NoteTrack;
import org.bukkit.ChatColor;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static javax.sound.midi.ShortMessage.*;

@Injection
public class MidiLoaderService {

    private final Map<String, NoteTrack> midiData;

    @Inject
    public MidiLoaderService()
    {
        midiData = new HashMap<>();
    }

    public NoteTrack load(String path) throws Exception {

        if(midiData.containsKey(path))
        {
          return midiData.get(path);
        }
        if(!FileUtility.pathExists(path))
        {
            throw new Exception("path not exists");
        }

        var file = new File(path);
        var data = MidiParser.loadFile(file);
        midiData.put(path, data);
        return data;
    }



}
