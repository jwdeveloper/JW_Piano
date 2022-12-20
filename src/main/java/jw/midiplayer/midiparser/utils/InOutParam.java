/*
 * MidiPlayer a plugin that allows you to play custom music.
 * Copyright (c) 2014, SBPrime <https://github.com/SBPrime/>
 * Copyright (c) MidiPlayer contributors
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted free of charge provided that the following 
 * conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution,
 * 3. Redistributions of source code, with or without modification, in any form 
 *    other then free of charge is not allowed,
 * 4. Redistributions in binary form in any form other then free of charge is 
 *    not allowed.
 * 5. Any derived work based on or containing parts of this software must reproduce 
 *    the above copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided with the 
 *    derived work.
 * 6. The original author of the software is allowed to change the license 
 *    terms or the entire license of the software as he sees fit.
 * 7. The original author of the software is allowed to sublicense the software 
 *    or its parts using any license terms he sees fit.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package jw.midiplayer.midiparser.utils;

/**
 * This is a helper class that allows you to add output (and input) parameters
 * to java functions
 *
 * @author SBPrime
 * @param <T> The type of the parameter
 */
public class InOutParam<T> {

    /**
     * Initialize reference parameter (in and out value)
     *
     * @param <T> The type of the parameter
     * @param value The value of the parameter
     * @return An instance for this parameter
     */
    public static <T> InOutParam<T> Ref(T value) {
        return new InOutParam<T>(value);
    }

    /**
     * Initialize output param (out only)
     *
     * @param <T> The type of the parameter
     * @return An instance for this parameter
     */
    public static <T> InOutParam<T> Out() {
        return new InOutParam<T>();
    }

    /**
     * Is the value set
     */
    private boolean m_isSet;

    /**
     * The parameter value
     */
    private T m_value;

    /**
     * Create new instance of ref param
     *
     * @param value The value of the parameter
     */
    private InOutParam(T value) {
        m_value = value;
        m_isSet = true;
    }

    /**
     * Create new instance of out param
     */
    private InOutParam() {
        m_isSet = false;
    }

    /**
     * Get the parameter value
     *
     * @return The value of the parameter
     */
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

    /**
     * Is the value set
     *
     * @return Whether the parameter has a value or not
     */
    public boolean isSet() {
        return m_isSet;
    }
}
