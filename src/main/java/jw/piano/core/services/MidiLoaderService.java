package jw.piano.core.services;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.files.implementation.FileUtility;
import jw.piano.api.midiplayer.MidiParser;
import jw.piano.api.midiplayer.NoteTrack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
