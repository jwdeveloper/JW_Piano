/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.core.mediator.midi.files;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.fluent.api.files.implementation.FileUtility;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.midi.reader.MidiFile;

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
