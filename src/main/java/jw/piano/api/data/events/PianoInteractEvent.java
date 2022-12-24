package jw.piano.api.data.events;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

@Data
public class PianoInteractEvent
{
    private final PlayerInteractEvent event;
    private final Player player;
    private final boolean leftClick;
    private final Location eyeLocation;

    public PianoInteractEvent(PlayerInteractEvent event)
    {
        this.event = event;
        this.player = event.getPlayer();
        this.leftClick = checkClick();
        this.eyeLocation = player.getEyeLocation();
    }



    private boolean checkClick()
    {
        if(event.getAction() != Action.LEFT_CLICK_AIR && event.getAction()  != Action.LEFT_CLICK_BLOCK)
        {
           return false;
        }
        return true;
    }
}
