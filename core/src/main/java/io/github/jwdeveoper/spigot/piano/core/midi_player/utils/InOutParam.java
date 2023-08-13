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
package io.github.jwdeveoper.spigot.piano.core.midi_player.utils;


public class InOutParam<T> {


    public static <T> InOutParam<T> Ref(T value) {
        return new InOutParam<T>(value);
    }


    public static <T> InOutParam<T> Out() {
        return new InOutParam<T>();
    }


    private boolean m_isSet;


    private T m_value;


    private InOutParam(T value) {
        m_value = value;
        m_isSet = true;
    }

    private InOutParam() {
        m_isSet = false;
    }


    public T getValue() {
        if (m_isSet) {
            return m_value;
        }

        throw new IllegalStateException("Output parameter not set");
    }

    public void setValue(T value) {
        m_isSet = true;
        m_value = value;
    }


    public boolean isSet() {
        return m_isSet;
    }
}
