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

package jw.piano.core.services;


import io.github.jwdeveloper.ff.core.common.logger.FluentLogger;
import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import jw.piano.core.repositories.ColorsRepository;
import org.bukkit.Color;
import java.util.List;

@Injection(lazyLoad = false)
public class ColorsService
{
    private final ColorsRepository repository;

    public ColorsService(ColorsRepository repository)
    {
        this.repository = repository;
    }


    public List<ColorInfo> getColors()
    {
        var result = repository.findAll();
        if(result.size() == 0)
        {
            load(result);
            repository.save();
        }


        return repository.findAll();
    }

    public void load(List<ColorInfo> colors)
    {
        colors.add(new ColorInfo("brown", brown(),true));
        for (var field : Color.class.getDeclaredFields()) {
            try {
                if (!field.getType().equals(Color.class)) {
                    continue;
                }
                field.setAccessible(true);
                var value = (Color) field.get(null);
                field.setAccessible(false);
                var name = field.getName().toLowerCase();
                if(name.equals("black"))
                {
                    colors.add(new ColorInfo(field.getName().toLowerCase(), black(),true));
                    continue;
                }
                colors.add(new ColorInfo(field.getName().toLowerCase(), value,true));
            } catch (Exception e) {
                FluentLogger.LOGGER.error("Color", e);
            }
        }
    }


    public void addColor(ColorInfo colorInfo)
    {
        repository.insertOne(colorInfo);
    }


    public void removeColor(ColorInfo colorInfo)
    {
        repository.deleteOne(colorInfo);
    }

    public static Color black()
    {
        return Color.fromRGB(55,55,55);
    }


    public static Color brown()
    {
        return Color.fromRGB(131,81,25);
    }
}
