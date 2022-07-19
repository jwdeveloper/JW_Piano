package jw.piano.awake_actions;

import jw.piano.data.PianoDataRepository;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

public class PianoManager implements PluginPipeline {

    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception {
        var repository = FluentInjection.getInjection(PianoDataRepository.class);
        var service = FluentInjection.getInjection(PianoService.class);

        for (var pianoData : repository.findAll()) {
            service.create(pianoData);
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
