package jw.piano.spigot.gui.midi;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverUI;
import jw.fluent.api.spigot.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.enums.MidiPlayingType;
import jw.piano.data.midi.MidiFile;
import jw.piano.data.models.PianoMidiFile;
import jw.piano.data.models.PianoMidiSettings;
import jw.piano.mediator.midi.reader.MidiReader;
import jw.piano.spigot.gameobjects.Piano;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Random;

@PlayerContext
@Injection(lifeTime = LifeTime.TRANSIENT)
public class MidiPlayerGui extends CrudListUI<PianoMidiFile> {
    private final FluentTranslator lang;
    private final FluentMediator mediator;

    private final MidiFilesPickerGui midiFilePickerGui;
    private final ObserverBag<Boolean> playerInLoop = new ObserverBag<>(false);
    private final ObserverBag<Boolean> playMidi = new ObserverBag<>(false);

    private MidiPlayingType midiPlayingType = MidiPlayingType.IN_ORDER;
    private final Observer<MidiPlayingType> midiPlyingTypeObserver = new Observer<>(this, "midiPlayingType");

    private Piano piano;

    private PianoMidiSettings midiSettings;

    @Inject
    public MidiPlayerGui(FluentTranslator lang,
                         FluentMediator mediator,
                         MidiFilesPickerGui midiFilePickerGui) {
        super("MidiPlayerGui", 4);
        this.lang = lang;
        this.midiFilePickerGui = midiFilePickerGui;
        this.mediator = mediator;
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        midiSettings = piano.getPianoData().getPianoMidiSettings();
        open(player);
    }


    @Override
    protected void onInitialize() {
        setListTitlePrimary("MIDI player");
        midiFilePickerGui.setParent(this);
        midiFilePickerGui.onContentPicked(this::onFileMidiSelected);
        onListOpen(player ->
        {
            setContentButtons(midiSettings.getMidiFilesSortedByIndex(), (data, button) ->
            {
                button.setDescription("Left Click: Move left in playing queue",
                        "Right Click: Move right in playing queue");
                button.setDataContext(data);
                button.setMaterial(data.getIcon());
                button.setTitlePrimary(data.getName());
                button.setOnRightClick((player1, button1) ->
                {
                    var data2 = button.<PianoMidiFile>getDataContext();
                    midiSettings.moveRight(data2);
                    open(player,piano);
                });
                button.setOnClick((player1, button1) ->
                {
                    var data2 = button.<PianoMidiFile>getDataContext();
                    midiSettings.moveLeft(data2);
                    open(player,piano);
                });
            });
            refreshContent();
        });

        ButtonObserverUI.factory()
                .boolObserver(playMidi.getObserver())
                .setTitlePrimary("Play Midi")
                .setLocation(0, 1)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(playerInLoop.getObserver())
                .setTitlePrimary("Play is loop")
                .setLocation(0, 2)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(playerInLoop.getObserver())
                .setTitlePrimary("Skip song")
                .setLocation(0, 3)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .enumSelectorObserver(midiPlyingTypeObserver)
                .setTitlePrimary("Player type")
                .setLocation(0, 4)
                .buildAndAdd(this);


        hideEditButton();
        getButtonInsert().setTitlePrimary("Add MIDI File");
        onInsert((player, button) ->
        {
            midiFilePickerGui.open(player);
        });

        getButtonDelete().setTitlePrimary("Remove MIDI File");
        onDelete((player, button) ->
        {
            var data = button.<PianoMidiFile>getDataContext();
            var midiFile = midiSettings.getMidiFiles().stream().filter(c -> c.getPath().equals(data.getPath())).findFirst();
            if (midiFile.isEmpty()) {
                return;
            }
            midiSettings.getMidiFiles().remove(midiFile.get());
            open(player,piano);
        });


    }

    private void onFileMidiSelected(Player player, ButtonUI buttonUI)
    {
        var content = buttonUI.<PianoMidiFile>getDataContext();
        var request = new MidiReader.Request(content.getPath());
        var response = mediator.resolve(request, MidiReader.Response.class);
        if (!response.created()) {
            this.setTitlePrimary("Error: file not loaded");
        } else {
            content.setIndex(midiSettings.getMidiFiles().size());
            midiSettings.addMidiFile(content);
        }
        this.open(player, piano);
    }
}
