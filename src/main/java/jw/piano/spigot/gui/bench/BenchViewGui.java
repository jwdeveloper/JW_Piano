/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.spigot.gui.bench;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Inject;
import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.dependecy_injection.api.enums.LifeTime;
import jw.fluent.api.desing_patterns.observer.implementation.Observer;
import jw.fluent.api.player_context.api.PlayerContext;
import jw.fluent.api.spigot.gui.fluent_ui.FluentChestUI;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.checkbox.CheckBox;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.chest_ui.ChestUI;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.translator.FluentTranslator;
import jw.piano.api.data.PluginPermissions;
import jw.piano.api.data.PluginTranslations;
import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.data.enums.AxisMove;
import jw.piano.api.observers.PianoDataObserver;
import jw.piano.api.piano.Piano;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PlayerContext
@Injection(lifeTime = LifeTime.SINGLETON)
public class BenchViewGui extends ChestUI {
    private final FluentTranslator lang;
    private final FluentChestUI fluentUI;
    private final Observer<AxisMove> axisObserver;
    private AxisMove axisMove;
    private PianoDataObserver dataObserver;
    private Piano piano;


    private List<CheckBox> checkBoxes;

    @Inject
    public BenchViewGui(FluentTranslator lang, FluentChestUI chestUI) {
        super("Bench", 3);
        this.lang = lang;
        this.fluentUI = chestUI;
        axisObserver = new Observer<>(this, "axisMove");
        checkBoxes = new ArrayList<>();
    }

    public void open(Player player, Piano piano) {
        this.piano = piano;
        this.dataObserver = piano.getPianoObserver();
        open(player);
    }

    @Override
    protected void onOpen(Player player) {

        checkBoxes.clear();
        checkBoxes.add(new CheckBox(
                lang.get(PluginTranslations.GUI.PIANO.BENCH_ACTIVE.TITLE),
                dataObserver.getBenchSettings().getActive(),
                PluginPermissions.GUI.BENCH.SETTINGS.ACTIVE));
    }

    @Override
    public void onInitialize() {

        setBorderMaterial(Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        setTitlePrimary(lang.get(PluginTranslations.GUI.BENCH.TITLE));
        var axis = Arrays.stream(AxisMove.values()).toList();
        fluentUI.buttonFactory().observeCheckBoxList(this, () -> checkBoxes, checkBoxListNotifierOptions ->
                {
                })
                .setDescription(descriptionInfoBuilder ->
                {
                    descriptionInfoBuilder.setTitle(lang.get(PluginTranslations.GUI.BENCH.TITLE));
                })
                .setMaterial(Material.REPEATER)
                .setLocation(0, 1)
                .build(this);

        fluentUI.buttonFactory()
                .observeList(() -> axisObserver, () -> axis, options ->
                {
                    options.setIgnoreRightClick(true);
                    options.setOnNameMapping(input ->
                            switch (input) {
                                case X -> lang.get(PluginTranslations.GUI.BENCH.MOVE.AXIS.X);
                                case Y -> lang.get(PluginTranslations.GUI.BENCH.MOVE.AXIS.Y);
                                case Z -> lang.get(PluginTranslations.GUI.BENCH.MOVE.AXIS.Z);
                            });
                })
                .setDescription(options ->
                {
                    options.setTitle(lang.get(PluginTranslations.GUI.BENCH.MOVE.TITLE));
                    options.addDescriptionLine(lang.get(PluginTranslations.GUI.BENCH.MOVE.DESC.MESSAGE_1));
                    options.addDescriptionLine(lang.get(PluginTranslations.GUI.BENCH.MOVE.DESC.MESSAGE_2));
                    options.setOnShiftClick(lang.get(PluginTranslations.GUI.BENCH.MOVE.CLICK.SHIFT));
                })
                .setOnShiftClick((player, button) ->
                {
                    var current = axisObserver.get();
                    onChangeBenchLocation(player, current);
                })
                .setMaterial(Material.LEAD)
                .setLocation(1, 1);
        //  .build(this);


        fluentUI.buttonBuilder().setDescription(config ->
                {
                    config.setTitle(lang.get(PluginTranslations.GUI.BENCH.RESET.TITLE));
                    config.addDescriptionLine(lang.get(PluginTranslations.GUI.BENCH.RESET.DESC));
                })
                .setMaterial(Material.TOTEM_OF_UNDYING)
                .setPermissions(PluginPermissions.GUI.BENCH.BASE)
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