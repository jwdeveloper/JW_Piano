package jw.piano.spigot.gui;

import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginConsts;
import jw.piano.api.data.PluginPermission;
import jw.piano.api.data.models.PianoSkin;
import jw.piano.handlers.webclient.WebClientLink;
import jw.piano.services.PianoService;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.spigot.gameobjects.PianoDataObserver;
import jw.piano.services.PianoSkinService;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.spigot.inventory_gui.EventsListenerInventoryUI;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonNotifier;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverEvent;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverUI;
import jw.fluent.api.spigot.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.utilites.messages.Emoticons;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@PlayerContext
@Injection(lifeTime = LifeTime.TRANSIENT)
public class PianoViewGUI extends ChestUI {
    private final PianoSkinService pianoSkinService;
    private final FluentTranslator lang;
    private final FluentMediator mediator;
    private PianoDataObserver pianoDataObserver;

    @Inject
    public PianoViewGUI(PianoSkinService pianoSkinService,
                        FluentTranslator lang,
                        FluentMediator mediator) {
        super("Piano", 5);
        this.mediator = mediator;
        this.pianoSkinService = pianoSkinService;
        this.lang = lang;
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoDataObserver();
        setTitle(piano.getPianoData().getName());
        open(player);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle("[" + title + "]");
    }

    @Override
    protected void onOpen(Player player) {

    }

    @Override
    public void onInitialize() {

        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);


        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getEnableBind())
                .setPermissions(PluginPermission.ACTIVE)
                .setTitlePrimary(lang.get("gui.piano.piano-active.title"))
                .setLocation(0, 1)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getInteractivePedalBind())
                .setPermissions(PluginPermission.PEDAl)
                .setTitlePrimary(lang.get("gui.piano.pedal-active.title"))
                .setLocation(0, 2)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getBenchActiveBind())
                .setPermissions(PluginPermission.BENCH)
                .setTitlePrimary(lang.get("gui.piano.bench-active.title"))
                .setLocation(0, 3)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getDetectPressInMinecraftBind())
                .setPermissions(PluginPermission.DETECT_KEY)
                .setTitlePrimary(lang.get("gui.piano.detect-key-active.title"))
                .setLocation(0, 4)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getDesktopClientAllowedBind())
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setTitlePrimary(lang.get("gui.piano.desktop-client-active.title"))
                .setLocation(0, 5)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getShowGuiHitBoxBind())
                .setPermissions(PluginPermission.SHOW_GUI_HITBOX)
                .setTitlePrimary(lang.get("gui.piano.show-gui-hitbox.title"))
                .setLocation(0, 6)
                .buildAndAdd(this);


        ButtonObserverUI.builder()
                .setOnClick((player, button) ->
                {
                    var id = pianoDataObserver.getPianoData().getUuid();
                    var pianoService = FluentApi.container().findInjection(PianoService.class);
                    var optional = pianoService.find(id);
                    if(optional.isEmpty())
                    {
                        FluentApi.messages()
                                .chat()
                                .error()
                                .text("Piano has not been found")
                                .send(player);
                        return;
                    }
                    var piano = optional.get();
                    piano.clear();
                    FluentApi.messages()
                            .chat()
                            .info()
                            .text("Piano has refreshed")
                            .send(player);
                    return;
                })
                .setMaterial(Material.SKELETON_SKULL)
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setTitlePrimary("Clear")
                .setDescription("Might be helpful when server has been suddenly shut down",
                                "and old piano model has not been properly removed.",
                                "Try also use /reload to clear all pianos")
                .setLocation(0, 8)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets(lang.get("gui.piano.teleport.title")))
                .setMaterial(Material.ENDER_PEARL)
                .setPermissions(PluginPermission.TELEPORT)
                .setLocation(2, 2)
                .setOnClick((player, button) ->
                {
                    var pianoLoc = pianoDataObserver.getLocationBind().get();
                    var destination = pianoLoc.clone();
                    destination.setDirection(pianoLoc.getDirection().multiply(-1));
                    destination.setZ(destination.getZ() + 2);
                    player.teleport(destination);
                })
                .buildAndAdd(this);


        ButtonObserverUI.builder()
                .addObserver(pianoDataObserver.getSkinIdBind(), new SkinModelNotifier(pianoSkinService.skins()))
                .setTitlePrimary(lang.get("gui.piano.skin.title"))
                .setPermissions(PluginPermission.PIANO, PluginPermission.SKIN)
                .setLocation(3, 4)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getEffectBind())
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PluginPermission.PIANO, PluginPermission.EFFECTS)
                .setTitlePrimary(lang.get("gui.piano.effect.title"))
                .setLocation(3, 2)
                .buildAndAdd(this);

        ButtonObserverUI.factory()
                .intRangeObserver(pianoDataObserver.getVolumeBind(), 0, 100, 5)
                .setMaterial(Material.BELL)
                .setPermissions(PluginPermission.PIANO, PluginPermission.VOLUME)
                .setTitlePrimary(lang.get("gui.piano.volume.title"))
                .setLocation(2, 6)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.PAPER)
                .setPermissions(PluginPermission.PIANO, PluginPermission.RENAME)
                .setTitlePrimary(lang.get("gui.piano.rename.title"))
                .setLocation(3, 6)
                .setDescription(lang.get("gui.piano.rename.desc"))
                .setOnClick((player, button) ->
                {
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
                        setTitle(s);
                        this.open(player);
                    });
                })
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.DIAMOND)
                .setHighlighted()
                .setTitlePrimary(lang.get("gui.piano.token.title"))
                .setDescription(lang.get("gui.piano.token.desc"))
                .setLocation(1, 4)
                .setOnClick(this::onTokenGeneration)
                .buildAndAdd(this);

        ButtonUI.factory()
                .goBackButton(this, getParent())
                .buildAndAdd(this);
    }


    private void onTokenGeneration(Player player, ButtonUI buttonUI) {
        if (!pianoDataObserver.getDesktopClientAllowedBind().get()) {
            FluentMessage.message().color(org.bukkit.ChatColor.AQUA).bold().inBrackets("Piano info").space().
                    reset().
                    text(lang.get("gui.piano.desktop-client-active.disabled")).send(player);
            return;
        }
        final var linkRequest = new WebClientLink.Request(player, pianoDataObserver.getPianoData());
        final var response = mediator.resolve(linkRequest, WebClientLink.Response.class);
        if (response == null) {
            FluentMessage.message().error().text("Can't generate link, unknown error");
            return;
        }

        FluentMessage.message().color(org.bukkit.ChatColor.AQUA).bold().inBrackets("Piano info").space().
                reset().
                text(lang.get("gui.piano.token.message-1")).send(player);

        final var msg = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + Emoticons.arrowRight + lang.get("gui.piano-menu.client-app.message"));
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginConsts.CLIENT_APP_URL));
        player.spigot().sendMessage(msg);


        final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + Emoticons.arrowRight + lang.get("gui.piano.token.click-to-copy"));
        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, response.getUrl()));

        final var hover = new Text(ChatColor.GRAY + lang.get("gui.piano.token.message-2"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        player.spigot().sendMessage(message);
        player.sendMessage(" ");
        close();
    }


    private final class SkinModelNotifier implements ButtonNotifier<Integer> {
        private final List<PianoSkin> skins;
        private final String prefix;
        private int currentIndex;

        public SkinModelNotifier(List<PianoSkin> skins) {
            this.skins = skins;
            this.prefix = org.bukkit.ChatColor.AQUA + Emoticons.dot + " ";
        }


        @Override
        public void onClick(ButtonObserverEvent<Integer> event) {
            if (skins.size() == 0) {
                return;
            }

            currentIndex = findIndex(event.getValue());
            currentIndex = (currentIndex + 1) % skins.size();
            var id = skins.get(currentIndex);
            event.getObserver().setValue(id.getCustomModelId());
        }

        @Override
        public void onValueChanged(ButtonObserverEvent<Integer> event) {
            final var button = event.getButton();
            final var description = new String[skins.size()];
            for (var i = 0; i < skins.size(); i++) {
                if (skins.get(i).getCustomModelId() == event.getValue()) {
                    description[i] = prefix + skins.get(i).getName();

                    if (event.getValue() == 0) {
                        button.setMaterial(Material.NOTE_BLOCK);
                    } else {
                        button.setCustomMaterial(PluginConsts.SKINS_MATERIAL, event.getValue());
                    }


                } else {
                    description[i] = skins.get(i).getName();
                }
            }
            button.setDescription(description);
        }

        private int findIndex(Integer pianoId) {
            var temp = 0;
            for (var skin : skins) {
                if (skin.getCustomModelId() == pianoId) {
                    return temp;
                }
                temp++;
            }
            return -1;
        }
    }

}


