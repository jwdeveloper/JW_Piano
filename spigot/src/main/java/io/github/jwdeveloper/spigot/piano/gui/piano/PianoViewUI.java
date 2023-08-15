package io.github.jwdeveloper.spigot.piano.gui.piano;

import io.github.jwdeveloper.ff.color_picker.api.ColorInfo;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.observer.implementation.ObserverBag;
import io.github.jwdeveloper.ff.extension.gui.OLD.events.ButtonClickEvent;
import io.github.jwdeveloper.ff.extension.gui.OLD.observers.list.checkbox.CheckBox;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryApi;
import io.github.jwdeveloper.ff.extension.gui.api.InventoryDecorator;
import io.github.jwdeveloper.ff.extension.gui.implementation.buttons.ButtonUI;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.BorderComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.components.implementation.common.title.TitleComponent;
import io.github.jwdeveloper.ff.extension.gui.prefab.simple.SimpleGUI;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginPermissions;
import io.github.jwdeveloper.spigot.piano.api.PianoPluginTranslations;
import io.github.jwdeveloper.spigot.piano.api.data.PianoSkinData;
import io.github.jwdeveloper.spigot.piano.api.data.PianoSoundData;
import io.github.jwdeveloper.spigot.piano.api.managers.effects.EffectInvoker;
import io.github.jwdeveloper.spigot.piano.api.observers.PianoDataObserver;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveloper.spigot.piano.gameobjects.managers.effects.EmptyEffect;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;


@Injection
public class PianoViewUI extends SimpleGUI {

    private final ColorPickerGui colorPickerGui;
    private final BenchViewGui benchViewGui;
    private final MidiPlayerGui midiPlayerGui;
    private final KeyboardGui keyboardGui;
    private final ObserverBag<EffectInvoker> effectObserver;
    private final ObserverBag<PianoSoundData> soundObserver;
    @Getter
    private final List<CheckBox> checkBoxes;
    private PianoDataObserver pianoDataObserver;
    private Piano piano;

    private TitleComponent titleComponent;


    public PianoViewUI(BenchViewGui benchViewGui,
                       MidiPlayerGui midiPlayerGui,
                       KeyboardGui keyboardGui,
                       buttonsFactoryFactory buttons,
                       ColorPickerGui colorPickerGui) {
        this.midiPlayerGui = midiPlayerGui;
        this.benchViewGui = benchViewGui;
        this.buttonsFactory = buttons;
        effectObserver = new ObserverBag<>(new EmptyEffect());
        soundObserver = new ObserverBag<>(new PianoSoundData());
        checkBoxes = new ArrayList<>();
        this.colorPickerGui = colorPickerGui;
        this.keyboardGui = keyboardGui;
    }



    @Override
    public void onInit(InventoryDecorator decorator, InventoryApi inventoryApi) {



        onOpen(e->
        {
            skinObserver.set(piano.getSkinManager().getCurrent());
            effectObserver.set(piano.getEffectManager().getCurrent());
            soundObserver.set(piano.getSoundsManager().getCurrent());
        });

        titleComponent =addComponent(inventoryApi.components().title());
        titleComponent.setTitleModel("piano", ()-> pianoDataObserver.getPianoData().getName());
        addComponent(new BorderComponent()).setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);

        /*
        benchViewGui.setParent(this);
        midiPlayerGui.setParent(this);
        keyboardGui.setParent(this);
        colorPickerGui.setParent(this);
         */

        var buttonsFactory = new PianoViewUIButtons(this,decorator,inventoryApi);

        buttonsFactory.keyboardButton()
                .withOnLeftClick((p,b)->
                {
                    keyboardGui.open(p, piano.getPianoObserver().getPianoData().getKeyboardSettings());
                })
                .build(this);

        buttonsFactory.teleportButton()
                .withOnLeftClick(this::onTeleport);


        buttonsFactory.tokenButton()
                .withOnLeftClick(this::onTokenGeneration);


        buttonsFactory.renameButton(this::onRename);

        buttonsFactory.pianoClearButton()
                .withOnLeftClick(this::onPianoClear);

        buttonsFactory.benchButton()
                .withOnLeftClick((player, button) ->
                {
                    benchViewGui.open(player, piano);
                }).build(this);

        buttonsFactory.midiPlayerButton()
                .withOnLeftClick((player, button) ->
                {
                    midiPlayerGui.open(player, piano);
                }).build(this);


        buttonsFactory.pianoVolumeButton(() -> pianoDataObserver.getVolume());

        buttonsFactory.pianoOptionsButton(this::getCheckBoxes);

        var skinsList = buttonsFactory.pianoSkinSelectButton(
                        () -> pianoDataObserver.getSkinName(),
                        () -> piano.getSkinManager().getItems());
              //  .setOnShiftClick(this::onSkinColor);

        skinsList.

        buttonsFactory.pianoParticleEffectSelectButton(
                        () -> pianoDataObserver.getEffectName(),
                        () -> piano.getEffectManager().getItems(),
                        effectObserver.getObserver());

        buttonsFactory.pianoSoundsSelectButton(
                        () -> pianoDataObserver.getSoundName(),
                        () -> piano.getSoundsManager().getItems(),
                        soundObserver.getObserver());


       // buttonsFactory.backButton(this).build(this);
    }


    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoObserver();
        this.piano = piano;

        checkBoxes.clear();
        checkBoxes.add(new CheckBox(
                translate(PianoPluginTranslations.GUI.PIANO.PEDAL_ACTIVE.TITLE),
                pianoDataObserver.getPedalsSettings().getPedalInteraction(),
                PianoPluginPermissions.GUI.PIANO.SETTINGS.PEDAL_PRESSING_ACTIVE));

        checkBoxes.add(new CheckBox(
                translate(PianoPluginTranslations.GUI.PIANO.DESKTOP_CLIENT_ACTIVE.TITLE),
                pianoDataObserver.getDesktopClientAllowed(),
                PianoPluginPermissions.GUI.PIANO.SETTINGS.DESKTOP_APP_ACTIVE));

        checkBoxes.add(new CheckBox(
                translate(PianoPluginTranslations.GUI.PIANO.DETECT_KEY_ACTIVE.TITLE),
                pianoDataObserver.getInteractiveKeyboard(),
                PianoPluginPermissions.GUI.PIANO.SETTINGS.KEYBOARD_PRESSING_ACTIVE));

        checkBoxes.add(new CheckBox(
                translate(PianoPluginTranslations.GUI.PIANO.PIANIST_ACTIVE.TITLE),
                pianoDataObserver.getShowPianist(),
                PianoPluginPermissions.GUI.PIANO.SETTINGS.PIANIST_ACTIVE));
        open(player);
    }


    private void onRename(AsyncPlayerChatEvent event) {

        pianoDataObserver.getPianoData().setName(event.getMessage());

    }

    private void onTeleport(ButtonClickEvent event) {
        piano.teleportPlayer(event.getPlayer());
    }

    private void onPianoClear(ButtonClickEvent event) {
        piano.reset();
        FluentApi.messages()
                .chat()
                .text(translate(PianoPluginTranslations.GUI.PIANO.CLEAR.MESSAGE_CLEAR))
                .send(event.getPlayer());
    }

    private void onSkinColor(ButtonClickEvent event)
    {
        var skin = piano.getSkinManager().getCurrent();
        if(skin.getItemStack().getType() == Material.AIR)
        {
            return;
        }
        colorPickerGui.setItemStack(skin.getItemStack());
        colorPickerGui.onContentPicked((player1, button1) ->
        {
            var data = button1.<ColorInfo>getDataContext();
            piano.setColor(data.getColor());
            open(player,piano);
        });
        colorPickerGui.open(player);
    }

    private void onTokenGeneration(ButtonClickEvent event) {
        piano.getTokenGenerator().generateAndSend(event.getPlayer());
        close();
    }



}