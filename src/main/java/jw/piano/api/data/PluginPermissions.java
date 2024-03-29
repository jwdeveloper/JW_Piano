/*
 * JW_PIANO  Copyright (C) 2023. by jwdeveloper
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to use, copy, modify, merge,
 *  and/or sell copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * The Software shall not be resold or distributed for commercial purposes without the
 * express written consent of the copyright holder.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 *
 *
 *
 */

package jw.piano.api.data;

public class PluginPermissions
{
    public static final String BASE = "jw-piano";

    public static class PIANO
    {
        public static final String BASE = "jw-piano.piano";

        public static class KEYBOARD
        {
            public static final String BASE = "jw-piano.piano.keyboard";
            public static final String USE = "jw-piano.piano.keyboard.use";
        }

        public static class BENCH
        {
            public static final String BASE = "jw-piano.piano.bench";
            public static final String USE = "jw-piano.piano.bench.use";
        }

        public static class PEDAL
        {
            public static final String BASE = "jw-piano.piano.pedal";
            public static final String USE = "jw-piano.piano.pedal.use";
        }
    }

    public static class COMMANDS
    {
        public static final String BASE = "jw-piano.commands";
        public static final String PIANO = "jw-piano.commands.piano";
    }

    public static class GUI
    {
        public static final String BASE = "jw-piano.gui";

        public static class MIDI_PLAYER
        {
            public static final String BASE = "jw-piano.gui.midi-player";
            public static final String SPEED = "jw-piano.gui.midi-player.speed";
            public static final String PLAYER_TYPE = "jw-piano.gui.midi-player.player-type";
            public static final String NEXT_SONG = "jw-piano.gui.midi-player.next-song";
            public static final String PREVIOUS_SONG = "jw-piano.gui.midi-player.previous-song";
            public static final String PLAY_STOP = "jw-piano.gui.midi-player.play-stop";
            public static final String SELECT_SONG = "jw-piano.gui.midi-player.select-song";
            public static final String REMOVE_SONG = "jw-piano.gui.midi-player.remove-song";
        }

        public static class BENCH
        {
            public static final String BASE = "jw-piano.gui.bench";
            public static final String MOVE = "jw-piano.gui.bench.move";

            public static class SETTINGS
            {
                public static final String BASE = "jw-piano.gui.bench.settings";
                public static final String ACTIVE = "jw-piano.gui.bench.settings.active";
            }
        }

        public static class PIANO
        {
            public static final String BASE = "jw-piano.gui.piano";
            public static final String GENERATE_TOKEN = "jw-piano.gui.piano.generate-token";
            public static final String VOLUME = "jw-piano.gui.piano.volume";
            public static final String RENAME = "jw-piano.gui.piano.rename";
            public static final String TELEPORT = "jw-piano.gui.piano.teleport";
            public static final String SKIN = "jw-piano.gui.piano.skin";
            public static final String EFFECT = "jw-piano.gui.piano.effect";
            public static final String SOUND = "jw-piano.gui.piano.sound";
            public static final String CLEAR = "jw-piano.gui.piano.clear";

            public static class SETTINGS
            {
                public static final String BASE = "jw-piano.gui.piano.settings";
                public static final String KEYBOARD_PRESSING_ACTIVE = "jw-piano.gui.piano.settings.keyboard-pressing-active";
                public static final String PEDAL_PRESSING_ACTIVE = "jw-piano.gui.piano.settings.pedal-pressing-active";
                public static final String DESKTOP_APP_ACTIVE = "jw-piano.gui.piano.settings.desktop-app-active";
                public static final String PIANIST_ACTIVE = "jw-piano.gui.piano.settings.pianist-active";
            }
        }

        public static class PIANO_LIST
        {
            public static final String BASE = "jw-piano.gui.piano-list";
            public static final String CREATE = "jw-piano.gui.piano-list.create";
            public static final String REMOVE = "jw-piano.gui.piano-list.remove";
        }
    }
}
