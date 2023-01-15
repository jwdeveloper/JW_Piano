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

package jw.piano.api.data.config;

import jw.fluent.api.files.implementation.yaml_reader.api.annotations.YamlProperty;
import lombok.Data;

@Data
public class SoundConfig {
    @YamlProperty(name = "name")
    private String name;

    @YamlProperty(name = "namespace", description = """
            Name of the folder that sounds are save in resourcepack
                    """)
    private String namespace;

    @YamlProperty(name = "sound-category", description = """
            Define sound category from minecraft settings that sound will play in.
             Allowed categories [AMBIENT, BLOCKS, HOSTILE, MASTER, MUSIC, NEUTRAL, RECORDS, VOICE, WEATHER]
            """)
    private String soundCategory;
}
