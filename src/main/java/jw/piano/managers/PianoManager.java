package jw.piano.managers;

import jw.piano.data.PianoDataRepository;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.dependency_injection.InjectionManager;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.configuration.pipeline.PluginPipeline;

public class PianoManager implements PluginPipeline
{
    @Override
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception
    {
      var repository = InjectionManager.getObject(PianoDataRepository.class);
      var service = InjectionManager.getObject(PianoService.class);

      for(var pianoData : repository.getMany())
      {

        service.create(pianoData);
      }
    }

    @Override
    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception
    {
        var repository = InjectionManager.getObject(PianoDataRepository.class);
        var service = InjectionManager.getObject(PianoService.class);

        for(var pianoData : repository.getMany())
        {
            service.delete(pianoData.getUuid());
        }
    }
}
