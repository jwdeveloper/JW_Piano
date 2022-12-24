package jw.piano.spigot.gui.bench;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginPermission;
import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.data.enums.AxisMove;
import jw.piano.api.piano.Piano;
import jw.piano.api.observers.PianoObserver;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;

@PlayerContext
@Injection(lifeTime = LifeTime.SINGLETON)
public class BenchViewGui extends ChestUI {
    private final FluentTranslator lang;
    private final FluentChestUI fluentUI;
    private final Observer<AxisMove> axisObserver;
    private AxisMove axisMove;
    private PianoObserver dataObserver;
    private Piano piano;

    @Inject
    public BenchViewGui(FluentTranslator lang, FluentChestUI chestUI) {
        super("Bench", 3);
        this.lang = lang;
        this.fluentUI = chestUI;
        axisObserver = new Observer<>(this, "axisMove");
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        this.dataObserver = piano.getPianoObserver();
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
        var axis = Arrays.stream(AxisMove.values()).toList();
        fluentUI.buttonFactory()
                .observeList(() -> axisObserver, () -> axis, options ->
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
                    var current = axisObserver.get();
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
                .setOnLeftClick((player, button) ->
                {
                    piano.getBench().reset();
                })
                .setLocation(0, 8)
                .build(this);

        fluentUI.buttonFactory()
                .back(this, getParent())
                .build(this);
    }

    private void onChangeBenchLocation(Player player, AxisMove moveType) {
        var benchMove = BenchMove.builder()
                .axisMove(moveType)
                .timeout(20 * 60)
                .player(player)
                .onAccept(message ->
                {
                    open(player, piano);
                    FluentApi.messages().chat().text(message).send(player);
                })
                .onCanceled(message ->
                {
                    open(player, piano);
                    FluentApi.messages().chat().text(message).send(player);
                }).build();

        piano.getBench().move(benchMove);
        close();
        sendInfoMessage(player);
    }

    private void sendInfoMessage(Player player) {
        FluentApi.messages().chat().info().textPrimary("Scroll mouse").textSecondary(" to move bench").send(player);
        FluentApi.messages().chat().info().textSecondary("Left click: ").text(" Accept ", ChatColor.DARK_GREEN).textSecondary("position").send(player);
        FluentApi.messages().chat().info().textSecondary("Right click: ").text(" Cancel ", ChatColor.DARK_RED).textSecondary("position").send(player);
    }
}