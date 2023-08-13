package io.github.jwdeveloper.spigot.piano;

import io.github.jwdeveloper.ff.plugin.implementation.FluentApi;
import io.github.jwdeveloper.spigot.piano.api.data.PianoData;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveoper.spigot.piano.core.services.PianoService;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

public class PianoApi
{
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

    public static void destroy(Piano piano)
    {
        FluentApi.container().findInjection(PianoService.class).delete(piano.getPianoData().getUuid());
    }

    public static List<Piano> getPianos() {
        return FluentApi.container().findInjection(PianoService.class).findAll();
    }
}
