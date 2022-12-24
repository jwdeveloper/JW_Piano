package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentButtonUIBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermission;
import jw.piano.api.data.enums.PianoKeysConst;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.core.services.SkinLoaderService;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Supplier;

@PlayerContext
@Injection()
public class PianoViewButtonsFactory {
    private final FluentTranslator lang;
    private final SkinLoaderService pianoSkinService;
    private final FluentChestUI fluentUi;

    @Inject
    public PianoViewButtonsFactory(FluentTranslator translator,
                                   SkinLoaderService pianoSkinService,
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
                .setMaterial(Material.JUKEBOX)
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
                .setMaterial(Material.NAME_TAG)
                .setPermissions(PluginPermission.RENAME)
                .setLocation(2, 6);
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
                .setLocation(1, 6);
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

    public FluentButtonUIBuilder pianoParticleEffectSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<EffectInvoker>> effectSupplier,
            Observer<EffectInvoker> effectIndexSupplier) {

        return fluentUi.buttonFactory()
                .observeList(() -> effectIndexSupplier,
                        effectSupplier,
                        options ->
                        {
                            options.setOnNameMapping(EffectInvoker::getName);
                            options.setOnSelectionChanged(event ->
                            {
                                final var value = event.data().getName();
                                final var observer = observerSupplier.get();
                                observer.set(value);
                                event.buttonUI().setMaterial(event.data().getIcon());
                            });
                        }
                )
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PluginPermission.EFFECTS)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.effect.title"));
                })
                .setLocation(3, 2);
    }

    public FluentButtonUIBuilder pianoSkinSelectButton(Supplier<Observer<String>> observerSupplier,
                                                       Supplier<List<PianoSkin>> skinsSupplier,
                                                       Observer<PianoSkin> skinObserver) {
        return fluentUi.buttonFactory()
                .observeList(() -> skinObserver, skinsSupplier, options ->
                {
                    options.setOnNameMapping(PianoSkin::getName);
                    options.setOnSelectionChanged(event ->
                    {
                        final var value = event.data().getName();
                        final var observer = observerSupplier.get();
                        observer.set(value);
                        if (value.equals("none")) {
                            event.buttonUI().setMaterial(Material.NOTE_BLOCK);
                            return;
                        }
                        event.buttonUI().setCustomMaterial(PluginConsts.MATERIAL, event.data().getCustomModelId());
                    });
                })
                .setDescription(config ->
                {
                    config.setTitle(lang.get("gui.piano.skin.title"));
                })
                .setPermissions(PluginPermission.SKIN)
                .setLocation(3, 4);
    }



    public FluentButtonUIBuilder pianoSoundsSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<PianoSound>> effectSupplier,
            Observer<PianoSound> effectIndexSupplier) {

        return fluentUi.buttonFactory()
                .observeList(() -> effectIndexSupplier,
                        effectSupplier,
                        options ->
                        {
                            options.setOnNameMapping(PianoSound::getName);
                            options.setOnSelectionChanged(event ->
                            {
                                final var value = event.data().getName();
                                final var observer = observerSupplier.get();
                                observer.set(value);
                            });
                        }
                )
                .setMaterial(Material.MUSIC_DISC_WARD)
           //     .setPermissions(PluginPermission.EFFECTS)
                .setDescription(options ->
                {
                    options.setTitle("Sounds");
                })
                .setLocation(3, 6);
    }



}
