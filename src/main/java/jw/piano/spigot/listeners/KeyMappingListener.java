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

package jw.piano.spigot.listeners;

import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.events.PianoKeyPressEvent;
import org.bukkit.event.EventHandler;

import java.util.HashMap;
import java.util.Map;


//waiting for 1.3 update
public class KeyMappingListener
{
    private Map<Integer, Integer> mapping = new HashMap<>();


    @EventHandler
    public void onPianoKey(PianoKeyPressEvent event)
    {
        var slot = event.getPlayer().getInventory().getHeldItemSlot();

        if(!event.isLeftClick())
        {
            mapping.put(slot, event.getPianoKey().getMidiIndex());
            event.getPlayer().sendMessage("Piano note set",event.getPianoKey().getName());
            event.setCancelled(true);

            event.getPiano().triggerNote(1, event.getPianoKey().getMidiIndex(), 20,1);
            FluentApi.tasks().taskLater(() ->
            {
                event.getPiano().triggerNote(0,event.getPianoKey().getMidiIndex(),0,1);
            },10);
            return;
        }

        if(!mapping.containsKey(slot))
        {
            return;
        }

        var index =  mapping.get(slot);

        event.getPiano().triggerNote(1, index, 100,0);
        FluentApi.tasks().taskLater(() ->
        {
            event.getPiano().triggerNote(0,index,0,0);
        },10);
    }

}
