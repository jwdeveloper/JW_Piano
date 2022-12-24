package jw.piano.api.data.config;


import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Data;


@Data
public class PianoConfig {
    @YamlProperty(name = "models-limit",
            description = "Limit of pianos that could be spawn on the server")
    private int pianoInstancesLimit = 10;

    @YamlProperty(name = "piano-range",
     description = """
             Piano became interactive when player distance to piano is lower or equal that `piano-range`
             """
    )
    private float pianoRange = 3;

}
