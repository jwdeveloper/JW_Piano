package jw.piano.spigot.gui.midi;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.crud_list_ui.CrudListUI;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.midiplayer.midiparser.MidiParser;
import jw.piano.api.data.enums.MidiPlayingType;
import jw.piano.api.data.models.midi.PianoMidiFile;
import jw.piano.api.data.models.midi.MidiPlayerSettings;
import jw.piano.api.piano.Piano;
import jw.piano.core.mediator.midi.reader.MidiReader;
import jw.piano.core.workers.MidiPlayerWorker;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.File;


@PlayerContext
@Injection
public class MidiPlayerGui extends CrudListUI<PianoMidiFile> {
    private final FluentTranslator lang;
    private final FluentMediator mediator;

    private final FluentChestUI fluentChestUI;

    private final MidiFilesPickerGui midiFilePickerGui;
    private final ObserverBag<Boolean> playerInLoop = new ObserverBag<>(false);
    private final ObserverBag<Boolean> playMidi = new ObserverBag<>(false);

    private MidiPlayingType midiPlayingType = MidiPlayingType.IN_ORDER;
    private final Observer<MidiPlayingType> midiPlyingTypeObserver = new Observer<>(this, "midiPlayingType");

    private Piano piano;

    private MidiPlayerSettings midiSettings;

    @Inject
    public MidiPlayerGui(FluentTranslator lang,
                         FluentMediator mediator,
                         MidiFilesPickerGui midiFilePickerGui,
                         FluentChestUI fluentChestUI) {
        super("MidiPlayerGui", 4);
        this.lang = lang;
        this.midiFilePickerGui = midiFilePickerGui;
        this.mediator = mediator;
        this.fluentChestUI = fluentChestUI;
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        midiSettings = piano.getPianoObserver().getMidiPlayerSettingsObserver().getMidiPlayerSetting();
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

        fluentChestUI.buttonFactory()
                .observeBool(()-> playMidi.getObserver())
                .setDescription(options ->
                {
                    options.setTitle("Play Midi");
                })
                .setLocation(0, 1)
                .build(this);

        fluentChestUI.buttonFactory()
                .observeBool(()-> playerInLoop.getObserver())
                .setDescription(options ->
                {
                    options.setTitle("Play is loop");
                })
                .setLocation(0, 2)
                .build(this);

        fluentChestUI.buttonFactory()
                .observeBool(()-> playerInLoop.getObserver())
                .setDescription(options ->
                {
                    options.setTitle("Skip song");
                })
                .setLocation(0, 3)
                .build(this);


        fluentChestUI.buttonFactory()
                .observeEnum(()-> midiPlyingTypeObserver)
                .setDescription(options ->
                {
                    options.setTitle("Player type");
                })
                .setLocation(0, 4)
                .build(this);


        fluentChestUI.buttonBuilder()
                .setDescription(options ->
                {
                    options.setTitle("TEST");
                })
                .setMaterial(Material.ARROW)
                .setOnLeftClick((player, button) ->
                {
                    var file = new File("D:\\MC\\paper_1.19\\plugins\\JW_Piano\\midi\\Czardas.mid");
                    var reader = MidiParser.loadFile(file);
                    var plauer = new MidiPlayerWorker(reader, piano);
                    player.sendMessage("FILE LOADED STARTED PLAYINGs");
                    plauer.start();
                })
                .setLocation(0, 4)
                .build(this);

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