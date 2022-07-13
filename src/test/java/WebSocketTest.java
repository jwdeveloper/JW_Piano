
import jw.piano.game_objects.Piano;
import jw.piano.request_handlers.piano_details.PianoDetailsMapper;
import jw.piano.request_handlers.piano_details.PianoDetailsResponse;
import jw.spigot_fluent_api.fluent_mapper.implementation.SimpleMapper;
import jw.spigot_fluent_api.fluent_mapper.api.Mapper;
import jw.spigot_fluent_api.utilites.ActionResult;
import org.junit.Test;

import java.io.IOException;

public class WebSocketTest {


    @Test
    public void ShouldLoadFile() throws InterruptedException, IOException {
        Mapper mapper = new SimpleMapper();
        mapper.registerMappingProfile(PianoDetailsMapper.class);
        mapper.map(new Piano(null), PianoDetailsResponse.class);
    }


    @Test
    public void ShoudlGetType() {
       var type = ActionResult.<String>type();
       var a = new ActionResult<String>().getClass();
       var i =0;
    }
}
