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

package io.github.jwdeveloper.spigot.piano.listeners;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Inject;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.ff.core.spigot.events.implementation.EventBase;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import io.github.jwdeveloper.ff.core.spigot.tasks.implementation.SimpleTaskTimer;
import io.github.jwdeveloper.spigot.piano.api.events.PianoInteractEvent;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveoper.spigot.piano.core.services.PianoService;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;

@Injection(lazyLoad = false)
public class PlayerInteractionListener extends EventBase {

    private final PianoService pianoService;
    private final HashMap<Player, Piano> pianoUsers;
    private final SimpleTaskTimer checkPlayerToPianoDistanceTask;
    private final FluentTaskFactory tasks;

    @Inject
    public PlayerInteractionListener(Plugin plugin, PianoService service, FluentTaskFactory taskManager) {
        super(plugin);
        this.pianoUsers = new HashMap<>();
        this.pianoService = service;
        this.tasks = taskManager;
        this.checkPlayerToPianoDistanceTask = checkDistanceTask();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEvent(PlayerInteractEvent event) {
        if (isPluginDisabled()) {
            return;
        }
        final var player = event.getPlayer();
        if (!pianoUsers.containsKey(player)) {
            var pianoOptional = pianoService.getNearestPiano(player.getLocation());
            if (pianoOptional.isEmpty())  //there is no piano in player the nearest location;
            {
                return;
            }
            pianoUsers.put(player, pianoOptional.get());
        }
        final var piano = pianoUsers.get(player);
        final var result = piano.triggerPlayerClick(new PianoInteractEvent(event, piano));
        event.setCancelled(result);
    }


    @EventHandler
    public void onChangeSlotEvent(PlayerSwapHandItemsEvent event) {
        if (isPluginDisabled()) {
            return;
        }
        final var piano = pianoUsers.get(event.getPlayer());
        if (piano == null)
            return;

        var result = piano.getPedals().triggerSustainPedal(event.getPlayer());
        event.setCancelled(result);
    }


    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent event) {
        if (isPluginDisabled()) {
            return;
        }
        pianoUsers.remove(event.getPlayer());
    }

    @Override
    public void onPluginStart(PluginEnableEvent event) {
        checkPlayerToPianoDistanceTask.run();
    }

    @Override
    public void onPluginStop(PluginDisableEvent event) {
        checkPlayerToPianoDistanceTask.stop();
    }

    private SimpleTaskTimer checkDistanceTask() {
        var playersToRemove = new ArrayList<>();
        return tasks.taskTimer(5,(iteration, task) ->
        {
            playersToRemove.clear();
            for (var pianoPlayer : pianoUsers.entrySet()) {
                if (!pianoPlayer.getValue().isLocationAtPianoRange(pianoPlayer.getKey().getLocation())) {
                    playersToRemove.add(pianoPlayer.getKey());
                }
            }
            for (var toRemove : playersToRemove) {
                pianoUsers.remove(toRemove);
            }
        });
    }

}
