package jw.piano.spigot.gui.bench;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
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
    private Piano piano;
    private PianoDataObserver dataObserver;

    private final ObserverBag<Integer> axisObserver = new ObserverBag<>(0);

    private FluentChestUI fluentChestUI;

    @Inject
    public BenchViewGui(FluentTranslator lang, FluentChestUI chestUI, BenchMoveService benchMoveService) {
        super("Bench", 3);
        this.lang = lang;
        this.benchMoveService = benchMoveService;
        this.fluentChestUI = chestUI;
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        this.dataObserver = piano.getPianoDataObserver();
        open(player);
    }

    @Override
    protected void onOpen(Player player) {
        fluentChestUI.buttonFactory()
                .observeBool(dataObserver.getBenchActiveBind())
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
        fluentChestUI.buttonFactory()
                .observeList(axisObserver.getObserver(),axis,options ->
                {
                    options.setOnNameMapping(input ->
                    {
                        var name = switch (input) {
                            case X -> "right / left";
                            case Y -> "up / down";
                            case Z -> "forward / backward";
                        };
                        return name;
                    });
                    options.setOnSelectionChanged(event ->
                    {
                        var itemstack = switch (event.data()) {
                            case X -> createXBanner();
                            case Y -> createYBanner();
                            case Z -> createZBanner();
                        };
                        event.buttonUI().setMaterial(itemstack.getType());
                        event.buttonUI().setMeta(itemstack.getItemMeta());
                    });
                })
                .setDescription(options ->
                {
                    options.setTitle("Move");
                    options.addDescriptionLine(FluentMessage
                            .message()
                            .bar(Emoticons.line, 20, ChatColor.GRAY).newLine()
                            .field(FluentApi.translator().get("gui.base.left-click"), "Edit position").newLine()
                            .field(FluentApi.translator().get("gui.base.right-click"), "Hande axis").newLine()
                            .toArray());
                })
                .setOnLeftClick((player, button) ->
                {
                    var current = axis.get(axisObserver.get());
                    onChangeBenchLocation(player, current);
                })
                .setLocation(1, 1)
                .build(this);


        ButtonUI.builder()
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermission.DESKTOP_CLIENT)
                .setTitlePrimary("Reset position")
                .setOnClick(this::onResetBenchLocation)
                .setDescription("set bench to default location")
                .setLocation(0, 8)
                .buildAndAdd(this);

        ButtonUI.factory()
                .goBackButton(this, getParent())
                .buildAndAdd(this);
    }

    private void onResetBenchLocation(Player player, ButtonUI buttonUI) {

        var optional = piano.getBench();
        if (optional.isEmpty()) {
            return;
        }
        var bench = optional.get();
        bench.resetLocation();
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
        FluentApi.messages().chat().info().textSecondary("Left click to").text(" Accept ", ChatColor.DARK_GREEN).textSecondary("position").send(player);
        FluentApi.messages().chat().info().textSecondary("Left right to cancel").text(" Cancel ", ChatColor.DARK_RED).textSecondary("position").send(player);
    }

    public ItemStack createXBanner() {
        ItemStack banner = new ItemStack(Material.CYAN_BANNER);
        BannerMeta meta = (BannerMeta) banner.getItemMeta();
        meta.addPattern(new Pattern(DyeColor.WHITE, PatternType.CROSS));
        meta.addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
        for (var flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        banner.setItemMeta(meta);
        return banner;
    }

    public ItemStack createZBanner() {
        ItemStack banner = new ItemStack(Material.CYAN_BANNER);
        ItemMeta meta = banner.getItemMeta();
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_TOP));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_BOTTOM));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
        for (var flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        banner.setItemMeta(meta);
        return banner;
    }


    public ItemStack createYBanner() {
        ItemStack banner = new ItemStack(Material.CYAN_BANNER);
        ItemMeta meta = banner.getItemMeta();
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNRIGHT));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.CYAN, PatternType.HALF_HORIZONTAL_MIRROR));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.WHITE, PatternType.STRIPE_DOWNLEFT));
        ((BannerMeta) meta).addPattern(new Pattern(DyeColor.CYAN, PatternType.BORDER));
        for (var flag : ItemFlag.values()) {
            meta.addItemFlags(flag);
        }
        banner.setItemMeta(meta);
        return banner;
    }
}