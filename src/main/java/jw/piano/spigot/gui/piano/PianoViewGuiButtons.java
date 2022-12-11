package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUIBuilder;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverUI;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PluginConsts;
import jw.piano.data.PluginPermission;
import jw.piano.data.enums.PianoKeysConst;
import jw.piano.services.PianoSkinService;
import jw.piano.spigot.gameobjects.PianoDataObserver;
import org.bukkit.Material;

@PlayerContext
@Injection(lifeTime = LifeTime.TRANSIENT)
public class PianoViewGuiButtons {
    private final FluentTranslator lang;
    private final PianoSkinService pianoSkinService;

    @Inject
    public PianoViewGuiButtons(FluentTranslator translator,
                               PianoSkinService pianoSkinService) {
        this.lang = translator;
        this.pianoSkinService = pianoSkinService;
    }

    public ButtonUIBuilder midiPlayerButton() {
        return ButtonUI.builder()
                .setMaterial(Material.MUSIC_DISC_13)
                .setTitlePrimary("Midi player")
                .setDescription("Play Midi files on this piano", "Files should be located in plugins/JW_Piano/midi")
                .setLocation(1, 2);
    }

    public ButtonUIBuilder pianoClearButton() {
        return ButtonUI.builder()
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setTitlePrimary("Clear")
                .setDescription("Might be helpful when server has been suddenly shut down",
                        "and old piano model has not been properly removed.",
                        "Try also use /reload to clear all pianos")
                .setLocation(0, 8);
    }


    public ButtonUIBuilder benchButton() {
        return ButtonUI.builder()
                .setTitlePrimary("Bench")
                .setCustomMaterial(PluginConsts.SKINS_MATERIAL, PianoKeysConst.BENCH.getId())
                .setLocation(2, 4);
    }

    public ButtonUIBuilder renameButton() {
        return ButtonUI.builder()
                .setMaterial(Material.PAPER)
                .setPermissions(PluginPermission.RENAME)
                .setTitlePrimary(lang.get("gui.piano.rename.title"))
                .setLocation(3, 6)
                .setDescription(lang.get("gui.piano.rename.desc"));
    }

    public ButtonUIBuilder teleportButton() {
        return ButtonUI.builder()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets(lang.get("gui.piano.teleport.title")))
                .setMaterial(Material.ENDER_PEARL)
                .setPermissions(PluginPermission.TELEPORT)
                .setLocation(2, 2);
    }

    public ButtonUIBuilder tokenButton() {
        return ButtonUI.builder()
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setTitlePrimary(lang.get("gui.piano.token.title"))
                .setDescription(lang.get("gui.piano.token.desc"))
                .setLocation(1, 4);
    }


    public void createObserverButtons(PianoDataObserver pianoDataObserver, PianoViewGUI pianoViewGUI) {
        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getEnableBind())
                .setPermissions(PluginPermission.ACTIVE)
                .setTitlePrimary(lang.get("gui.piano.piano-active.title"))
                .setLocation(0, 1)
                .buildAndAdd(pianoViewGUI);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getInteractivePedalBind())
                .setPermissions(PluginPermission.PEDAl)
                .setTitlePrimary(lang.get("gui.piano.pedal-active.title"))
                .setLocation(0, 2)
                .buildAndAdd(pianoViewGUI);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getDetectPressInMinecraftBind())
                .setPermissions(PluginPermission.DETECT_KEY)
                .setTitlePrimary(lang.get("gui.piano.detect-key-active.title"))
                .setLocation(0, 3)
                .buildAndAdd(pianoViewGUI);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getDesktopClientAllowedBind())
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setTitlePrimary(lang.get("gui.piano.desktop-client-active.title"))
                .setLocation(0, 4)
                .buildAndAdd(pianoViewGUI);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getShowGuiHitBoxBind())
                .setPermissions(PluginPermission.SHOW_GUI_HITBOX)
                .setTitlePrimary(lang.get("gui.piano.show-gui-hitbox.title"))
                .setLocation(0, 5)
                .buildAndAdd(pianoViewGUI);


        ButtonObserverUI.builder()
                .addObserver(pianoDataObserver.getSkinIdBind(), new SkinModelNotifier(pianoSkinService.skins()))
                .setTitlePrimary(lang.get("gui.piano.skin.title"))
                .setPermissions(PluginPermission.SKIN)
                .setLocation(3, 4)
                .buildAndAdd(pianoViewGUI);


        ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getEffectBind())
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PluginPermission.EFFECTS)
                .setTitlePrimary(lang.get("gui.piano.effect.title"))
                .setLocation(3, 2)
                .buildAndAdd(pianoViewGUI);

        ButtonObserverUI.factory()
                .intRangeObserver(pianoDataObserver.getVolumeBind(), 0, 100, 5)
                .setMaterial(Material.BELL)
                .setPermissions(PluginPermission.VOLUME)
                .setTitlePrimary(lang.get("gui.piano.volume.title"))
                .setLocation(2, 6)
                .buildAndAdd(pianoViewGUI);
    }
}
