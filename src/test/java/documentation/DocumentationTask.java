package documentation;

import be.seeseemelk.mockbukkit.MockBukkit;
import io.github.jwdeveloper.ff.tools.description.DescriptionGeneratorTool;
import jw.PianoPluginMainMock;
import org.junit.Test;

public class DocumentationTask
{
    @Test
    public void run()
    {
        MockBukkit.mock();
        var description = new DescriptionGeneratorTool( MockBukkit.createMockPlugin(), new PianoPluginMainMock(), null);
        description.generate("siema");
        MockBukkit.unmock();
    }

}
