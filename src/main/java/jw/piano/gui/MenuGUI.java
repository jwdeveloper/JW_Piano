package jw.piano.gui;

import jw.piano.data.PianoData;
import jw.piano.data.PianoDataRepository;
import jw.piano.utility.PianoTypes;
import jw.spigot_fluent_api.dependency_injection.InjectionType;
import jw.spigot_fluent_api.dependency_injection.SpigotBean;
import jw.spigot_fluent_api.fluent_gui.implementation.crud_list_ui.CrudListUI;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@SpigotBean(injectionType = InjectionType.TRANSIENT)
public class MenuGUI extends CrudListUI<PianoData>
{
    private final PianoViewGUI pianoViewGUI;
    private final PianoDataRepository pianoDataRepository;

    public MenuGUI(PianoViewGUI pianoViewGUI, PianoDataRepository pianoDataRepository)
    {
        super("Menu",6);
        this.pianoDataRepository = pianoDataRepository;
        this.pianoViewGUI = pianoViewGUI;
          this.setEnableLogs(true);
        FluentPlugin.logSuccess(pianoDataRepository.toString());
    }

    @Override
    public void onInitialize() {
        this.setTitle("Menu");
        this.pianoViewGUI.setParent(this);
        this.setContentButtons(pianoDataRepository.getMany(),(data, button) ->
        {
            button.setTitle(data.name);
            button.setDescription(data.description);
            button.setMaterial(data.icon);
            button.setDataContext(data);
        });
        this.onInsert((player, button) ->
        {
            var location = player.getLocation().setDirection(new Vector(0, 0, 1));
            var pianoData = new PianoData();
            pianoData.name = "NEW PIANO";
            pianoData.setLocation(location);
            pianoData.setEnable(true);
            pianoData.setPianoType(PianoTypes.Grand_Piano);
            var result = pianoDataRepository.insertOne(pianoData);
            if(!result)
            {
                setTitle("Unable to create new piano");
            }
            this.refreshContent();
        });
        this.onDelete((player, button) ->
        {
            var result = pianoDataRepository.deleteOne(button.getDataContext());
            if(!result)
            {
                setTitle("Unable to remove piano");
            }
            this.refreshContent();
        });
        this.onGet((player, button) ->
        {
            openPianoView(player, button.getDataContext());
        });

    }
    public void openPianoView(Player player,PianoData pianoData)
    {
        pianoViewGUI.open(player, pianoData);
    }
}
