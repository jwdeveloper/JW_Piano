package jw.piano.gui;

import jw.piano.data.PianoData;
import jw.piano.enums.PianoType;
import jw.piano.game_objects.Piano;
import jw.piano.service.PianoDataService;
import jw.piano.service.PianoService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.fluent_gui.implementation.crud_list_ui.CrudListUI;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.UUID;

@Injection
public class MenuGUI extends CrudListUI<PianoData> {

    private final PianoViewGUI pianoViewGUI;
    private final PianoDataService pianoDataService;
    private final PianoService pianoService;

    @Inject
    public MenuGUI(PianoViewGUI pianoViewGUI,
                   PianoDataService pianoDataService,
                   PianoService pianoService) {
        super("Menu", 6);
        this.pianoService = pianoService;
        this.pianoDataService = pianoDataService;
        this.pianoViewGUI = pianoViewGUI;
        this.setEnableLogs(true);
    }

    @Override
    public void onInitialize() {
        setTitle("Menu");
        pianoViewGUI.setParent(this);
        setContentButtons(pianoDataService.get(), (data, button) ->
        {
            button.setTitle(data.getName());
            button.setDescription(data.getDescription());
            button.setMaterial(data.getIcon());
            button.setDataContext(data);
        });
        onInsert((player, button) ->
        {
            var location = player.getLocation().setDirection(new Vector(0, 0, 1));
            var pianoData = new PianoData();
            pianoData.setName("NEW PIANO");
            pianoData.setLocation(location);
            pianoData.setEnable(true);
            pianoData.setPianoType(PianoType.GRAND_PIANO);
            var result = pianoDataService.insert(pianoData);
            if (!result) {
                setTitle("Unable to create new piano");
            }
            refreshContent();
        });
        onDelete((player, button) ->
        {
            var piano = button.<PianoData>getDataContext();
            var result = pianoDataService.delete(piano.getUuid());
            if (!result) {
                setTitle("Unable to remove piano");
            }
            refreshContent();
        });
       onGet((player, button) ->
        {
            var pianoData = button.<PianoData>getDataContext();
            var result = pianoService.get(pianoData.getUuid());
            if (result.isEmpty()) {
                setTitle("Unable to find piano");
            }
            var uuid = pianoData.getUuid();
            FluentPlugin.logSuccess(uuid.toString()+" POPRAWNE");
            FluentPlugin.logSuccess(uuid.getMostSignificantBits()+"  "+uuid.getLeastSignificantBits());

            var id = new UUID(uuid.getMostSignificantBits(),uuid.getLeastSignificantBits());
            FluentPlugin.logSuccess(id+" Nowe");
            openPianoView(player, result.get());
        });
    }

    public void openPianoView(Player player, Piano piano)
    {
        pianoViewGUI.open(player, piano);
    }
}
