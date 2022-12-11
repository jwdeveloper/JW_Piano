package jw.piano.spigot.gui.midi;


import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.inventory_gui.implementation.picker_list_ui.PickerUI;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.piano.data.midi.MidiFile;
import jw.piano.data.models.PianoMidiFile;
import jw.piano.mediator.midi.files.MidiFiles;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@PlayerContext
@Injection(lifeTime = LifeTime.TRANSIENT)
public class MidiFilesPickerGui extends PickerUI<PianoMidiFile>
{
    private final FluentMediator mediator;
    private final List<Material> icons;

    public MidiFilesPickerGui(FluentMediator mediator) {
        super("MidiFilesPickerGui");
        this.mediator = mediator;
        icons = new ArrayList<>();
        icons.add(Material.MUSIC_DISC_13);
        icons.add(Material.MUSIC_DISC_11);
        icons.add(Material.MUSIC_DISC_CAT);
        icons.add(Material.MUSIC_DISC_BLOCKS);
        icons.add(Material.MUSIC_DISC_MELLOHI);
        icons.add(Material.MUSIC_DISC_CHIRP);
        icons.add(Material.MUSIC_DISC_WARD);
        icons.add(Material.MUSIC_DISC_MALL);

    }





    @Override
    protected void onInitialize() {
        setListTitlePrimary("Select MIDI file");
        onListOpen(player ->
        {
            setContentButtons(getMidiFiles(),(data, button) ->
            {
                button.setTitlePrimary(data.getName());
                button.setMaterial(data.getIcon());
                button.setDataContext(data);
            });
            refreshContent();
        });
    }

    private List<PianoMidiFile> getMidiFiles()
    {
        var response = mediator.resolve(new MidiFiles.Request(), MidiFiles.Response.class);
        var files = response.files();
        var random = new Random();
        var materials = Material.values();
        var pianoMidiFiles = new ArrayList<PianoMidiFile>();
        for(var file : files)
        {
            var index = random.nextInt(0, materials.length-1);
            pianoMidiFiles.add(new PianoMidiFile(file.getPath(),file.getName(),materials[index],0,false));
        }
        return pianoMidiFiles;
    }
}
