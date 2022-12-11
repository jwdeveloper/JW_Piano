package jw.piano.spigot.listeners;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.spigot.events.EventBase;
import jw.fluent.api.spigot.inventory_gui.InventoryUI;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import jw.piano.services.BenchMoveService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;


@Injection(lazyLoad = false)
public class BenchMoveListener extends EventBase {
    private final BenchMoveService benchMoveService;



    public BenchMoveListener(BenchMoveService service) {
        benchMoveService = service;
    }

    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event) {
        benchMoveService.unregister(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(benchMoveService.accept(event.getPlayer()))
            {
                event.setCancelled(true);
            }
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(benchMoveService.cancel(event.getPlayer()))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent event) {
        var isIncreased = event.getPreviousSlot() < event.getNewSlot();
        FluentLogger.LOGGER.info("OLD", event.getPreviousSlot(), "NEW: ",event.getNewSlot());
        if (event.getPreviousSlot() == InventoryUI.INVENTORY_WIDTH-1 && event.getNewSlot() == 0) {
            FluentLogger.LOGGER.info("INCREASE");
            isIncreased = true;
        }
        if (event.getPreviousSlot() == 0 && event.getNewSlot() == InventoryUI.INVENTORY_WIDTH-1) {
            FluentLogger.LOGGER.info("DECREASE");
            isIncreased = false;
        }
        benchMoveService.move(event.getPlayer(), isIncreased);
    }


}
