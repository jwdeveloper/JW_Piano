package io.github.jwdeveloper.spigot.piano.api.events;

import io.github.jwdeveloper.spigot.piano.api.piano.Piano;
import io.github.jwdeveloper.spigot.piano.api.piano.keyboard.PianoKey;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PianoKeyPressEvent extends Event implements Cancellable
{
    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private final PianoKey pianoKey;

    @Getter
    private final Player player;

    @Getter
    private final Piano piano;

    @Getter
    private final boolean leftClick;

    @Setter
    private boolean cancelled;

    public PianoKeyPressEvent(Player player, PianoKey pianoKey, Piano piano, boolean leftClick)
    {
        this.player = player;
        this.pianoKey =pianoKey;
        this.leftClick = leftClick;
        this.piano = piano;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
