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
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.Piano;
import jw.piano.core.services.PianoService;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

public class PianoApi {
    public static Optional<Piano> create(Location location, PianoData pianoData) {
        pianoData.setLocation(location);
        return FluentApi.container().findInjection(PianoService.class).create(pianoData);
    }

    public static Optional<Piano> create(Location location) {
        var data = new PianoData();
        data.setLocation(location);
        data.setName("piano");
        return FluentApi.container().findInjection(PianoService.class).create(data);
    }

    public static Optional<Piano> create(Location location, String name) {
        var data = new PianoData();
        data.setLocation(location);
        data.setName(name);
        return FluentApi.container().findInjection(PianoService.class).create(data);
    }

    public static void destroy(Piano piano) {
        FluentApi.container().findInjection(PianoService.class).delete(piano.getPianoObserver().getPianoData().getUuid());
    }
    public static List<Piano> getPianos() {
       return FluentApi.container().findInjection(PianoService.class).findAll();
    }
}
