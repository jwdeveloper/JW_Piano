package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.plugin.implementation.FluentApi;
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
import jw.piano.spigot.gui.bench.BenchViewGui;
import jw.piano.spigot.gui.midi.MidiPlayerGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@PlayerContext
@Injection
public class PianoViewGUI extends ChestUI {
    private final PianoViewButtonsFactory pianoViewButtons;
    private final BenchViewGui benchViewGui;
    private final MidiPlayerGui midiPlayerGui;
    private final Observer<PianoSkin> skinObserver;
    private PianoSkin pianoSkin;
    private final Observer<EffectInvoker> effectObserver;
    private EffectInvoker effectInvoker;

    private final Observer<PianoSound> soundObserver;
    private PianoSound pianoSound;

    private PianoObserver pianoDataObserver;
    private Piano piano;

    @Inject
    public PianoViewGUI(BenchViewGui benchViewGui,
                        MidiPlayerGui midiPlayerGui,
                        PianoViewButtonsFactory buttons) {
        super("Piano", 5);
        this.midiPlayerGui = midiPlayerGui;
        this.benchViewGui = benchViewGui;
        this.pianoViewButtons = buttons;

        skinObserver = new Observer<>(this, "pianoSkin");
        effectObserver= new Observer<>(this, "effectInvoker");
        soundObserver = new Observer<>(this, "pianoSound");
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoObserver();
        this.piano = piano;
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

        pianoViewButtons.pianoEnableButton(() -> pianoDataObserver.getActiveBind()).build(this);
        pianoViewButtons.pianoDesktopClientEnableButton(() -> pianoDataObserver.getDesktopClientAllowedBind()).build(this);
        pianoViewButtons.pianoKeyboardEnableButton(() -> pianoDataObserver.getKeyboardInteraction()).build(this);
        pianoViewButtons.pianoPedalEnableButton(() -> pianoDataObserver.getPedalsInteractionBind()).build(this);
        pianoViewButtons.pianoVolumeButton(() -> pianoDataObserver.getVolumeBind()).build(this);

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
                .inBrackets("Piano info")
                .space().
                reset().
                text("Write new piano's name on the chat").send(player);
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
                .text("Piano has refreshed")
                .send(player);
    }

    private void onTokenGeneration(Player player, ButtonUI buttonUI) {
        piano.getTokenGenerator().generateAndSend(player);
        close();
    }


}


