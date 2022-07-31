package jw.piano.gui;

import jw.piano.data.PianoPermission;
import jw.piano.data.PianoSkin;
import jw.piano.data.PianoConfig;
import jw.piano.game_objects.Piano;
import jw.piano.game_objects.PianoDataObserver;
import jw.piano.handlers.web_client.WebClientLinkRequest;
import jw.piano.service.PianoSkinService;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Inject;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.annotations.Injection;
import jw.spigot_fluent_api.desing_patterns.dependecy_injection.enums.LifeTime;
import jw.spigot_fluent_api.desing_patterns.mediator.FluentMediator;
import jw.spigot_fluent_api.fluent_gui.EventsListenerInventoryUI;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.*;
import jw.spigot_fluent_api.fluent_gui.implementation.chest_ui.ChestUI;
import jw.spigot_fluent_api.fluent_message.FluentMessage;
import jw.spigot_fluent_api.fluent_plugin.languages.Lang;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class PianoViewGUI extends ChestUI {

    private PianoDataObserver pianoDataObserver;
    private final PianoConfig settings;
    private PianoSkinService pianoSkinService;

    @Inject
    public PianoViewGUI(PianoConfig settings, PianoSkinService pianoSkinService) {
        super("Piano", 5);
        this.settings = settings;
        this.pianoSkinService = pianoSkinService;
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


        ButtonUI.builder()
                .setTitle(FluentMessage.message().color(org.bukkit.ChatColor.AQUA).inBrackets(Lang.get("gui.piano.teleport.title")))
                .setMaterial(Material.ENDER_PEARL)
                .setPermissions(PianoPermission.PIANO, PianoPermission.TELEPORT)
                .setLocation(2, 2)
                .setOnClick((player, button) ->
                {
                    var pianoLoc = pianoDataObserver.getLocationBind().get();
                    var destination = pianoLoc.clone();
                    destination.setDirection(pianoLoc.getDirection().multiply(-1));
                    destination.setZ(destination.getZ()+2);
                    player.teleport(destination);
                })
                .buildAndAdd(this);


        ButtonObserverUI.builder()
                .addObserver(pianoDataObserver.getSkinIdBind(), new SkinModelNotifier(pianoSkinService.skins()))
                .setTitlePrimary(Lang.get("gui.piano.skin.title"))
                .setPermissions(PianoPermission.PIANO, PianoPermission.SKIN)
                .setLocation(3, 4)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .enumSelectorObserver(pianoDataObserver.getEffectBind())
                .setMaterial(Material.FIREWORK_ROCKET)
                .setPermissions(PianoPermission.PIANO, PianoPermission.EFFECTS)
                .setTitlePrimary(Lang.get("gui.piano.effect.title"))
                .setLocation(3, 2)
                .buildAndAdd(this);



        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getEnableBind())
                .setMaterial(Material.REDSTONE_TORCH)
                .setPermissions(PianoPermission.PIANO, PianoPermission.ACTIVE)
                .setTitlePrimary(Lang.get("gui.piano.piano-active.title"))
                .setLocation(0, 1)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getInteractivePedalBind())
                .setMaterial(Material.REDSTONE_TORCH)
                .setPermissions(PianoPermission.PIANO, PianoPermission.PEDAl)
                .setTitlePrimary(Lang.get("gui.piano.pedal-active.title"))
                .setLocation(0, 2)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .boolObserver(pianoDataObserver.getBenchActiveBind())
                .setMaterial(Material.REDSTONE_TORCH)
                .setPermissions(PianoPermission.PIANO, PianoPermission.BENCH)
                .setTitlePrimary(Lang.get("gui.piano.bench-active.title"))
                .setLocation(0, 3)
                .buildAndAdd(this);


        ButtonObserverUI.factory()
                .intSelectObserver(pianoDataObserver.getVolumeBind(), 0, 100, 5)
                .setMaterial(Material.BELL)
                .setPermissions(PianoPermission.PIANO, PianoPermission.VOLUME)
                .setTitlePrimary(Lang.get("gui.piano.volume.title"))
                .setLocation(2, 6)
                .buildAndAdd(this);

        ButtonUI.builder()
                .setMaterial(Material.PAPER)
                .setPermissions(PianoPermission.PIANO, PianoPermission.RENAME)
                .setTitlePrimary(Lang.get("gui.piano.rename.title"))
                .setLocation(3, 6)
                .setDescription(Lang.get("gui.piano.rename.desc"))
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
                .setTitlePrimary(Lang.get("gui.piano.token.title"))
                .setDescription(Lang.get("gui.piano.token.desc"))
                .setLocation(1, 4)
                .setOnClick((player, button) ->
                {
                    FluentMessage.message().color(org.bukkit.ChatColor.AQUA).bold().inBrackets("Piano info").space().
                            reset().
                            text("Copy and paste token to Piano client app").send(player);
                    player.sendMessage(" ");
                    final var msg = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + Emoticons.arrowRight + " [Download client app]");
                    msg.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, settings.CLIENT_APP_URL));
                    player.spigot().sendMessage(msg);


                    player.sendMessage(" ");
                    final var linkRequest = new WebClientLinkRequest(player, pianoDataObserver.getPianoData());
                    final var url = FluentMediator.resolve(linkRequest, String.class);
                    final var message = new TextComponent(ChatColor.AQUA + "" + ChatColor.BOLD + Emoticons.arrowRight + " [Click to copy token]");
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, url));

                    final var hover = new Text(ChatColor.GRAY + "value will be copied after click");
                    message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
                    player.spigot().sendMessage(message);
                    player.sendMessage(" ");

                    close();
                })
                .buildAndAdd(this);

        ButtonUI.factory()
                .goBackButton(this, getParent())
                .buildAndAdd(this);
    }


    public class SkinModelNotifier implements ButtonNotifier<Integer> {
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
            final var descrition = new String[skins.size()];
            for (var i = 0; i < skins.size(); i++) {
                if (skins.get(i).getCustomModelId() == event.getValue()) {
                    descrition[i] = prefix + skins.get(i).getName();

                    if(event.getValue() == 0)
                    {
                        button.setMaterial(Material.NOTE_BLOCK);
                    }
                    else
                    {
                        button.setCustomMaterial(PianoConfig.SKINS_MATERIAL, event.getValue());
                    }


                } else {
                    descrition[i] = skins.get(i).getName();
                }
            }
            button.setDescription(descrition);
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


