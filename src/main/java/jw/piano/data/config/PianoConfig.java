package jw.piano.data.config;

import jw.fluent.api.files.implementation.yml.api.annotations.YmlFile;
import jw.fluent.api.files.implementation.yml.api.annotations.YmlProperty;
import lombok.Data;

@YmlFile
@Data
public class PianoConfig
{
    @YmlProperty(path = "piano-config",
            name = "models-limit",
            description = "Limit of pianos that could be spawn on the server")
    private int pianoInstancesLimit = 10;

    @YmlProperty(path = "piano-config")
    private float minDistanceToPiano = 3;

    @YmlProperty(path = "piano-config")
    private float maxDistanceFromPiano = 3;

    @YmlProperty(path = "piano-config")
    private float maxDistanceFromKeys = 2;
}
