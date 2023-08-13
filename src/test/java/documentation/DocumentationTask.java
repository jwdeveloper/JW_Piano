package documentation;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.jwdeveloper.ff.tools.description.DescriptionGeneratorTool;
import jw.piano.api.data.PluginConsts;
import jw.piano.spigot.PianoExtension;
import org.junit.Test;

public class DocumentationTask
{
    @Test
    public void run()
    {
        MockBukkit.mock();
        var description = new DescriptionGeneratorTool( MockBukkit.createMockPlugin(), new PianoExtension(), null);
        description.addParameter("CLIENT_APP_URL",  PluginConsts.CLIENT_APP_URL);
        description.addParameter("RESOURCEPACK_URL",  PluginConsts.RESOURCEPACK_URL);
        description.addParameter("ORAXEN_CONTENT",  "{ }");


        description.generate("siema");
        MockBukkit.unmock();
    }

}
