package jw.piano.api.observers;


import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.piano.api.data.enums.MidiPlayingType;
import jw.piano.api.data.models.midi.MidiPlayerSettings;
import lombok.Data;

@Data
public class MidiPlayerSettingsObserver
{
    private MidiPlayerSettings midiPlayerSetting;
    private Observer<Boolean> isPlayingInLoopObserver = new Observer<>();
    private Observer<Boolean> isPlayingObserver = new Observer<>();
    private Observer<Integer> speedObserver = new Observer<>();
    private Observer<MidiPlayingType> midiPlayingTypeObserver = new Observer<>();

    public MidiPlayerSettingsObserver()
    {
        isPlayingInLoopObserver.bind(MidiPlayerSettings.class,"isPlayingInLoop");
        isPlayingObserver.bind(MidiPlayerSettings.class,"isPlaying");
        speedObserver.bind(MidiPlayerSettings.class,"speed");
        midiPlayingTypeObserver.bind(MidiPlayerSettings.class,"midiPlayingType");
    }

    public void setObject(MidiPlayerSettings model)
    {
        midiPlayerSetting = model;
        isPlayingInLoopObserver.setObject(model);
        isPlayingObserver.setObject(model);
        speedObserver.setObject(model);
        midiPlayingTypeObserver.setObject(model);
    }
}