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

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.spigot.permissions.implementation.PermissionsUtility;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.midi.PianoMidiFile;
import jw.piano.api.observers.MidiPlayerSettingsObserver;
import jw.piano.api.piano.MidiPlayer;
import jw.piano.api.piano.Piano;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


@PlayerContext
@Injection
public class MidiPlayerGui extends ChestUI {
    private final FluentChestUI fluentChestUI;
    private final MidiFilesPickerGui midiFilePickerGui;
    private List<ButtonUI> midiSongSlots;
    private Piano piano;
    private MidiPlayer midiPlayer;
    private MidiPlayerSettingsObserver observer;
    private FluentTranslator lang;

    @Inject
    public MidiPlayerGui(MidiFilesPickerGui midiFilePickerGui,
                         FluentTranslator translator,
                         FluentChestUI fluentChestUI) {
        super("MidiPlayerGui", 5);
        this.midiFilePickerGui = midiFilePickerGui;
        this.fluentChestUI = fluentChestUI;
        this.lang = translator;
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        this.midiPlayer = piano.getMidiPlayer();
        this.observer = midiPlayer.getObserver();
        open(player);
    }


    @Override
    protected void onInitialize() {
        setTitlePrimary(lang.get(PluginTranslations.GUI.MIDI_PLAYER.TITLE));
        drawBorder();
        midiSongSlots = createMidiSongSlots();
        midiFilePickerGui.setParent(this);

        fluentChestUI.buttonFactory()
                .observeBool(() -> observer.getIsPlaying(), options ->
                {
                    options.setEnabled(lang.get(PluginTranslations.GUI.MIDI_PLAYER.STATE.PLAY));
                    options.setDisabled(lang.get(PluginTranslations.GUI.MIDI_PLAYER.STATE.STOP));
                })
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.MIDI_PLAYER.STATE.TITLE));
                    options.setOnLeftClick(lang.get(PluginTranslations.GUI.MIDI_PLAYER.STATE.CLICK.LEFT));
                })
                .setLocation(4, 4)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.PLAY_STOP)
                .build(this);

        fluentChestUI.buttonBuilder()
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.MIDI_PLAYER.PREVIOUS.TITLE));
                })
                .setMaterial(Material.ARROW)
                .setOnLeftClick((player, button) ->
                {
                    midiPlayer.previous();
                    open(player, piano);
                })
                .setLocation(4, 3)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.PREVIOUS_SONG)
                .build(this);

        fluentChestUI.buttonBuilder()
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.MIDI_PLAYER.NEXT.TITLE));
                })
                .setLocation(4, 5)
                .setOnLeftClick((player, button) ->
                {
                    midiPlayer.next();
                    open(player, piano);
                })
                .setMaterial(Material.ARROW)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.NEXT_SONG)
                .build(this);


        fluentChestUI.buttonFactory()
                .observeEnum(() -> observer.getPlayingType())
                .setDescription(options ->
                {
                    var description = new MessageBuilder();
                    description.text(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.RANDOM.TITLE))
                            .space().text(Emoticons.arrowRight).space()
                            .text(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.RANDOM.DESC)).newLine().newLine()

                            .newLine().text(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.IN_ORDER.TITLE))
                            .space().text(Emoticons.arrowRight).space()
                            .text(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.IN_ORDER.DESC)).newLine().newLine()

                            .newLine().text(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.LOOP.TITLE))
                            .space().text(Emoticons.arrowRight).space()
                            .text(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.LOOP.DESC)).newLine().newLine();

                    options.addDescriptionLine(description.toArray());
                    options.setTitle(lang.get(PluginTranslations.GUI.MIDI_PLAYER.MODE.TITLE));
                })
                .setMaterial(Material.REPEATER)
                .setLocation(0, 1)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.PLAYER_TYPE)
                .build(this);

        fluentChestUI.buttonFactory()
                .observeBarInt(() -> observer.getSpeed(), options ->
                {
                    options.setMinimum(1);
                    options.setYield(5);
                    options.setMaximum(300);
                })
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.MIDI_PLAYER.SPEED.TITLE));
                })
                .setMaterial(Material.DIAMOND_HORSE_ARMOR)
                .setLocation(0, 2)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.SPEED)
                .build(this);

        fluentChestUI.buttonFactory()
                .back(this, this.getParent())
                .build(this);
    }

    @Override
    protected void onOpen(Player player) {
        var midiFiles = midiPlayer.getSongs();
        for (var button : midiSongSlots) {
            var optional = midiFiles.stream().filter(c -> c.getIndex() == button.getWidth()).findFirst();
            if (optional.isEmpty()) {
                button.setMaterial(Material.GRAY_STAINED_GLASS_PANE);
                button.setColor(Color.WHITE);
                button.updateDescription(3, " ");
                continue;
            }
            var song = optional.get();

            button.setCustomMaterial(PluginConsts.MATERIAL, 450);
            button.setDataContext(song);
            button.setColor(Color.WHITE);
            button.updateDescription(3, FluentApi.messages().chat().space().text(song.getName(), ChatColor.WHITE).toString());
            if (song.equals(midiPlayer.getCurrentSong())) {
                button.setColor(Color.GREEN);
            }
        }
    }

    private List<ButtonUI> createMidiSongSlots() {
        var result = new ArrayList<ButtonUI>();
        for (var i = 1; i < InventoryUI.INVENTORY_WIDTH - 1; i++) {
            var btn = fluentChestUI.buttonBuilder()
                    .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
                    .setDescription(descriptionInfoBuilder ->
                    {
                        descriptionInfoBuilder.addDescriptionLine(" ");
                        descriptionInfoBuilder.addDescriptionLine(" ");
                        descriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.MIDI_PLAYER.SONG.TITLE));
                        descriptionInfoBuilder.setOnLeftClick(lang.get(PluginTranslations.GUI.MIDI_PLAYER.SONG.CLICK.LEFT));
                        descriptionInfoBuilder.setOnRightClick(lang.get(PluginTranslations.GUI.MIDI_PLAYER.SONG.CLICK.RIGHT));
                        descriptionInfoBuilder.setOnShiftClick(lang.get(PluginTranslations.GUI.MIDI_PLAYER.SONG.CLICK.SHIFT));
                    })
                    .setOnLeftClick(this::onInsertMidi)
                    .setOnRightClick(this::onRemoveMidi)
                    .setOnShiftClick(this::onSelectCurrent)
                    .setLocation(2, i)
                    .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.SELECT_SONG)
                    .build(this);
            result.add(btn);
        }
        return result;
    }

    private void drawBorder() {
        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        for (var i = 0; i < InventoryUI.INVENTORY_WIDTH; i++) {
            ButtonUI.factory().backgroundButton(1, i, Material.LIGHT_BLUE_STAINED_GLASS_PANE).buildAndAdd(this);
            ButtonUI.factory().backgroundButton(3, i, Material.LIGHT_BLUE_STAINED_GLASS_PANE).buildAndAdd(this);
        }
    }

    private void onSelectCurrent(Player player, ButtonUI buttonUI) {
        var content = buttonUI.<PianoMidiFile>getDataContext();
        if (content == null) {
            return;
        }
        midiPlayer.setCurrentSong(content);
        open(player, piano);
    }


    private void onInsertMidi(Player player, ButtonUI buttonUI) {
        midiFilePickerGui.onContentPicked((player1, button) ->
        {
            var content = button.<PianoMidiFile>getDataContext();
            content.setIndex(buttonUI.getWidth());
            midiPlayer.addSong(content);
            open(player, piano);
        });
        midiFilePickerGui.open(player);
    }


    private void onRemoveMidi(Player player, ButtonUI buttonUI) {

        if(!PermissionsUtility.hasOnePermission(player, PluginPermissions.GUI.MIDI_PLAYER.REMOVE_SONG))
        {
            return;
        }
        var data = buttonUI.<PianoMidiFile>getDataContext();
        midiPlayer.removeSong(data);
        buttonUI.setHighlighted(false);
        open(player, piano);
    }
}
