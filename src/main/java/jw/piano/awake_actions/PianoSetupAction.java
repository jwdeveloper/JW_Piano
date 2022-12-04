package jw.piano.awake_actions;

import jw.fluent.plugin.api.FluentApiBuilder;
import jw.fluent.plugin.api.FluentApiExtention;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.piano.data.PluginConfig;
import jw.piano.enums.PianoEffect;
import jw.piano.service.PianoService;
import org.bukkit.persistence.PersistentDataType;

public class PianoSetupAction implements FluentApiExtention {
    @Override
    public void onConfiguration(FluentApiBuilder builder) {

    }

    @Override
    public void onFluentApiEnable(FluentApi fluentAPI) throws Exception {
        var repository = FluentApi.injection().findInjection(PianoDataRepository.class);
        var service = FluentApi.injection().findInjection(PianoService.class);
        for (var pianoData : repository.findAll()) {
            if (pianoData.getEffect() == null) {
                pianoData.setEffect(PianoEffect.SIMPLE_PARTICLE);
            }
            clearOldModel(pianoData);
            service.create(pianoData);
        }
    }

    @Override
    public void onFluentApiDisabled(FluentApi fluentAPI) throws Exception {
        var repository = FluentApi.injection().findInjection(PianoDataRepository.class);
        var service =  FluentApi.injection().findInjection(PianoService.class);
        for (var pianoData : repository.findAll()) {
            service.delete(pianoData.getUuid());
        }
    }

    private void clearOldModel(PianoData pianoData) {
        var loc = pianoData.getLocation();
        var entities = loc.getWorld().getNearbyEntities(loc, 4, 6, 4);
        for (var e : entities) {
            if (e.getPersistentDataContainer().has(PluginConfig.PIANO_NAMESPACE, PersistentDataType.STRING)) {
                e.remove();
            }
        }

    }




}
