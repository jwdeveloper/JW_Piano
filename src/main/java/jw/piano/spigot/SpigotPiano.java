package jw.piano.spigot;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.Piano;
import jw.piano.core.services.PianoService;
import org.bukkit.Location;

import java.util.List;
import java.util.Optional;

public class SpigotPiano {
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
