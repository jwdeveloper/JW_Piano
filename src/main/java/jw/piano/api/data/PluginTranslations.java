package jw.piano.api.data;

public class PluginTranslations
{

    public static class GENERAL
    {
        public static final String INFO = "general.info";
    }

    public static class GUI
    {

        public static class BASE
        {

            public static class INSERT
            {
                public static final String DESC = "gui.base.insert.desc";
            }

            public static class DELETE
            {
                public static final String DESC = "gui.base.delete.desc";
                public static final String ERROR = "gui.base.delete.error";
            }
        }

        public static class PIANO_LIST
        {
            public static final String TITLE = "gui.piano-list.title";

            public static class RESOURCEPACK
            {
                public static final String TITLE = "gui.piano-list.resourcepack.title";
                public static final String DESC = "gui.piano-list.resourcepack.desc";
            }

            public static class CLIENT_APP
            {
                public static final String TITLE = "gui.piano-list.client-app.title";
                public static final String DESC = "gui.piano-list.client-app.desc";
                public static final String MESSAGE = "gui.piano-list.client-app.message";
            }

            public static class CLICK
            {
                public static final String ERROR = "gui.piano-list.click.error";
            }
        }

        public static class PIANO
        {

            public static class VOLUME
            {
                public static final String TITLE = "gui.piano.volume.title";
            }

            public static class TELEPORT
            {
                public static final String TITLE = "gui.piano.teleport.title";
            }

            public static class EFFECT
            {
                public static final String TITLE = "gui.piano.effect.title";
            }

            public static class SKIN
            {
                public static final String TITLE = "gui.piano.skin.title";
            }

            public static class RENAME
            {
                public static final String TITLE = "gui.piano.rename.title";
                public static final String DESC = "gui.piano.rename.desc";
                public static final String MESSAGE_1 = "gui.piano.rename.message-1";
            }

            public static class TOKEN
            {
                public static final String TITLE = "gui.piano.token.title";
                public static final String DESC = "gui.piano.token.desc";
                public static final String MESSAGE_1 = "gui.piano.token.message-1";
                public static final String MESSAGE_2 = "gui.piano.token.message-2";
                public static final String CLICK_TO_COPY = "gui.piano.token.click-to-copy";
                public static final String ERROR = "gui.piano.token.error";
            }

            public static class PIANO_ACTIVE
            {
                public static final String TITLE = "gui.piano.piano-active.title";
            }

            public static class PEDAL_ACTIVE
            {
                public static final String TITLE = "gui.piano.pedal-active.title";
            }

            public static class BENCH_ACTIVE
            {
                public static final String TITLE = "gui.piano.bench-active.title";
            }

            public static class DESKTOP_CLIENT_ACTIVE
            {
                public static final String TITLE = "gui.piano.desktop-client-active.title";
                public static final String DISABLED = "gui.piano.desktop-client-active.disabled";
            }

            public static class DETECT_KEY_ACTIVE
            {
                public static final String TITLE = "gui.piano.detect-key-active.title";
            }

            public static class SOUNDS
            {
                public static final String TITLE = "gui.piano.sounds.title";
            }

            public static class PIANO_OPTIONS
            {
                public static final String TITLE = "gui.piano.piano-options.title";
            }

            public static class CLEAR
            {
                public static final String TITLE = "gui.piano.clear.title";
                public static final String MESSAGE_1 = "gui.piano.clear.message-1";
                public static final String MESSAGE_2 = "gui.piano.clear.message-2";
                public static final String MESSAGE_3 = "gui.piano.clear.message-3";
                public static final String MESSAGE_CLEAR = "gui.piano.clear.message-clear";
            }

            public static class MIDI_PLAYER
            {
                public static final String TITLE = "gui.piano.midi-player.title";
                public static final String DESC = "gui.piano.midi-player.desc";
            }

            public static class BENCH
            {
                public static final String TITLE = "gui.piano.bench.title";
            }
        }

        public static class MIDI_PLAYER
        {
            public static final String TITLE = "gui.midi-player.title";

            public static class SPEED
            {
                public static final String TITLE = "gui.midi-player.speed.title";
                public static final String DESC = "gui.midi-player.speed.desc";
            }

            public static class STATE
            {
                public static final String TITLE = "gui.midi-player.state.title";
                public static final String STOP = "gui.midi-player.state.stop";
                public static final String PLAY = "gui.midi-player.state.play";
            }

            public static class SONG
            {
                public static final String TITLE = "gui.midi-player.song.title";

                public static class CLICK
                {
                    public static final String LEFT = "gui.midi-player.song.click.left";
                    public static final String RIGHT = "gui.midi-player.song.click.right";
                    public static final String SHIFT = "gui.midi-player.song.click.shift";
                }
            }

            public static class NEXT
            {
                public static final String TITLE = "gui.midi-player.next.title";
            }

            public static class PREVIOUS
            {
                public static final String TITLE = "gui.midi-player.previous.title";
            }

            public static class MODE
            {
                public static final String TITLE = "gui.midi-player.mode.title";

                public static class RANDOM
                {
                    public static final String TITLE = "gui.midi-player.mode.random.title";
                    public static final String DESC = "gui.midi-player.mode.random.desc";
                }

                public static class LOOP
                {
                    public static final String TITLE = "gui.midi-player.mode.loop.title";
                    public static final String DESC = "gui.midi-player.mode.loop.desc";
                }

                public static class IN_ORDER
                {
                    public static final String TITLE = "gui.midi-player.mode.in-order.title";
                    public static final String DESC = "gui.midi-player.mode.in-order.desc";
                }
            }
        }

        public static class BENCH
        {
            public static final String TITLE = "gui.bench.title";

            public static class RESET
            {
                public static final String TITLE = "gui.bench.reset.title";
                public static final String DESC = "gui.bench.reset.desc";
            }

            public static class MOVE
            {
                public static final String TITLE = "gui.bench.move.title";

                public static class AXIS
                {
                    public static final String X = "gui.bench.move.axis.x";
                    public static final String Y = "gui.bench.move.axis.y";
                    public static final String Z = "gui.bench.move.axis.z";
                }

                public static class DESC
                {
                    public static final String MESSAGE_1 = "gui.bench.move.desc.message-1";
                    public static final String MESSAGE_2 = "gui.bench.move.desc.message-2";
                }

                public static class CLICK
                {
                    public static final String SHIFT = "gui.bench.move.click.shift";
                }
            }
        }
    }

    public static class SKINS
    {
        public static final String NONE = "skins.none";
        public static final String GRAND_PIANO = "skins.grand-piano";
        public static final String UPRIGHT_PIANO = "skins.upright-piano";
        public static final String ELECTRIC_PIANO = "skins.electric-piano";
    }

    public static class PIANO
    {

        public static class CREATE
        {
            public static final String ERROR_TOO_MUCH = "piano.create.error-too-much";
            public static final String ERROR = "piano.create.error";
        }
    }

    public static class COMMANDS
    {

        public static class PIANO
        {
            public static final String DESC = "commands.piano.desc";
        }
    }
}
