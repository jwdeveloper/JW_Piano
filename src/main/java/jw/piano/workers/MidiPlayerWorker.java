package jw.piano.workers;

import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.data.midi.MidiData;
import jw.piano.spigot.gameobjects.models.PianoGameObject;

public class MidiPlayerWorker
{
    private MidiData midiCraftData;
    private PianoGameObject piano;

    public MidiPlayerWorker(MidiData midiData, PianoGameObject piano)
    {
        this.midiCraftData = midiData;
        this.piano = piano;
    }

    public void start()
    {
        FluentLogger.LOGGER.info("STARTED PLAYING");
        FluentApi.tasks().taskTimer(1,(iteration, task) ->
        {
            var events = midiCraftData.getEvents(iteration);
            if(events == null)
            {
                //  FluentLogger.LOGGER.info("TICK EMPTY",iteration);
                return;
            }
            //  FluentLogger.LOGGER.info("TICK EVENTS",iteration,events.size());
            for(var event : events)
            {
                switch (event.eventType()) {
                    case 0 -> piano.invokeNote(event.velocity(), event.noteId(), event.velocity());
                    case 1 -> piano.invokePedal(event.velocity(), event.noteId());
                    case 2 -> piano.refreshKeys();
                }
            }
        }).run();

    }

    public void stop()
    {
        FluentLogger.LOGGER.info("STOPED PLAYING");
    }
}
