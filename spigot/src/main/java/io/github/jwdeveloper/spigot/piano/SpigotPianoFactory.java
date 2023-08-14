package io.github.jwdeveloper.spigot.piano;

import io.github.jwdeveloper.ff.core.injector.api.annotations.Injection;
import io.github.jwdeveloper.spigot.piano.api.data.PianoData;
import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveloper.spigot.piano.api.piano.PianoFactory;
import io.github.jwdeveloper.spigot.piano.gameobjects.PianoImpl;

@Injection
public class SpigotPianoFactory implements PianoFactory {
    @Override
    public Piano create(PianoData pianoData) {
        return new PianoImpl(pianoData);
    }
}
