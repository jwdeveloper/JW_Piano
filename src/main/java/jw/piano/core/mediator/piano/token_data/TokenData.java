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

package jw.piano.core.mediator.piano.token_data;

import jw.piano.api.data.models.PianoData;
import lombok.AllArgsConstructor;
import lombok.Data;

public class TokenData
{

    @Data
    @AllArgsConstructor
    public static class Dto
    {
        private String serverIP;

        private int port;

        private String a;

        private String b;
        //JavaScript is too stupid to properly translate Long from Json so I was forced to changed 'a' and 'b' to String

        private String pluginVersion;
    }

    @Data
    @AllArgsConstructor
    public static class Request
    {
        private PianoData pianoData;
    }

    @Data
    @AllArgsConstructor
    public static class Response
    {
        private String url;
    }

}
