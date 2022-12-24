package jw.piano.api.data.config;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Data;

@Data
public class SkinConfig {
    @YamlProperty( name = "name", description = "test")
    private String name;

    @YamlProperty(name = "custom-model-id", description = "test")
    private Integer customModelId;

    @YamlProperty(name = "material", description = "test")
    private String material;
}
