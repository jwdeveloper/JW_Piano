
import jw.piano.data.Settings;
import jw.piano.game_objects.Piano;
import jw.piano.request_handlers.piano_details.PianoDetailsHandler;
import jw.piano.request_handlers.piano_details.PianoDetailsMapper;
import jw.piano.request_handlers.piano_details.PianoDetailsResponse;
import jw.piano.websocket.PianoWebSocket;
import jw.spigot_fluent_api.desing_patterns.mediator.implementation.SimpleMediator;
import jw.spigot_fluent_api.desing_patterns.mediator.interfaces.Mediator;
import jw.spigot_fluent_api.fluent_mapper.implementation.SimpleMapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.Mapper;
import jw.spigot_fluent_api.fluent_mapper.interfaces.MapperProfile;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.utilites.files.json.JsonUtility;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

public class WebSocketTest {


    @Test
    public void ShouldLoadFile() throws InterruptedException, IOException {
        Mapper mapper = new SimpleMapper();
        mapper.registerMappingProfile(PianoDetailsMapper.class);
        mapper.map(new Piano(null), PianoDetailsResponse.class);
    }
}
