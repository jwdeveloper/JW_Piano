package jw.piano.api.data.models;

import lombok.Data;

@Data
public class PedalsSettings
{
    private Boolean sustainPressed = false;
    private Boolean pedalInteraction = true;
}
