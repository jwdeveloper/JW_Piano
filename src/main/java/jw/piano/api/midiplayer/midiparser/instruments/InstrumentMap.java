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
package jw.piano.api.midiplayer.midiparser.instruments;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author prime
 */
public class InstrumentMap {
    /**
     * All known instruments map
     */
    private static final Map<Integer, Instrument> s_instruments = new HashMap<Integer, Instrument>();
    
    
    /**
     * The drum machine mapping
     */
    private static final Map<Integer, InstrumentEntry> s_drumMap = new HashMap<Integer, InstrumentEntry>();
        

    /**
     * Default/fallback instrument
     */
    private static Instrument s_defaultInstrument;
    
    
    /**
     * Default drum instrument
     */
    private static InstrumentEntry s_defaultDrum;
    
    static {
        InstrumentEntry instrument = new InstrumentEntry("note.harp", 1.0f);
        Map<OctaveDefinition, InstrumentEntry> octaves = new HashMap<OctaveDefinition, InstrumentEntry>();

        for (int i = 0; i < 11; i += 2) {
            octaves.put(new OctaveDefinition(i, i + 1), instrument);
        }

        s_defaultInstrument = new Instrument(octaves);        
        s_defaultDrum = new InstrumentEntry("note.bd", 1.0f);
    }

    
    /**
     * MTA access mutex
     */
    private static final Object s_mutex = new Object();

    
    /**
     * Get instrument for MIDI program
     * @param program The program to use
     * @return The instrument for this MIDI program
     */
    public static Instrument getInstrument(int program) {
        synchronized (s_mutex) {
            Instrument instrument = s_instruments.getOrDefault(program, s_defaultInstrument);

            return instrument;
        }
    }
    
    
    /**
     * Get the drum instrument
     * @param key The key to find this instrument
     * @return The selected drum instrument
     */
    public static InstrumentEntry getDrum(int key) {
        synchronized (s_mutex)
        {
            InstrumentEntry instrument = s_drumMap.getOrDefault(key, s_defaultDrum);
            
            return instrument;
        }
    }

    
    /**
     * Get default instrument
     * @return The default instrument
     */
    public static Instrument getDefault() {
        synchronized (s_mutex) {
            return s_defaultInstrument;
        }
    }

    /**
     * Set the instrument map
     *
     * @param instruments The instruments in the map
     * @param defaultInstrument The default instrument
     */
    public static void set(Map<Integer, Map<OctaveDefinition, InstrumentEntry>> instruments,
            Map<OctaveDefinition, InstrumentEntry> defaultInstrument) {
        synchronized (s_mutex) {
            s_instruments.clear();
            for (Map.Entry<Integer, Map<OctaveDefinition, InstrumentEntry>> entrySet : instruments.entrySet()) {
                s_instruments.put(entrySet.getKey(), new Instrument(entrySet.getValue()));
            }

            s_defaultInstrument = new Instrument(defaultInstrument);
        }
    }
    
    
    
    
    /**
     * Set the drum map
     *
     * @param drums The drum instruments in the drum map
     * @param defaultDrum The default drum instrument
     */
    public static void set(Map<Integer, InstrumentEntry> drums, InstrumentEntry defaultDrum) {
        synchronized (s_mutex) {
            s_drumMap.clear();
            for (Map.Entry<Integer, InstrumentEntry> entry : drums.entrySet())
            {
                s_drumMap.put(entry.getKey(), entry.getValue());
            }
            s_defaultDrum = defaultDrum;
        }
    }
}