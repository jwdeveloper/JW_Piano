package jw.piano.mediator.midi.files;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.midi.MidiFile;

import java.util.ArrayList;

@Injection
public class MidiFilesHandler implements MediatorHandler<MidiFiles.Request, MidiFiles.Response> {

    private final String path;

    @Inject
    public MidiFilesHandler()
    {
        path = FluentApi.path()+FileUtility.separator()+"midi";
    }

    @Override
    public MidiFiles.Response handle(MidiFiles.Request request)
    {
        FileUtility.ensurePath(path);
        var files = FileUtility.getFolderFilesName(path, "mid","midi");
        var midiFiles = new ArrayList<MidiFile>();
        for(var file : files)
        {
            midiFiles.add(new MidiFile(path+FileUtility.separator()+file,file));
        }
        return new MidiFiles.Response(midiFiles);
    }
}
