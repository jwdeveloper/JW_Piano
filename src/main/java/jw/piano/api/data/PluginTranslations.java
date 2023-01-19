package jw.piano.api.data;

public class PluginTranslations
{
    public static class GENERAL
    {

        // Piano info
        public static final String INFO = "general.info";
    }

    public static class GUI
    {

        public static class BASE
        {

            public static class INSERT
            {

                // Create new piano
                public static final String DESC = "gui.base.insert.desc";
            }

            public static class DELETE
            {

                // Remove a piano
                public static final String DESC = "gui.base.delete.desc";

                // Unable to remove piano
                public static final String ERROR = "gui.base.delete.error";
            }

            public static class ACTIVE
            {

                // Active
                public static final String TITLE = "gui.base.active.title";
            }
        }

        public static class PIANO_LIST
        {

            // Piano list
            public static final String TITLE = "gui.piano-list.title";

            public static class RESOURCEPACK
            {

                // Get Resourcepack
                public static final String TITLE = "gui.piano-list.resourcepack.title";

                // Click here to get current version of resourcepack
                public static final String DESC = "gui.piano-list.resourcepack.desc";

                public static class CLICK
                {

                    // Download resourcepack to minecraft
                    public static final String LEFT = "gui.piano-list.resourcepack.click.left";

                    // Send resourcepack link
                    public static final String RIGHT = "gui.piano-list.resourcepack.click.right";
                }
            }

            public static class CLIENT_APP
            {

                // Get client app
                public static final String TITLE = "gui.piano-list.client-app.title";

                // Click here to get current version of client app
                public static final String DESC = "gui.piano-list.client-app.desc";

                // Click here to download desktop app
                public static final String MESSAGE = "gui.piano-list.client-app.message";
            }

            public static class CLICK
            {

                // Unable to find piano
                public static final String ERROR = "gui.piano-list.click.error";
            }
        }

        public static class PIANO
        {

            public static class VOLUME
            {

                // Loudness
                public static final String TITLE = "gui.piano.volume.title";
            }

            public static class TELEPORT
            {

                // Teleport
                public static final String TITLE = "gui.piano.teleport.title";
            }

            public static class EFFECT
            {

                // Effect
                public static final String TITLE = "gui.piano.effect.title";
            }

            public static class SKIN
            {

                // Skin
                public static final String TITLE = "gui.piano.skin.title";
            }

            public static class KEYBOARD
            {

                // Piano keyboard
                public static final String TITLE = "gui.piano.keyboard.title";

                // Change colors for Piano keys
                public static final String DESC = "gui.piano.keyboard.desc";
            }

            public static class RENAME
            {

                // Set name
                public static final String TITLE = "gui.piano.rename.title";

                // Set custom piano's name
                public static final String DESC = "gui.piano.rename.desc";

                // Write new piano's name on the chat
                public static final String MESSAGE_1 = "gui.piano.rename.message-1";
            }

            public static class TOKEN
            {

                // Click to copy token
                public static final String TITLE = "gui.piano.token.title";

                // Connect your real piano or play MIDI files with client app
                public static final String DESC = "gui.piano.token.desc";

                // Copy and paste token to Piano client app
                public static final String MESSAGE_1 = "gui.piano.token.message-1";

                // Value will be copied after click
                public static final String MESSAGE_2 = "gui.piano.token.message-2";

                // Click to copy token
                public static final String CLICK_TO_COPY = "gui.piano.token.click-to-copy";

                // Can't generate link, unknown error
                public static final String ERROR = "gui.piano.token.error";
            }

            public static class PEDAL_ACTIVE
            {

                // Press pedal on 'f' click
                public static final String TITLE = "gui.piano.pedal-active.title";
            }

            public static class PIANIST_ACTIVE
            {

                // Pianist
                public static final String TITLE = "gui.piano.pianist-active.title";
            }

            public static class BENCH_ACTIVE
            {

                // Bench active
                public static final String TITLE = "gui.piano.bench-active.title";
            }

            public static class DESKTOP_CLIENT_ACTIVE
            {

                // Desktop app access
                public static final String TITLE = "gui.piano.desktop-client-active.title";

                // Desktop app is disabled for this piano
                public static final String DISABLED = "gui.piano.desktop-client-active.disabled";
            }

            public static class DETECT_KEY_ACTIVE
            {

                // Pressing piano keys
                public static final String TITLE = "gui.piano.detect-key-active.title";
            }

            public static class SOUNDS
            {

                // Sounds
                public static final String TITLE = "gui.piano.sounds.title";
            }

            public static class PIANO_OPTIONS
            {

                // Piano options
                public static final String TITLE = "gui.piano.piano-options.title";
            }

            public static class CLEAR
            {

                // Refresh piano
                public static final String TITLE = "gui.piano.clear.title";

                // Might be helpful when server has been suddenly shut down
                public static final String MESSAGE_1 = "gui.piano.clear.message-1";

                // and old piano model has not been properly removed.
                public static final String MESSAGE_2 = "gui.piano.clear.message-2";

                // Try also use /reload to clear all pianos
                public static final String MESSAGE_3 = "gui.piano.clear.message-3";

                // Piano has refreshed
                public static final String MESSAGE_CLEAR = "gui.piano.clear.message-clear";
            }

            public static class MIDI_PLAYER
            {

                // Midi player
                public static final String TITLE = "gui.piano.midi-player.title";

                // Play MIDI files on this piano. Files should be located in plugins/JW_Piano/midi
                public static final String DESC = "gui.piano.midi-player.desc";
            }

            public static class BENCH
            {

                // Bench
                public static final String TITLE = "gui.piano.bench.title";
            }

            public static class COLOR
            {

                // Color
                public static final String TITLE = "gui.piano.color.title";
            }
        }

        public static class MIDI_PLAYER
        {

            // MIDI player
            public static final String TITLE = "gui.midi-player.title";

            public static class SPEED
            {

                // Speed
                public static final String TITLE = "gui.midi-player.speed.title";
            }

            public static class STATE
            {

                // MIDI Player state
                public static final String TITLE = "gui.midi-player.state.title";

                // Stopped
                public static final String STOP = "gui.midi-player.state.stop";

                // Playing
                public static final String PLAY = "gui.midi-player.state.play";

                public static class CLICK
                {

                    // Change MIDI Player state
                    public static final String LEFT = "gui.midi-player.state.click.left";
                }
            }

            public static class SONG
            {

                // Place MIDI song
                public static final String TITLE = "gui.midi-player.song.title";

                public static class CLICK
                {

                    // Select MIDI
                    public static final String LEFT = "gui.midi-player.song.click.left";

                    // Remove MIDI
                    public static final String RIGHT = "gui.midi-player.song.click.right";

                    // Set song as current
                    public static final String SHIFT = "gui.midi-player.song.click.shift";
                }
            }

            public static class NEXT
            {

                // Next song
                public static final String TITLE = "gui.midi-player.next.title";
            }

            public static class PREVIOUS
            {

                // Previous song
                public static final String TITLE = "gui.midi-player.previous.title";
            }

            public static class MODE
            {

                // Mode
                public static final String TITLE = "gui.midi-player.mode.title";

                public static class RANDOM
                {

                    // Random
                    public static final String TITLE = "gui.midi-player.mode.random.title";

                    // Songs will be played in random order
                    public static final String DESC = "gui.midi-player.mode.random.desc";
                }

                public static class LOOP
                {

                    // Loop
                    public static final String TITLE = "gui.midi-player.mode.loop.title";

                    // Current song will be played forever in loop
                    public static final String DESC = "gui.midi-player.mode.loop.desc";
                }

                public static class IN_ORDER
                {

                    // In order
                    public static final String TITLE = "gui.midi-player.mode.in-order.title";

                    // Songs will be played one after another
                    public static final String DESC = "gui.midi-player.mode.in-order.desc";
                }
            }
        }

        public static class BENCH
        {

            // Bench settings
            public static final String TITLE = "gui.bench.title";

            public static class RESET
            {

                // Reset position
                public static final String TITLE = "gui.bench.reset.title";

                // Teleport bench to its default location
                public static final String DESC = "gui.bench.reset.desc";
            }

            public static class MOVE
            {

                // Move
                public static final String TITLE = "gui.bench.move.title";

                public static class AXIS
                {

                    // right / left
                    public static final String X = "gui.bench.move.axis.x";

                    // up / down
                    public static final String Y = "gui.bench.move.axis.y";

                    // forward / backward
                    public static final String Z = "gui.bench.move.axis.z";
                }

                public static class DESC
                {

                    // Default location might not fit to you.
                    public static final String MESSAGE_1 = "gui.bench.move.desc.message-1";

                    // But it can edit by mouse scrolling
                    public static final String MESSAGE_2 = "gui.bench.move.desc.message-2";
                }

                public static class CLICK
                {

                    // Edit position
                    public static final String SHIFT = "gui.bench.move.click.shift";
                }
            }
        }

        public static class KEYBOARD
        {

            // Keyboard Settings
            public static final String TITLE = "gui.keyboard.title";

            public static class CHAT
            {

                // Enter MIDI track number to chat
                public static final String INFO = "gui.keyboard.chat.info";

                // Invalid value, only number allowed
                public static final String ERROR = "gui.keyboard.chat.error";
            }

            public static class CLICK
            {

                // Change color
                public static final String LEFT = "gui.keyboard.click.left";

                // Change MIDI track number
                public static final String SHIFT = "gui.keyboard.click.shift";
            }

            public static class DEFAULT_COLOR
            {

                // Default press color
                public static final String TITLE = "gui.keyboard.default-color.title";
            }

            public static class MIDI_TRACK
            {

                // Add MIDI track
                public static final String TITLE = "gui.keyboard.midi-track.title";

                public static class DESC
                {

                    // Mostly MIDI has 2 tracks
                    public static final String LINE_1 = "gui.keyboard.midi-track.desc.line-1";

                    // 1 -> for the right hand
                    public static final String LINE_2 = "gui.keyboard.midi-track.desc.line-2";

                    // 2 -> for the left hand
                    public static final String LINE_3 = "gui.keyboard.midi-track.desc.line-3";

                    // but you can add more of them
                    public static final String LINE_4 = "gui.keyboard.midi-track.desc.line-4";
                }

                public static class CLICK
                {

                    // Add new MIDI track
                    public static final String LEFT = "gui.keyboard.midi-track.click.left";
                }
            }
        }
    }

    public static class SKINS
    {

        // none
        public static final String NONE = "skins.none";

        // grand piano
        public static final String GRAND_PIANO = "skins.grand-piano";

        // upright piano
        public static final String UPRIGHT_PIANO = "skins.upright-piano";

        // electric piano
        public static final String ELECTRIC_PIANO = "skins.electric-piano";

        // grand piano closed
        public static final String GRAND_PIANO_CLOSED = "skins.grand-piano-closed";
    }

    public static class PIANO
    {

        public static class CREATE
        {

            // Can't add more pianos on the server
            public static final String ERROR_TOO_MUCH = "piano.create.error-too-much";

            // Unable to add new piano
            public static final String ERROR = "piano.create.error";
        }
    }

    public static class COMMANDS
    {

        public static class PIANO
        {

            // opens GUI where you can Create/Edit/Delete pianos
            public static final String DESC = "commands.piano.desc";
        }
    }
}
