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

package jw.piano.spigot;




import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import jw.piano.api.data.PluginModels;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;


public class CrazyKey
{
    private HitBoxDisplay hitBoxDisplay;
    private InteractiveHitBox interactiveHitBox;
    private PluginModels.ResourceModel keyModel;
    private ItemDisplay display;

    private boolean isDown;

    public CrazyKey(Location location)
    {
        interactiveHitBox = new InteractiveHitBox(location, new Vector(1,1,1));
        hitBoxDisplay = new HitBoxDisplay(interactiveHitBox);
        hitBoxDisplay.show(0.5f);
        keyModel = PluginModels.PIANO_KEY;
        display = location.getWorld().spawn(location, ItemDisplay.class);
        display.setItemStack(keyModel.toItemStack());


        FluentApi.events().onEvent(PlayerInteractEvent.class,this::interactEvent);
    }


    public void interactEvent(PlayerInteractEvent event)
    {
        var player = event.getPlayer();
        if(!interactiveHitBox.isCollider(event.getPlayer().getEyeLocation(),10))
        {
            hitBoxDisplay.color(Color.RED);
            return;
        }

        if(isDown)
        {
            player.sendMessage("UP");
            moveUp();
        }
        else
        {
            player.sendMessage("Down");
            moveDown();

        }
        isDown = !isDown;
        hitBoxDisplay.color(Color.GREEN);
    }


    public void moveDown()
    {
        Vector3f translation =  new Vector3f(0,0,0);
        AxisAngle4f leftRotation =  new AxisAngle4f(-0.5f,1,0,0);
        Vector3f scale =  new Vector3f(1,1,1);
        AxisAngle4f rleftRotation =  new AxisAngle4f(0,0,0,0);
        Transformation transformation = new Transformation(translation,leftRotation,scale,rleftRotation);
        display.setInterpolationDelay(1);
        display.setTransformation(transformation);
        display.setInterpolationDuration(10);
    }

    public void moveUp()
    {
        Vector3f translation =  new Vector3f(0,0,0);
        AxisAngle4f leftRotation =  new AxisAngle4f(0,0,0,0);
        Vector3f scale =  new Vector3f(1,1,1);
        AxisAngle4f rleftRotation =  new AxisAngle4f(0,0,0,0);
        Transformation transformation = new Transformation(translation,leftRotation,scale,rleftRotation);
        display.setInterpolationDelay(1);
        display.setTransformation(transformation);
        display.setInterpolationDuration(2);
    }


}
