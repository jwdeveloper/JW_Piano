package jw.piano.handlers.web_client;

import jw.piano.data.PianoData;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.entity.Player;

@Data
@AllArgsConstructor
public class WebClientLinkRequest
{
    private Player player;

    private PianoData pianoData;
}
