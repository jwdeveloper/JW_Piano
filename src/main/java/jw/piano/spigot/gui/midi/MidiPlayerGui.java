package jw.piano.spigot.gui.midi;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.spigot.messages.message.MessageBuilder;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.midi.PianoMidiFile;
import jw.piano.api.observers.MidiPlayerSettingsObserver;
import jw.piano.api.piano.MidiPlayer;
import jw.piano.api.piano.Piano;
import org.bukkit.ChatColor;
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

    @Inject
    public MidiPlayerGui(MidiFilesPickerGui midiFilePickerGui,
                         FluentChestUI fluentChestUI) {
        super("MidiPlayerGui", 5);
        this.midiFilePickerGui = midiFilePickerGui;
        this.fluentChestUI = fluentChestUI;
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        this.midiPlayer = piano.getMidiPlayer();
        this.observer = midiPlayer.getObserver();
        open(player);
    }


    @Override
    protected void onInitialize() {
        setTitlePrimary(PluginTranslations.GUI.MIDI_PLAYER.TITLE);
        drawBorder();
        midiSongSlots = createMidiSongSlots();
        midiFilePickerGui.setParent(this);

        fluentChestUI.buttonFactory()
                .observeBool(() -> observer.getIsPlayingObserver(), options ->
                {
                    options.setEnabled(PluginTranslations.GUI.MIDI_PLAYER.STATE.PLAY);
                    options.setDisabled(PluginTranslations.GUI.MIDI_PLAYER.STATE.STOP);
                })
                .setDescription(options ->
                {
                    options.setTitle(PluginTranslations.GUI.MIDI_PLAYER.STATE.TITLE);
                })
                .setLocation(4, 4)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.PLAY_STOP)
                .build(this);

        fluentChestUI.buttonBuilder()
                .setDescription(options ->
                {
                    options.setTitle(PluginTranslations.GUI.MIDI_PLAYER.PREVIOUS.TITLE);
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
                    options.setTitle(PluginTranslations.GUI.MIDI_PLAYER.NEXT.TITLE);
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
                .observeEnum(() -> observer.getPlayingTypeObserver())
                .setDescription(options ->
                {
                    var description = new MessageBuilder();
                    description.text(PluginTranslations.GUI.MIDI_PLAYER.MODE.RANDOM.TITLE).text(Emoticons.arrowRight)
                            .text(PluginTranslations.GUI.MIDI_PLAYER.MODE.RANDOM.DESC).newLine().newLine()

                            .newLine().text(PluginTranslations.GUI.MIDI_PLAYER.MODE.IN_ORDER.TITLE).text(Emoticons.arrowRight)
                            .text(PluginTranslations.GUI.MIDI_PLAYER.MODE.IN_ORDER.DESC).newLine().newLine()

                            .newLine().text(PluginTranslations.GUI.MIDI_PLAYER.MODE.LOOP.TITLE).text(Emoticons.arrowRight)
                            .text(PluginTranslations.GUI.MIDI_PLAYER.MODE.LOOP.DESC).newLine().newLine();

                    options.addDescriptionLine(description.toArray());
                    options.setTitle(PluginTranslations.GUI.MIDI_PLAYER.MODE.TITLE);
                })
                .setMaterial(Material.DIAMOND)
                .setLocation(0, 1)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.PLAYER_TYPE)
                .build(this);

        fluentChestUI.buttonFactory()
                .observeInt(() -> observer.getSpeedObserver(), options ->
                {
                    options.setMinimum(10);
                    options.setYield(5);
                    options.setMaximum(200);
                })
                .setDescription(options ->
                {
                    options.setTitle(PluginTranslations.GUI.MIDI_PLAYER.SPEED.TITLE);
                    options.addDescriptionLine(PluginTranslations.GUI.MIDI_PLAYER.SPEED.DESC);
                })
                .setMaterial(Material.REPEATER)
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
                button.updateDescription(3, " ");
                continue;
            }
            var song = optional.get();

            button.setMaterial(song.getIcon());
            button.setDataContext(song);
            button.setHighlighted(false);
            button.updateDescription(3, FluentApi.messages().chat().text(" Name ", ChatColor.AQUA).text(Emoticons.arrowRight).space().text(song.getName(), ChatColor.WHITE).toString());
            if (song.equals(midiPlayer.getCurrentSong())) {
                button.setHighlighted(true);
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
                        descriptionInfoBuilder.setTitle(PluginTranslations.GUI.MIDI_PLAYER.SONG.TITLE);
                        descriptionInfoBuilder.setOnLeftClick(PluginTranslations.GUI.MIDI_PLAYER.SONG.CLICK.LEFT);
                        descriptionInfoBuilder.setOnRightClick(PluginTranslations.GUI.MIDI_PLAYER.SONG.CLICK.RIGHT);
                        descriptionInfoBuilder.setOnShiftClick(PluginTranslations.GUI.MIDI_PLAYER.SONG.CLICK.SHIFT);
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
        var data = buttonUI.<PianoMidiFile>getDataContext();
        midiPlayer.removeSong(data);
        buttonUI.setHighlighted(false);
        open(player, piano);
    }
}
