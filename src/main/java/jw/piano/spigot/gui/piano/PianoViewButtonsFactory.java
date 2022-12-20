package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentButtonUIBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PluginConsts;
import jw.piano.data.PluginPermission;
import jw.piano.data.enums.PianoEffect;
import jw.piano.data.enums.PianoKeysConst;
import jw.piano.data.models.PianoSkin;
import jw.piano.services.PianoSkinService;
import org.bukkit.Material;

import java.util.function.Supplier;

@PlayerContext
@Injection()
public class PianoViewButtonsFactory {
    private final FluentTranslator lang;
    private final PianoSkinService pianoSkinService;
    private final FluentChestUI fluentUi;
    @Inject
    public PianoViewButtonsFactory(FluentTranslator translator,
                                   PianoSkinService pianoSkinService,
                                   FluentChestUI buttonUIBuilder) {
        this.lang = translator;
        this.pianoSkinService = pianoSkinService;
        this.fluentUi = buttonUIBuilder;
    }

    public FluentButtonUIBuilder backButton(InventoryUI pianoUI) {
        return fluentUi.buttonFactory()
                .back(pianoUI, null);
    }

    public FluentButtonUIBuilder midiPlayerButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle("Midi player");
                    buttonStyleBuilder.addDescriptionLine("Play Midi files on this piano. Files should be located in plugins/JW_Piano/midi");
                })
                .setMaterial(Material.MUSIC_DISC_13)
                .setLocation(1, 2);
    }

    public FluentButtonUIBuilder pianoClearButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle("Clear");
                    buttonStyleBuilder.addDescriptionLine("Might be helpful when server has been suddenly shut down");
                    buttonStyleBuilder.addDescriptionLine("and old piano model has not been properly removed.");
                    buttonStyleBuilder.addDescriptionLine("Try also use /reload to clear all pianos");
                })
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setLocation(0, 8);
    }


    public FluentButtonUIBuilder benchButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.setTitle("Bench");
                })
                .setMaterial(PluginConsts.MATERIAL, PianoKeysConst.BENCH.getId())
                .setLocation(2, 4);
    }

    public FluentButtonUIBuilder renameButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get("gui.piano.rename.title"));
                    buttonStyleBuilder.addDescriptionLine(lang.get("gui.piano.rename.desc"));
                })
                .setMaterial(Material.PAPER)
                .setPermissions(PluginPermission.RENAME)
                .setLocation(3, 6);
    }

    public FluentButtonUIBuilder teleportButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get("gui.piano.teleport.title"));
                })
                .setMaterial(Material.ENDER_PEARL)
                .setPermissions(PluginPermission.TELEPORT)
                .setLocation(2, 2);
    }

    public FluentButtonUIBuilder tokenButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get("gui.piano.token.title"));
                    buttonStyleBuilder.addDescriptionLine(lang.get("gui.piano.token.desc"));
                })
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setLocation(1, 4);
    }

    public FluentButtonUIBuilder pianoEnableButton(Supplier<Observer<Boolean>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeBool(observerSupplier)
                .setPermissions(PluginPermission.ACTIVE)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.piano-active.title"));
                })
                .setLocation(0, 1);
    }

    public FluentButtonUIBuilder pianoPedalEnableButton(Supplier<Observer<Boolean>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeBool(observerSupplier)
                .setPermissions(PluginPermission.PEDAl)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.pedal-active.title"));
                })
                .setLocation(0, 2);
    }

    public FluentButtonUIBuilder pianoKeyboardEnableButton(Supplier<Observer<Boolean>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeBool(observerSupplier)
                .setPermissions(PluginPermission.DETECT_KEY)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.detect-key-active.title"));
                })
                .setLocation(0, 3);
    }

    public FluentButtonUIBuilder pianoDesktopClientEnableButton(Supplier<Observer<Boolean>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeBool(observerSupplier)
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.desktop-client-active.title"));
                })
                .setLocation(0, 4);
    }

    public FluentButtonUIBuilder pianoSkinSelectButton(Supplier<Observer<Integer>> observerSupplier, Observer<Integer> skinIndexObserver) {
        return fluentUi.buttonFactory()
                .observeList(() -> skinIndexObserver, pianoSkinService.skins(), options ->
                {
                    options.setOnNameMapping(PianoSkin::getName);
                    options.setOnSelectionChanged(event ->
                    {
                        final var value = event.data().getCustomModelId();
                        final var skinIdObserver = observerSupplier.get();
                        skinIdObserver.set(value);
                        if (value == 0) {
                            event.buttonUI().setMaterial(Material.NOTE_BLOCK);
                            return;
                        }
                        event.buttonUI().setCustomMaterial(PluginConsts.MATERIAL, value);
                    });
                })
                .setDescription(config ->
                {
                    config.setTitle(lang.get("gui.piano.skin.title"));
                })
                .setPermissions(PluginPermission.SKIN)
                .setLocation(3, 4);
    }

    public FluentButtonUIBuilder pianoParticleEffectSelectButton(Supplier<Observer<PianoEffect>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeEnum(observerSupplier)
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PluginPermission.EFFECTS)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.effect.title"));
                })
                .setLocation(3, 2);
    }


    public FluentButtonUIBuilder pianoVolumeButton(Supplier<Observer<Integer>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeInt(observerSupplier, options ->
                {
                    options.setYield(5);
                    options.setMaximum(100);
                    options.setMinimum(0);
                })
                .setMaterial(Material.BELL)
                .setPermissions(PluginPermission.VOLUME)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.volume.title"));
                })
                .setLocation(2, 6);
    }


}
