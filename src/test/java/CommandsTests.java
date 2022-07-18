import jw.piano.commands.MainCommand;
import jw.piano.data.Settings;
import jw.piano.game_objects.Piano;
import jw.piano.handlers.piano_details.PianoDetailsMapper;
import jw.piano.handlers.piano_details.PianoDetailsResponse;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.fluent_mapper.api.Mapper;
import jw.spigot_fluent_api.fluent_mapper.implementation.SimpleMapper;
import org.junit.Test;

import java.io.IOException;

public class CommandsTests
{
    @Test
    public void ShouldLoadFile() throws InterruptedException, IOException {

        Container container = new Container();
        container.register(Settings.class, LifeTime.SINGLETON);
        container.register(MainCommand.class, LifeTime.SINGLETON);
        FluentInjection.setInjectionContainer(container);

        var cmd = FluentInjection.getInjection(MainCommand.class);
    }

}
