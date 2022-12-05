package jw.piano.api.data;

import jw.fluent.api.spigot.permissions.api.annotations.PermissionGroup;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionProperty;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.HashMap;

public class PianoPermission
{
    public final static String PIANO = "piano";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player create piano")
    public final static String CREATE = PIANO +".create";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player remove piano")
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
    @PermissionProperty(description = "player set piano particles in GUI")
    public final static String EFFECTS = PIANO +".effects";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player enable/disable piano pedal in GUI")
    public final static String PEDAl = PIANO +".pedal";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player enable/disable piano bench in GUI")
    public final static String BENCH = PIANO +".bench";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player teleport to piano in GUI")
    public final static String TELEPORT = PIANO +".teleport";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player is able to click at the piano keys")
    public final static String DETECT_KEY = PIANO +".detect-key";

    @PermissionGroup(group = "plugin")
    @PermissionProperty(description = "player can use desktop-client")
    public final static String DESKTOP_CLIENT = PIANO +".desktop-client";
}
