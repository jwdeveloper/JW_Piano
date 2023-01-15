/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot.gui.midi;


import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.picker_list_ui.PickerUI;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.models.midi.PianoMidiFile;
import jw.piano.core.mediator.midi.files.MidiFiles;
import org.bukkit.Color;
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
                button.setCustomMaterial(PluginConsts.MATERIAL, 450);
                button.setDataContext(data);
                button.setColor(Color.WHITE);
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
            pianoMidiFiles.add(new PianoMidiFile(file.getPath(),file.getName(),Material.MUSIC_DISC_CAT,0,false));
        }
        return pianoMidiFiles;
    }
}
