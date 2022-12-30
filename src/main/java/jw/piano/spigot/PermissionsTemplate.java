package jw.piano.spigot;

import jw.fluent.api.spigot.permissions.api.annotations.PermissionGroup;
import jw.fluent.api.spigot.permissions.api.annotations.PermissionProperty;

@PermissionGroup(group = "jw-piano")
public class PermissionsTemplate
{
    @PermissionGroup(group = "gui")
    public static class GUI
    {
        @PermissionGroup(group = "piano-list")
        public static class PIANO_LIST
        {
            @PermissionProperty(description = "player can create piano")
            public static final String CREATE = "create";
            @PermissionProperty(description = "player can remove piano")
            public static final String REMOVE = "remove";
        }

        @PermissionGroup(group = "piano")
        public static class PIANO
        {
            @PermissionGroup(group = "settings")
            public static class SETTINGS
            {
                @PermissionProperty(description = "piano can be disabled but not removed")
                public static final String PIANO_ACTIVE = "piano-active";

                @PermissionProperty(description = "player can enable/disable clicking at the piano keys")
                public static final String KEYBOARD_PRESSING_ACTIVE = "keyboard-pressing-active";

                @PermissionProperty(description = "player can enable/disable pushing sustain pedal after 'f' press")
                public static final String PEDAL_PRESSING_ACTIVE = "pedal-pressing-active";

                @PermissionProperty(description = "piano will receiving data from desktop-app")
                public static final String DESKTOP_APP_ACTIVE = "desktop-app-active";
            }

            @PermissionProperty(description = "player can generate token for desktop app")
            public static final String GENERATE_TOKEN = "generate-token";

            @PermissionProperty(description = "player can teleport to piano")
            public static final String VOLUME = "volume";

            @PermissionProperty(description = "player can rename piano")
            public static final String RENAME = "rename";

            @PermissionProperty(description = "player can teleport to piano")
            public static final String TELEPORT = "teleport";

            @PermissionProperty(description = "player can change piano skin")
            public static final String SKIN = "skin";

            @PermissionProperty(description = "player can change piano particle effect")
            public static final String EFFECT = "effect";

            @PermissionProperty(description = "player can change piano sound")
            public static final String SOUND = "sound";

            @PermissionProperty(description = "player can refresh piano model")
            public static final String CLEAR = "clear";
        }

        @PermissionGroup(group = "bench")
        public static class BENCH
        {
            @PermissionProperty(description = "player can move bench around")
            public static final String MOVE = "move";

            @PermissionGroup(group = "settings")
            public static class SETTINGS
            {
                @PermissionProperty(description = "player can disable bench")
                public static final String ACTIVE = "active";
            }
        }

        @PermissionGroup(group = "midi-player")
        public static class MIDI_PLAYER
        {
            @PermissionProperty(description = "player can change speed of midi player")
            public static final String SPEED = "speed";

            @PermissionProperty(description = "MIDI player type")
            public static final String PLAYER_TYPE = "player-type";

            @PermissionProperty(description = "player can play next song")
            public static final String NEXT_SONG = "next-song";

            @PermissionProperty(description = "player can play previous song")
            public static final String PREVIOUS_SONG = "previous-song";

            @PermissionProperty(description = "player can play or stop midi player")
            public static final String PLAY_STOP = "play-stop";

            @PermissionProperty(description = "player can add song to MIDI player")
            public static final String SELECT_SONG = "select-song";

            @PermissionProperty(description = "player can remove song from MIDI player")
            public static final String REMOVE_SONG = "remove-song";
        }
    }

    @PermissionGroup(group = "commands")
    public static class COMMANDS
    {
        @PermissionProperty(description = "player can open piano list gui")
        public final static String PIANO = "piano";
    }

    @PermissionGroup(group = "piano")
    public static class PIANO
    {
        @PermissionGroup(group = "pedal")
        public static class PEDAL
        {
            @PermissionProperty(description = "player can push sustain pedal with 'f' press")
           public static final String USE = "use";
        }
        @PermissionGroup(group = "bench")
        public static class BENCH
        {
            @PermissionProperty(description = "player sit on the bench")
            public static final String USE = "use";
        }
        @PermissionGroup(group = "keyboard")
        public static class KEYBOARD
        {
            @PermissionProperty(description = "player click on the piano keys")
            public static final String USE = "use";
        }
    }
}
