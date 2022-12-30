package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox.CheckBox;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.Piano;
import jw.piano.api.observers.PianoObserver;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.gui.inventory_gui.EventsListenerInventoryUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.piano.spigot.PluginDocumentation;
import jw.piano.spigot.gui.bench.BenchViewGui;
import jw.piano.spigot.gui.midi.MidiPlayerGui;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@PlayerContext
@Injection
public class PianoViewGUI extends ChestUI {

    private final FluentTranslator lang;
    private final PianoViewButtonsFactory pianoViewButtons;
    private final BenchViewGui benchViewGui;
    private final MidiPlayerGui midiPlayerGui;
    private final Observer<PianoSkin> skinObserver;
    private PianoSkin pianoSkin;
    private final Observer<EffectInvoker> effectObserver;
    private EffectInvoker effectInvoker;

    private final Observer<PianoSound> soundObserver;
    private PianoSound pianoSound;


    @Getter
    private final List<CheckBox> checkBoxes;

    private PianoObserver pianoDataObserver;
    private Piano piano;

    @Inject
    public PianoViewGUI(BenchViewGui benchViewGui,
                        MidiPlayerGui midiPlayerGui,
                        PianoViewButtonsFactory buttons,
                        FluentTranslator lang) {
        super("Piano", 5);
        this.midiPlayerGui = midiPlayerGui;
        this.benchViewGui = benchViewGui;
        this.pianoViewButtons = buttons;
        this.lang = lang;

        skinObserver = new Observer<>(this, "pianoSkin");
        effectObserver = new Observer<>(this, "effectInvoker");
        soundObserver = new Observer<>(this, "pianoSound");
        checkBoxes = new ArrayList<>();
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoObserver();
        this.piano = piano;


        checkBoxes.clear();
        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.PIANO_ACTIVE.TITLE),
                pianoDataObserver.getActiveBind(),
                PluginPermissions.GUI.PIANO.SETTINGS.PIANO_ACTIVE));

        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.PEDAL_ACTIVE.TITLE),
                pianoDataObserver.getPedalsInteractionBind(),
                PluginPermissions.GUI.PIANO.SETTINGS.PEDAL_PRESSING_ACTIVE));

        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.DESKTOP_CLIENT_ACTIVE.TITLE),
                pianoDataObserver.getDesktopClientAllowedBind(),
                PluginPermissions.GUI.PIANO.SETTINGS.DESKTOP_APP_ACTIVE));

        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.DETECT_KEY_ACTIVE.TITLE),
                pianoDataObserver.getKeyboardInteraction(),
                PluginPermissions.GUI.PIANO.SETTINGS.KEYBOARD_PRESSING_ACTIVE));
        setTitlePrimary(pianoDataObserver.getPianoData().getName());
        open(player);
    }

    @Override
    protected void onOpen(Player player) {
        skinObserver.set(piano.getSkinManager().getCurrent());
        effectObserver.set(piano.getEffectManager().getCurrent());
        soundObserver.set(piano.getSoundsManager().getCurrent());
    }

    @Override
    protected void onInitialize() {
        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        benchViewGui.setParent(this);
        midiPlayerGui.setParent(this);

        pianoViewButtons.teleportButton()
                .setOnLeftClick(this::onTeleport)
                .build(this);

        pianoViewButtons.tokenButton()
                .setOnLeftClick(this::onTokenGeneration)
                .build(this);

        pianoViewButtons.renameButton()
                .setOnLeftClick(this::onRename)
                .build(this);

        pianoViewButtons.pianoClearButton()
                .setOnLeftClick(this::onPianoClear)
                .build(this);

        pianoViewButtons.benchButton()
                .setOnLeftClick((player, button) ->
                {
                    benchViewGui.open(player, piano);
                }).build(this);

        pianoViewButtons.midiPlayerButton()
                .setOnLeftClick((player, button) ->
                {
                    midiPlayerGui.open(player, piano);
                }).build(this);


        pianoViewButtons.pianoVolumeButton(() -> pianoDataObserver.getVolumeBind()).build(this);
        pianoViewButtons.pianoOptionsButton(this, this::getCheckBoxes).build(this);
        pianoViewButtons.pianoSkinSelectButton(
                        () -> pianoDataObserver.getSkinNameBind(),
                        () -> piano.getSkinManager().getItems(),
                        skinObserver)
                .build(this);

        pianoViewButtons.pianoParticleEffectSelectButton(
                        () -> pianoDataObserver.getEffectNameBind(),
                        () -> piano.getEffectManager().getItems(),
                        effectObserver)
                .build(this);

        pianoViewButtons.pianoSoundsSelectButton(
                        () -> pianoDataObserver.getEffectNameBind(),
                        () -> piano.getSoundsManager().getItems(),
                        soundObserver)
                .build(this);


        pianoViewButtons.backButton(this).build(this);
    }

    private void onRename(Player player, ButtonUI buttonUI) {
        this.close();
        FluentMessage.message()
                .color(org.bukkit.ChatColor.AQUA)
                .bold()
                .inBrackets(PluginTranslations.GENERAL.INFO)
                .space()
                .reset()
                .text(PluginTranslations.GUI.PIANO.RENAME.MESSAGE_1).send(player);
        EventsListenerInventoryUI.registerTextInput(player, s ->
        {
            pianoDataObserver.getPianoData().setName(s);
            setTitlePrimary(s);
            this.open(player);
        });
    }

    private void onTeleport(Player player, ButtonUI buttonUI) {
        piano.teleportPlayer(player);
    }

    private void onPianoClear(Player player, ButtonUI buttonUI) {
        piano.reset();
        FluentApi.messages()
                .chat()
                .info()
                .text(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_CLEAR)
                .send(player);
    }

    private void onTokenGeneration(Player player, ButtonUI buttonUI) {
        piano.getTokenGenerator().generateAndSend(player);
        close();
    }


}


