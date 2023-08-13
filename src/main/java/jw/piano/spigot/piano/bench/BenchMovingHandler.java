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

package jw.piano.spigot.piano.bench;


import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.SimpleEvent;
import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.data.enums.AxisMove;
import jw.piano.api.data.events.BenchMovingEvent;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.util.Consumer;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class BenchMovingHandler {
    private final float SPEED = 0.3f;
    private final Vector DEFAULT_OFFSET = new Vector(-0.2, 0.61, 1.2);
    private final Vector MAX_OFFSET = new Vector(1, 1, 1);
    private final Location origin;
    private final List<SimpleEvent> events;
    private BenchMovingEvent benchMoveEvent;
    @Setter
    private Consumer<Location> onLocationUpdated;

    public BenchMovingHandler(Location origin) {
        this.origin = origin;
        events = new ArrayList<>();
        var quitEvent = FluentApi.events().onEvent(PlayerQuitEvent.class, this::onPlayerQuit);
        var kickEvent = FluentApi.events().onEvent(PlayerKickEvent.class, this::onPlayerKick);
        var interactEvent = FluentApi.events().onEvent(PlayerInteractEvent.class, this::onPlayerInteract);
        var slotEvent = FluentApi.events().onEvent(PlayerItemHeldEvent.class, this::onSlotChange);

        events.add(quitEvent);
        events.add(kickEvent);
        events.add(interactEvent);
        events.add(slotEvent);
    }


    public void move(BenchMove benchMove, Location startLocation) {
        if (benchMoveEvent != null) {
            benchMove.getOnCanceled().accept("some else is moving bench");
            return;
        }
        benchMoveEvent = new BenchMovingEvent(benchMove, startLocation);
    }


    private void onPlayerQuit(PlayerQuitEvent event) {
        if (!validateEvent(event)) {
            return;
        }
        benchMoveEvent = null;
    }

    private void onPlayerKick(PlayerKickEvent event) {
        if (!validateEvent(event)) {
            return;
        }
        benchMoveEvent = null;
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        if (!validateEvent(event)) {
            return;
        }
        var dto = benchMoveEvent.getBenchMoveDto();
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            dto.getOnAccept().accept(FluentApi.messages().chat().info().text("Location changed").toString());
            benchMoveEvent = null;

        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            dto.getOnCanceled().accept(FluentApi.messages().chat().info().text("Location canceled").toString());
            benchMoveEvent = null;
        }
        event.setCancelled(true);
    }

    private void onSlotChange(PlayerItemHeldEvent event) {
        if (!validateEvent(event)) {
            return;
        }

        var isIncreased = event.getPreviousSlot() < event.getNewSlot();
        if (event.getPreviousSlot() == InventoryUI.INVENTORY_WIDTH - 1 && event.getNewSlot() == 0) {
            isIncreased = true;
        }
        if (event.getPreviousSlot() == 0 && event.getNewSlot() == InventoryUI.INVENTORY_WIDTH - 1) {
            isIncreased = false;
        }

        var location = createNewLocation(benchMoveEvent.getStartLocation(), isIncreased, benchMoveEvent.getBenchMoveDto().getAxisMove());
        if (!validateLocation(location)) {
           // return;
        }
        onLocationUpdated.accept(location);
    }


    private Location createNewLocation(Location location, boolean increase, AxisMove moveGameType) {
        var grain = increase ? SPEED : -SPEED;
        var loc = location.clone();
        switch (moveGameType) {
            case X -> loc.add(grain, 0, 0);
            case Y -> loc.add(0, grain, 0);
            case Z -> loc.add(0, 0, grain);
        }
        return loc;
    }

    private boolean validateEvent(PlayerEvent event) {
        if (benchMoveEvent == null) {
            return false;
        }
        if (!event.getPlayer().equals(benchMoveEvent.getBenchMoveDto().getPlayer()))
        {
            return false;
        }
        return true;
    }

    private boolean validateLocation(Location location) {
        var difference = origin.subtract(location.clone()).toVector();
        var maxOffset = origin.clone().add(MAX_OFFSET);
        FluentLogger.LOGGER.info("Validacja");
        if (location.getX() < -maxOffset.getX() ||
                location.getX() > maxOffset.getX() ||
                location.getY() < -maxOffset.getY() ||
                location.getY() > maxOffset.getY() ||
                location.getZ() < -maxOffset.getZ() ||
                location.getZ() > maxOffset.getZ()
        ) {
            FluentLogger.LOGGER.info("nie dziala");
            return false;
        }
        return true;
    }

    public void destroy() {
        for (var event : events) {
            event.unregister();
        }
    }

    public Location reset() {
        return  origin.clone().add(DEFAULT_OFFSET);
    }
}
