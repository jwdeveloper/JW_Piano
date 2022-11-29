
import jw.piano.game_objects.Piano;
import jw.piano.handlers.piano_details.PianoDetailsMapper;
import jw.piano.handlers.piano_details.PianoDetailsResponse;
import jw.fluent_api.mapper.implementation.SimpleMapper;
import jw.fluent_api.mapper.api.Mapper;
import jw.fluent_api.utilites.ActionResult;
import org.junit.Test;

import java.io.IOException;

public class WebSocketTest {


    @Test
    public void ShouldLoadFile() throws InterruptedException, IOException {
        Mapper mapper = new SimpleMapper(null);
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
