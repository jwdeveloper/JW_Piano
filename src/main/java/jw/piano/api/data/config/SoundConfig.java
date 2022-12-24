package jw.piano.api.data.config;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Data;

@Data
public class SoundConfig {
    @YamlProperty(name = "name")
    private String name;

    @YamlProperty(name = "namespace", description = """
            Name of the folder that sounds are save in resourcepack
                    """)
    private String namespace;

    @YamlProperty(name = "sound-category", description = """
            Define sound category from minecraft settings that sound will play in.
             Allowed categories [AMBIENT, BLOCKS, HOSTILE, MASTER, MUSIC, NEUTRAL, RECORDS, VOICE, WEATHER]
            """)
    private String soundCategory;
}
