package io.github.jwdeveloper.spigot.piano.gui.piano;

import io.github.jwdeveloper.ff.core.observer.implementation.Observer;
import io.github.jwdeveloper.ff.extension.gui.OLD.observers.list.checkbox.CheckBox;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.api.buttons.ButtonBuilder;
import io.github.jwdeveloper.ff.extension.gui.prefab.widgets.implementation.list.ContentListWidget;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginConsts;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginModels;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginPermissions;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginTranslations;
import io.github.jwdeveloper.spigot.piano.api.data.PianoSkinData;
import io.github.jwdeveloper.spigot.piano.api.data.PianoSoundData;
import io.github.jwdeveloper.spigot.piano.api.managers.effects.EffectInvoker;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;


public class PianoViewUIButtons {

    private final PianoViewUI pianoViewUI;
    private final InventoryDecorator decorator;
    private final InventoryApi api;

    public PianoViewUIButtons(PianoViewUI pianoViewUI,
                              InventoryDecorator decorator,
                              InventoryApi inventoryApi) {
        this.decorator = decorator;
        this.api = inventoryApi;
        this.pianoViewUI = pianoViewUI;
    }


    /*
    public ButtonBuilder backButton(InventoryUI pianoUI) {
        return pianoViewUI.button()
                .back(pianoUI, null);
    }*/


    public ButtonBuilder keyboardButton() {

        return pianoViewUI.button()
                .withStyleRenderer(style ->
                {
                    style.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.KEYBOARD.TITLE));
                    style.withDescription(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.KEYBOARD.DESC));
                })
                .withMaterial(PianoPluginConsts.MATERIAL, Color.WHITE, PianoPluginModels.PIANO_KEY.id())
                .withPosition(2, 6)
                .withPermissions(PianoPluginPermissions.GUI.MIDI_PLAYER.BASE);
    }


    public ButtonBuilder midiPlayerButton() {
        return pianoViewUI.button()
                .withStyleRenderer(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.MIDI_PLAYER.TITLE));
                    buttonStyleBuilder.withDescription(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.MIDI_PLAYER.DESC));
                })
                .withMaterial(Material.JUKEBOX)
                .withPosition(1, 2)
                .withPermissions(PianoPluginPermissions.GUI.MIDI_PLAYER.BASE);
    }


    public ButtonBuilder pianoClearButton() {
        return pianoViewUI.button()
                .withStyleRenderer(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.CLEAR.TITLE));
                    buttonStyleBuilder.withUseCache();
                    buttonStyleBuilder.withDescriptionLine(e ->
                            e.builder()
                                    .textNewLine(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.CLEAR.MESSAGE_1))
                                    .textNewLine(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.CLEAR.MESSAGE_2))
                                    .textNewLine(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.CLEAR.MESSAGE_3))
                                    .toString());
                })
                .withMaterial(Material.TOTEM_OF_UNDYING)
                .withPermissions(PianoPluginPermissions.GUI.PIANO.CLEAR)
                .withPosition(0, 8);
    }


    public ButtonBuilder benchButton() {
        return pianoViewUI.button()
                .withStyleRenderer(buttonDescriptionInfoBuilder ->
                {
                    buttonDescriptionInfoBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.BENCH.TITLE));
                })
                .withMaterial(PianoPluginConsts.MATERIAL, PianoPluginModels.BENCH.id())
                .withPermissions(PianoPluginPermissions.GUI.BENCH.BASE)
                .withPosition(2, 4);
    }


    public ButtonBuilder renameButton(Consumer<AsyncPlayerChatEvent> onChat)
    {

        var openMessage = FluentApi.messages().chat()
                .color(org.bukkit.ChatColor.AQUA)
                .bold()
                .inBrackets(pianoViewUI.translate(PianoPluginTranslations.GENERAL.INFO))
                .space()
                .reset()
                .text(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.RENAME.MESSAGE_1))
                .toString();


        var button = pianoViewUI.button();
        api.buttons().inputChat(button,options ->
        {
            options.onChatInput(onChat);
            options.setOpenMessage(openMessage);
        });


        return pianoViewUI.button()
                .withStyleRenderer(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.RENAME.TITLE));
                    buttonStyleBuilder.withDescription(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.RENAME.DESC));
                })
                .withMaterial(Material.NAME_TAG)
                .withPermissions(PianoPluginPermissions.GUI.PIANO.RENAME)
                .withPosition(0, 2);
    }


    public ButtonBuilder teleportButton() {
        return pianoViewUI.button()
                .withStyleRenderer(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.TELEPORT.TITLE));
                })
                .withMaterial(Material.ENDER_PEARL)
                .withPermissions(PianoPluginPermissions.GUI.PIANO.TELEPORT)
                .withPosition(2, 2);
    }


    public ButtonBuilder tokenButton() {
        return pianoViewUI.button()
                .withStyleRenderer(buttonStyleBuilder ->
                {
                    buttonStyleBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.TOKEN.TITLE));
                    buttonStyleBuilder.withDescription(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.TOKEN.DESC));
                })
                .withMaterial(Material.DIAMOND)
                .withHighlighted()
                .withPermissions(PianoPluginPermissions.GUI.PIANO.GENERATE_TOKEN)
                .withPosition(1, 4);
    }


    public ButtonBuilder pianoVolumeButton(Supplier<Observer<Integer>> observer) {
        var button = pianoViewUI.button().
                withMaterial(Material.BELL)
                .withPermissions(PianoPluginPermissions.GUI.PIANO.VOLUME)
                .withStyleRenderer(options ->
                {
                    options.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.VOLUME.TITLE));
                })
                .withPosition(1, 6);

        api.buttons().progressBar(button, options ->
        {
            options.setYield(5);
            options.setMaximum(100);
            options.setMinimum(0);
            options.setDataSource(observer);
        });

        return button;
    }


    public ButtonBuilder pianoParticleEffectSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<EffectInvoker>> effectSupplier,
            Observer<EffectInvoker> effectIndexSupplier) {

        return pianoViewUI.button()
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
                                event.buttonUI().withMaterial(event.data().getIcon());
                            });
                        }
                )
                .withMaterial(Material.FIREWORK_ROCKET)
                .withPermissions(PianoPluginPermissions.GUI.PIANO.EFFECT)
                .withStyleRenderer(options ->
                {
                    options.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.EFFECT.TITLE));
                })
                .withPosition(3, 2);
    }


    public ContentListWidget<PianoSkinData> pianoSkinSelectButton(Supplier<Observer<String>> observerSupplier,
                                                                  Supplier<PianoSkinData> selectedObserver,
                                                                  Supplier<List<PianoSkinData>> skinsSupplier)
    {
        var button = pianoViewUI.button()
                .withStyleRenderer(config ->
                {
                    config.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.SKIN.TITLE));
                   // config.withShiftClickInfo(pianoViewUI.translate(FluentTranslations.COLOR_PICKER.CHANGE_COLOR));
                })
                .withPermissions(PianoPluginPermissions.GUI.PIANO.SKIN)
                .withPosition(3, 4);

        return  api.buttons().<PianoSkinData>contentListWidget(button,contentListOptions ->
        {
            contentListOptions.setContentSource(skinsSupplier);
            contentListOptions.setSelectedItemObserver(selectedObserver);
            contentListOptions.setOnSelectionChanged(event ->
            {
                final var value = event.getSelectedItem();
                final var observer = observerSupplier.get();
                observer.set(value.getName());
                if (value.equals("none")) {
                    event.getButton().setMaterial(Material.NOTE_BLOCK);
                    return;
                }
                event.getButton().setCustomMaterial(PianoPluginConsts.MATERIAL, value.getCustomModelId());
            });
        });
    }


    public ButtonBuilder pianoSoundsSelectButton(
            Supplier<Observer<String>> observerSupplier,
            Supplier<List<PianoSoundData>> effectSupplier,
            Observer<PianoSoundData> effectIndexSupplier) {

        return pianoViewUI.button()
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
                .withMaterial(Material.MUSIC_DISC_WARD)
                .withPermissions(PianoPluginPermissions.GUI.PIANO.SOUND)
                .withStyleRenderer(options ->
                {
                    options.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.SOUNDS.TITLE));
                })
                .withPosition(3, 6);
    }


    public ButtonBuilder pianoOptionsButton(Supplier<List<CheckBox>> values) {
        return pianoViewUI.button()
                .withPermissions(PianoPluginPermissions.GUI.PIANO.SETTINGS.BASE)
                .withMaterial(Material.REPEATER)
                .withPosition(0, 1)
                .withStyleRenderer(descriptionInfoBuilder ->
                {
                    descriptionInfoBuilder.withTitle(pianoViewUI.translate(PianoPluginTranslations.GUI.PIANO.PIANO_OPTIONS.TITLE));
                });
    }
}
