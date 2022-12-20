package jw.piano.spigot.gui.bench;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.messages.FluentMessage;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.data.PluginPermission;
import jw.piano.data.dto.BenchMoveDto;
import jw.piano.data.enums.MoveGameObjectAxis;
import jw.piano.services.BenchMoveService;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.spigot.gameobjects.PianoDataObserver;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

@PlayerContext
@Injection(lifeTime = LifeTime.SINGLETON)
public class BenchViewGui extends ChestUI {
    private final FluentTranslator lang;
    private final BenchMoveService benchMoveService;
    private final FluentChestUI fluentUI;
    private final ObserverBag<Integer> axisObserver = new ObserverBag<>(0);
    private Piano piano;
    private PianoDataObserver dataObserver;

    @Inject
    public BenchViewGui(FluentTranslator lang, FluentChestUI chestUI, BenchMoveService benchMoveService) {
        super("Bench", 3);
        this.lang = lang;
        this.benchMoveService = benchMoveService;
        this.fluentUI = chestUI;
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        this.dataObserver = piano.getPianoDataObserver();
        open(player);
    }

    @Override
    protected void onOpen(Player player) {
        fluentUI.buttonFactory()
                .observeBool(() -> dataObserver.getBenchActiveBind())
                .setPermissions(PluginPermission.BENCH)
                .setDescription(options ->
                {
                    options.setTitle(lang.get("gui.piano.bench-active.title"));
                })
                .setLocation(0, 1)
                .build(this);
    }

    @Override
    public void onInitialize() {

        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        setTitlePrimary("Bench settings");
        var axis = Arrays.stream(MoveGameObjectAxis.values()).toList();
        fluentUI.buttonFactory()
                .observeList(axisObserver::getObserver, axis, options ->
                {
                    options.setIgnoreRightClick(true);
                    options.setOnNameMapping(input ->
                            switch (input) {
                                case X -> "right / left";
                                case Y -> "up / down";
                                case Z -> "forward / backward";
                            });
                })
                .setDescription(options ->
                {
                    options.setTitle("Move");
                    options.addDescriptionLine("Default location might not fit to you.");
                    options.addDescriptionLine("But it can edit by mouse scrolling");
                    options.setOnLeftClick("Change direction");
                    options.setOnRightClick("Edit position");
                })
               .setOnRightClick((player, button) ->
                {
                    var current = axis.get(axisObserver.get());
                    onChangeBenchLocation(player, current);
                })
                .setMaterial(Material.LEAD)
                .setLocation(1, 1)
                .build(this);


        fluentUI.buttonBuilder().setDescription(config ->
                {
                    config.setTitle("Reset position");
                    config.addDescriptionLine("Teleport bench to its default location");
                })
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setOnLeftClick(this::onResetBenchLocation)
                .setLocation(0, 8)
                .build(this);

        fluentUI.buttonFactory()
                .back(this, getParent())
                .build(this);
    }

    private void onResetBenchLocation(Player player, ButtonUI buttonUI) {

        var optional = piano.getBench();
        if (optional.isEmpty()) {
            return;
        }
        var bench = optional.get();
        var defaultOffset = bench.resetLocation();
        piano.getPianoData().getBenchSettings().setOffset(defaultOffset);
    }

    private void onChangeBenchLocation(Player player, MoveGameObjectAxis moveType) {
        var dto = new BenchMoveDto();
        dto.setOnAccept(message ->
        {
            open(player, piano);
            FluentApi.messages().chat().text(message).send(player);
        });
        dto.setOnCanceled(message ->
        {
            open(player, piano);
            FluentApi.messages().chat().text(message).send(player);
        });
        dto.setPiano(piano);
        dto.setPlayer(player);
        dto.setMoveType(moveType);


        var result = benchMoveService.register(player.getUniqueId(), dto);
        if (!result) {
            errorMessage(player);
            return;
        }
        close();
        sendInfoMessage(player);
    }

    private void errorMessage(Player player) {
        FluentApi.messages().chat().error().textSecondary("Both piano and bench must be active (visible)").send(player);
    }

    private void sendInfoMessage(Player player) {
        FluentApi.messages().chat().info().textPrimary("Scroll mouse").textSecondary(" to move bench").send(player);
        FluentApi.messages().chat().info().textSecondary("Left click: ").text(" Accept ", ChatColor.DARK_GREEN).textSecondary("position").send(player);
        FluentApi.messages().chat().info().textSecondary("Right click: ").text(" Cancel ", ChatColor.DARK_RED).textSecondary("position").send(player);
    }
}