package jw.piano.api.observers;


import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.piano.api.data.enums.MidiPlayingType;
import jw.piano.api.data.models.midi.MidiPlayerSettings;
import lombok.Data;

@Data
public class MidiPlayerSettingsObserver
{
    private MidiPlayerSettings midiPlayerSetting;
    private Observer<Boolean> isPlayingObserver = new Observer<>();
    private Observer<Integer> speedObserver = new Observer<>();
    private Observer<MidiPlayingType> playingTypeObserver = new Observer<>();

    public MidiPlayerSettingsObserver()
    {
        isPlayingObserver.bind(MidiPlayerSettings.class,"isPlaying");
        speedObserver.bind(MidiPlayerSettings.class,"speed");
        playingTypeObserver.bind(MidiPlayerSettings.class,"playingType");
    }

    public void setObject(MidiPlayerSettings model)
    {
        midiPlayerSetting = model;
        isPlayingObserver.setObject(model);
        speedObserver.setObject(model);
        playingTypeObserver.setObject(model);
    }
}