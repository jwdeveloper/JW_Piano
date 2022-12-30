package jw.piano.api.piano;


import jw.piano.api.managers.effects.EffectManager;
import jw.piano.api.managers.skins.SkinManager;
import jw.piano.api.managers.sounds.SoundsManager;
import jw.piano.api.observers.PianoObserver;
import jw.piano.api.piano.common.*;
import jw.piano.api.piano.keyboard.Keyboard;
import jw.piano.api.piano.pedals.PedalGroup;
import jw.piano.api.piano.token.TokenGenerator;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Piano extends Teleportable, Interactable, Visiable, GuiViewer, Resetable {


    TokenGenerator getTokenGenerator();
    SoundsManager getSoundsManager();

    SkinManager getSkinManager();

    EffectManager getEffectManager();

    Bench getBench();

    PedalGroup getPedals();

    Keyboard getKeyboard();

    MidiPlayer getMidiPlayer();

    void setColor(Color color);

    PianoObserver getPianoObserver();

    void setVisible(boolean isVisible);

    void setVolume(int volume);

    void teleportPlayer(Player player);

    boolean isLocationAtPianoRange(Location location);

    boolean openGui(Player player);

    void triggerNote(int pressed, int midiIndex, int velocity);

    void triggerPedal(int isPressed, int midiIndex, int velocity);


}
