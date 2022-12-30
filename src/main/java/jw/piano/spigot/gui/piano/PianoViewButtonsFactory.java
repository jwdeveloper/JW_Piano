package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentButtonUIBuilder;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox.CheckBox;
import jw.fluent.api.spigot.gui.inventory_gui.InventoryUI;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.enums.PianoKeysConst;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.api.data.sounds.PianoSound;
import jw.piano.api.managers.effects.EffectInvoker;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Supplier;

@PlayerContext
@Injection()
public class PianoViewButtonsFactory {
    private final FluentTranslator lang;
    private final FluentChestUI fluentUi;

    @Inject
    public PianoViewButtonsFactory(FluentTranslator translator,
                                   FluentChestUI buttonUIBuilder) {
        this.lang = translator;
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
                    buttonStyleBuilder.setTitle(PluginTranslations.GUI.PIANO.MIDI_PLAYER.TITLE);
                    buttonStyleBuilder.addDescriptionLine(PluginTranslations.GUI.PIANO.MIDI_PLAYER.DESC);
                })
                .setMaterial(Material.JUKEBOX)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.BASE)
                .setLocation(1, 2);
    }

    public FluentButtonUIBuilder pianoClearButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(PluginTranslations.GUI.PIANO.CLEAR.TITLE);
                    buttonStyleBuilder.addDescriptionLine(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_1);
                    buttonStyleBuilder.addDescriptionLine(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_2);
                    buttonStyleBuilder.addDescriptionLine(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_3);
                })
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermissions.GUI.PIANO.CLEAR)
                .setLocation(0, 8);
    }


    public FluentButtonUIBuilder benchButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.setTitle(PluginTranslations.GUI.PIANO.BENCH.TITLE);
                })
                .setMaterial(PluginConsts.MATERIAL, PianoKeysConst.BENCH.getId())
                .setPermissions(PluginPermissions.GUI.BENCH.BASE)
                .setLocation(2, 4);
    }

    public FluentButtonUIBuilder renameButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.RENAME.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.RENAME.DESC));
                })
                .setMaterial(Material.NAME_TAG)
                .setPermissions(PluginPermissions.GUI.PIANO.RENAME)
                .setLocation(2, 6);
    }

    public FluentButtonUIBuilder teleportButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.TELEPORT.TITLE));
                })
                .setMaterial(Material.ENDER_PEARL)
                .setPermissions(PluginPermissions.GUI.PIANO.TELEPORT)
                .setLocation(2, 2);
    }

    public FluentButtonUIBuilder tokenButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.TOKEN.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.TOKEN.DESC));
                })
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setPermissions(PluginPermissions.GUI.PIANO.GENERATE_TOKEN)
                .setLocation(1, 4);
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
                .setPermissions(PluginPermissions.GUI.PIANO.VOLUME)
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO.VOLUME.TITLE));
                })
                .setLocation(1, 6);
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
                .setPermissions(PluginPermissions.GUI.PIANO.EFFECT)
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO.EFFECT.TITLE));
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
                    config.setTitle(lang.get(PluginTranslations.GUI.PIANO.SKIN.TITLE));
                })
                .setPermissions(PluginPermissions.GUI.PIANO.SKIN)
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
                .setPermissions(PluginPermissions.GUI.PIANO.SOUND)
                .setDescription(options ->
                {
                    options.setTitle(PluginTranslations.GUI.PIANO.SOUNDS.TITLE);
                })
                .setLocation(3, 6);
    }

    public FluentButtonUIBuilder pianoOptionsButton(InventoryUI inventoryUI, Supplier<List<CheckBox>> values) {
        return fluentUi.buttonFactory()
                .observeCheckBoxList(inventoryUI, values, checkBoxListNotifierOptions ->
                {

                })
                .setPermissions(PluginPermissions.GUI.PIANO.SETTINGS.BASE)
                .setMaterial(Material.DIAMOND_PICKAXE)
                .setLocation(0, 1)
                .setDescription(descriptionInfoBuilder ->
                {
                    descriptionInfoBuilder.setTitle(PluginTranslations.GUI.PIANO.PIANO_OPTIONS.TITLE);
                });
    }
}
