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

package jw.piano.spigot.piano.managers.effects;

import jw.piano.api.data.PluginConsts;
import jw.piano.api.managers.effects.EffectInvoker;
import jw.piano.api.piano.keyboard.PianoKey;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class SimpleParticleEffect implements EffectInvoker {


    private World world;
    private Particle.DustOptions dustOptions;

    private final float SIZE = 0.3f;

    public SimpleParticleEffect()
    {

    }

    @Override
    public String getName() {
        return "simple particles";
    }

    @Override
    public void onNote(PianoKey pianoKey, Location location, int noteIndex, int velocity, Color color, boolean isPressed) {
        if(world == null)
            world = location.getWorld();
        if(!isPressed)
        {
            return;
        }
        world.spawnParticle(Particle.REDSTONE, location.clone().add( 0,1.8f,0), 2, new Particle.DustOptions(color, SIZE));
    }


    @Override
    public void onDestroy() {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void refresh() {

    }
}
