import jw.piano.data.PianoConfig;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.Container;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.FluentInjection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import org.junit.Test;

import java.io.IOException;

public class CommandsTests
{
    @Test
    public void ShouldLoadFile() throws InterruptedException, IOException {

        Container container = new Container();
        container.register(PianoConfig.class, LifeTime.SINGLETON);
        FluentInjection.setInjectionContainer(container);
    }

}
