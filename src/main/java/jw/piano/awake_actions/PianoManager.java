package jw.piano.awake_actions;

import jw.piano.data.Consts;
import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.piano.enums.PianoEffect;
import jw.piano.enums.PianoType;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

public class PianoManager implements PluginPipeline {

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {
        var repository = FluentInjection.getInjection(PianoDataRepository.class);
        var service = FluentInjection.getInjection(PianoService.class);
        for (var pianoData : repository.findAll()) {
            if(pianoData.getPianoType() == null)
            {
                pianoData.setPianoType(PianoType.GRAND_PIANO);
            }
            if(pianoData.getEffect() == null)
            {
                pianoData.setEffect(PianoEffect.SIMPLE_PARTICLE);
            }
            clearOldModel(pianoData);
            service.create(pianoData);
        }
    }

    private void clearOldModel(PianoData pianoData)
    {
        var loc = pianoData.getLocation();
        var entities = loc.getWorld().getNearbyEntities(loc, 3, 6, 1);
        for(var e :entities)
        {
            if(e.getPersistentDataContainer().has(Consts.PIANO_NAMESPACE, PersistentDataType.STRING))
            {
                e.remove();
            }
        }

    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception {

        var repository = FluentInjection.getInjection(PianoDataRepository.class);
        var service = FluentInjection.getInjection(PianoService.class);
        for (var pianoData : repository.findAll()) {
          service.delete(pianoData.getUuid());
        }
    }
}
