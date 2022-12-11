package jw.piano.mediator.midi.reader;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.mediator.api.MediatorHandler;
import jw.piano.services.MidiReaderService;

import java.util.LinkedHashMap;
import java.util.Map;

@Injection
public class MidiReaderHandler implements MediatorHandler<MidiReader.Request, MidiReader.Response>
{
    private final Map<String, MidiReader.Response> catchedMidiData;
    private final MidiReaderService readerService;

    @Inject
    public MidiReaderHandler(MidiReaderService midiReaderService)
    {
        this.readerService = midiReaderService;
        catchedMidiData = new LinkedHashMap<>();
    }


    @Override
    public MidiReader.Response handle(MidiReader.Request request)
    {
        if(catchedMidiData.containsKey(request.path()))
        {
            return catchedMidiData.get(request.path());
        }

        try
        {
             var midiData = readerService.readMidiData(request.path());
             var response = new MidiReader.Response(midiData, "",true);
             catchedMidiData.put(request.path(), response);
             return response;
        }
        catch (Exception e)
        {
           return new MidiReader.Response(null,null,false);
        }
    }
}
