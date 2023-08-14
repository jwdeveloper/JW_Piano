package io.github.jwdeveloper.spigot.piano.api.piano;

import io.github.jwdeveloper.spigot.piano.api.data.PianoData;

public interface PianoFactory
{
     Piano create(PianoData pianoData);
}
