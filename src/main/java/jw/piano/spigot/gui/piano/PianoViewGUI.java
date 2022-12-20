package jw.piano.spigot.gui.piano;

import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.fluent.plugin.implementation.modules.mediator.FluentMediator;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PluginConsts;
import jw.piano.mediator.piano.webclient_link.WebClientLink;
import jw.piano.services.PianoService;
import jw.piano.services.PianoSkinService;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.spigot.gameobjects.PianoDataObserver;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.spigot.gui.inventory_gui.EventsListenerInventoryUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.piano.spigot.gui.bench.BenchViewGui;
import jw.piano.spigot.gui.midi.MidiPlayerGui;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@PlayerContext
@Injection
public class PianoViewGUI extends ChestUI {

    private final FluentMediator mediator;
    private final PianoViewButtonsFactory pianoViewButtons;
    private final BenchViewGui benchViewGui;
    private final MidiPlayerGui midiPlayerGui;
    private final FluentTranslator lang;
    private final Observer<Integer> skinIndexObserver;
    private final PianoSkinService skinService;
    private PianoDataObserver pianoDataObserver;
    private Piano piano;


    @Inject
    public PianoViewGUI(FluentMediator mediator,
                        FluentTranslator translator,
                        PianoSkinService skinService,
                        BenchViewGui benchViewGui,
                        MidiPlayerGui midiPlayerGui,
                        PianoViewButtonsFactory buttons) {
        super("Piano", 5);
        this.mediator = mediator;
        this.midiPlayerGui = midiPlayerGui;
        this.benchViewGui = benchViewGui;
        this.pianoViewButtons = buttons;
        this.lang = translator;
        this.skinService = skinService;
        skinIndexObserver = new ObserverBag<>(0).getObserver();
    }

    public void open(Player player, Piano piano) {
        pianoDataObserver = piano.getPianoDataObserver();
        this.piano = piano;
        setTitlePrimary(piano.getPianoData().getName());
        open(player);
    }

    @Override
    protected void onOpen(Player player) {
        var skinIndex = skinService.getSkinIndex(pianoDataObserver.getPianoData().getSkinId());
        skinIndexObserver.set(skinIndex);
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

        pianoViewButtons.pianoEnableButton(() -> pianoDataObserver.getEnableBind()).build(this);
        pianoViewButtons.pianoDesktopClientEnableButton(() -> pianoDataObserver.getDesktopClientAllowedBind()).build(this);
        pianoViewButtons.pianoKeyboardEnableButton(() -> pianoDataObserver.getDetectPressInMinecraftBind()).build(this);
        pianoViewButtons.pianoPedalEnableButton(() -> pianoDataObserver.getInteractivePedalBind()).build(this);


        pianoViewButtons.pianoSkinSelectButton(() -> pianoDataObserver.getSkinIdBind(), skinIndexObserver).build(this);
        pianoViewButtons.pianoParticleEffectSelectButton(() -> pianoDataObserver.getEffectBind()).build(this);
        pianoViewButtons.pianoVolumeButton(() -> pianoDataObserver.getVolumeBind()).build(this);

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
        var pianoLoc = pianoDataObserver.getLocationBind().get();
        var destination = pianoLoc.clone();
        destination.setDirection(pianoLoc.getDirection().multiply(-1));
        destination.setZ(destination.getZ() + 2);
        player.teleport(destination);
    }

    private void onPianoClear(Player player, ButtonUI buttonUI) {
        var id = pianoDataObserver.getPianoData().getUuid();
        var pianoService = FluentApi.container().findInjection(PianoService.class);
        var optional = pianoService.find(id);
        if (optional.isEmpty()) {
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

        FluentMessage.message()
                .color(org.bukkit.ChatColor.AQUA)
                .bold()
                .inBrackets("Piano info")
                .space()
                .reset()
                .text(lang.get("gui.piano.token.message-1")).send(player);

        final var desktopAppMessage = FluentMessage.message()
                .text(ChatColor.AQUA)
                .text(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(lang.get("gui.piano-menu.client-app.message"))
                .toTextComponent();
        desktopAppMessage.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, PluginConsts.CLIENT_APP_URL));


        final var tokenCopyMessage = FluentMessage.message()
                .text(ChatColor.AQUA)
                .text(ChatColor.BOLD)
                .text(Emoticons.arrowRight)
                .space()
                .text(lang.get("gui.piano.token.click-to-copy"))
                .toTextComponent();
        tokenCopyMessage.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, response.getUrl()));
        final var hover = new Text(ChatColor.GRAY + lang.get("gui.piano.token.message-2"));
        tokenCopyMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));

        player.sendMessage(" ");
        player.spigot().sendMessage(desktopAppMessage);
        player.spigot().sendMessage(tokenCopyMessage);
        player.sendMessage(" ");
        close();
    }


}


