package jw.piano.gui;

import jw.data.repositories.RepositoryGUI;
import jw.dependency_injection.Injectable;
import jw.dependency_injection.InjectionType;
import jw.gui.examples.chestgui.BuilderListGUI;
import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.piano.utility.PianoTypes;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@Injectable(injectionType = InjectionType.TRANSIENT)
public class MenuGUI extends RepositoryGUI<PianoData>
{
    private final PianoViewGUI pianoViewGUI;
    private final PianoDataRepository pianoDataRepository;

    public MenuGUI(PianoViewGUI pianoViewGUI, PianoDataRepository pianoDataRepository)
    {
        super(null, "Menu", pianoDataRepository);

        this.pianoDataRepository = pianoDataRepository;
        this.pianoViewGUI = pianoViewGUI;
    }

    @Override
    public void onInitialize() {
        super.onInitialize();
        this.setTitle("Menu");
        this.getCopyButton().setActive(false);
        this.pianoViewGUI.setParent(this);
        this.onInsert = value ->
        {
            Location location = this.player.getLocation();
            location.setDirection(new Vector(0, 0, 1));

            PianoData pianoData = new PianoData();
            pianoData.name = value;
            pianoData.location = location;
            pianoData.isEnable = true;
            pianoData.pianoType = PianoTypes.Grand_Piano;
            this.getRepository().insertOne(pianoData);
            this.open(player);
        };
        this.onClickContent = (player, button) ->
        {
            openPianoView(player, button.getHoldingObject());
        };
    }
    public void openPianoView(Player player,PianoData pianoData)
    {
        pianoViewGUI.open(player, pianoData);
    }
}
