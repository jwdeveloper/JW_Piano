package io.github.jwdeveloper.spigot.piano.gui.piano;

import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import org.bukkit.Color;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Supplier;

public class PianoViewUIButtons
{
    private final InventoryDecorator decorator;
    private final  InventoryApi inventoryApi;


    public PianoViewUIButtons(InventoryDecorator decorator,
                                   InventoryApi inventoryApi) {
        this.decorator = decorator;
        this.inventoryApi = inventoryApi;
    }

    @Override
    public ButtonBuilder backButton(InventoryUI pianoUI) {
        return fluentUi.buttonFactory()
                .back(pianoUI, null);
    }

    @Override
    public ButtonBuilder keyboardButton() {

        return decorator
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.KEYBOARD.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.KEYBOARD.DESC));
                })
                .setMaterial(PluginConsts.MATERIAL, PluginModels.PIANO_KEY.id(), Color.WHITE)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.BASE)
                .setLocation(2, 6);
    }

    @Override
    public ButtonBuilder midiPlayerButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.MIDI_PLAYER.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.MIDI_PLAYER.DESC));
                })
                .setMaterial(Material.JUKEBOX)
                .setPermissions(PluginPermissions.GUI.MIDI_PLAYER.BASE)
                .setLocation(1, 2);
    }

    @Override
    public ButtonBuilder pianoClearButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.CLEAR.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_1));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_2));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.CLEAR.MESSAGE_3));
                })
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermissions.GUI.PIANO.CLEAR)
                .setLocation(0, 8);
    }


    @Override
    public ButtonBuilder benchButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.BENCH.TITLE));
                    buttonDescriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.BENCH.TITLE));
                })
                .setMaterial(PluginConsts.MATERIAL, PluginModels.BENCH.id())
                .setPermissions(PluginPermissions.GUI.BENCH.BASE)
                .setLocation(2, 4);
    }

    @Override
    public ButtonBuilder renameButton() {
        return fluentUi
                .buttonBuilder()
                .setDescription(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.RENAME.TITLE));
                    buttonStyleBuilder.addDescriptionLine(lang.get(PluginTranslations.GUI.PIANO.RENAME.DESC));
                })
                .setMaterial(Material.NAME_TAG)
                .setPermissions(PluginPermissions.GUI.PIANO.RENAME)
                .setLocation(0, 2);
    }

    @Override
    public ButtonBuilder teleportButton() {
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

    @Override
    public ButtonBuilder tokenButton() {
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

    @Override
    public ButtonBuilder pianoVolumeButton(Supplier<Observer<Integer>> observerSupplier) {
        return fluentUi.buttonFactory()
                .observeBarInt(observerSupplier, options ->
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


    @Override
    public ButtonBuilder pianoParticleEffectSelectButton(
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


    public ButtonBuilder pianoSkinSelectButton(Supplier<Observer<String>> observerSupplier,
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
                    config.setOnShiftClick(lang.get(FluentTranslations.COLOR_PICKER.CHANGE_COLOR));
                })
                .setPermissions(PluginPermissions.GUI.PIANO.SKIN)
                .setLocation(3, 4);
    }


    @Override
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
                    options.setTitle(lang.get(PluginTranslations.GUI.PIANO.SOUNDS.TITLE));
                })
                .setLocation(3, 6);
    }

    @Override
    public FluentButtonUIBuilder pianoOptionsButton(InventoryUI inventoryUI, Supplier<List<CheckBox>> values) {
        return fluentUi.buttonFactory()
                .observeCheckBoxList(inventoryUI, values, checkBoxListNotifierOptions ->
                {

                })
                .setPermissions(PluginPermissions.GUI.PIANO.SETTINGS.BASE)
                .setMaterial(Material.REPEATER)
                .setLocation(0, 1)
                .setDescription(descriptionInfoBuilder ->
                {
                    descriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.PIANO.PIANO_OPTIONS.TITLE));
                });
    }
}
