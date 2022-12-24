package jw.piano.api.data;

import jw.fluent.api.spigot.permissions.api.annotations.PermissionGroup;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionProperty;

public class PluginPermission
{

    public final static String PIANO = "piano";

    @PermissionGroup(group = "commands")
    @PermissionProperty(description = "player open gui to create/remove piano")
    public final static String PIANO_CMD = "piano.commands.piano";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can create piano")
    public final static String CREATE = PIANO +".create";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can remove piano")
    public final static String REMOVE = PIANO +".remove";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can edit piano volume in GUI")
    public final static String VOLUME = PIANO +".volume";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can rename piano in GUI")
    public final static String RENAME = PIANO +".rename";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player change piano skin in GUI")
    public final static String SKIN = PIANO +".skin";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player change piano state in GUI")
    public final static String ACTIVE = PIANO +".active";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can change piano sound")
    public final static String SOUND = PIANO +".sound";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player set piano particles in GUI")
    public final static String EFFECTS = PIANO +".effects";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player enable/disable piano pedal in GUI")
    public final static String PEDAl = PIANO +".pedal";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "open bench GUI")
    public final static String BENCH = PIANO +".bench";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "change visibility of bench")
    public final static String BENCH_ACTIVE = BENCH +".active";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player teleport to piano in GUI")
    public final static String TELEPORT = PIANO +".teleport";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player is able to click at the piano keys")
    public final static String DETECT_KEY = PIANO +".detect-key";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can use desktop-client")
    public final static String DESKTOP_CLIENT = PIANO +".desktop-client";



    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "opens midi player in gui",group = "midi-player",isParent = true)
    public final static String MIDI_PLAYER = "midi-player";


    @PermissionGroup(group = "midi-player")
    @PermissionProperty(description = "can play/stop songs ")
    public final static String PLAY =  MIDI_PLAYER+".play";

    @PermissionGroup(group = "midi-player")
    @PermissionProperty(description = "can set current song, load song, delete song, skip")
    public final static String CHANGE_SONG =  MIDI_PLAYER+".song";

    @PermissionGroup(group = "midi-player")
    @PermissionProperty(description = "change midi player type")
    public final static String CHANGE_TYPE =  MIDI_PLAYER+".type";

    @PermissionGroup(group = "midi-player")
    @PermissionProperty(description = "change speed of midi song")
    public final static String CHANGE_SPEED =  MIDI_PLAYER+".speed";

}
