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
package jw.piano.api.midiplayer.commands;

import jw.piano.api.midiplayer.MidiPlayerMain;
import jw.piano.api.midiplayer.configuration.ConfigProvider;
import jw.piano.api.midiplayer.midiparser.instruments.MapFileParser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.io.File;

/**
 * Reload configuration command
 * @author SBPrime
 */
public class ReloadCommand extends BaseCommand {

    private final MidiPlayerMain m_pluginMain;

    public ReloadCommand(MidiPlayerMain pluginMain) {
        m_pluginMain = pluginMain;
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String name, String[] args) {
        if (args != null && args.length > 0) {
            return false;
        }

        Player player = (cs instanceof Player) ? (Player) cs : null;

        m_pluginMain.reloadConfig();
        ReloadConfig(player);
        return true;
    }

    public boolean ReloadConfig(Player player) {
        if (!ConfigProvider.load(m_pluginMain)) {
            MidiPlayerMain.say(player, "Error loading config");
            return false;
        }



        if (!ReloadInstrumentMap(player)) {
            return false;
        }
        MidiPlayerMain.say(player, "Config loaded");
        return true;
    }

    private boolean ReloadInstrumentMap(Player player) {
        String instrumentName = ConfigProvider.getInstrumentMapFile();
        String drumName = ConfigProvider.getDrumMapFile();
        File instrumentFile = new File(ConfigProvider.getPluginFolder(), instrumentName);
        File drumFile = new File(ConfigProvider.getPluginFolder(), drumName);
        
        if (MapFileParser.loadMap(instrumentFile)) {
            MidiPlayerMain.say(player, "Instrument map file loaded.");
        } else {
            MidiPlayerMain.say(player, "Error loading instrument map file");
            if (!MapFileParser.loadDefaultMap()) {
                MidiPlayerMain.say(player, "Error loading default instrument map.");
                return false;
            } else {
                MidiPlayerMain.say(player, "Loaded default instrument map.");
            }
        }
        
        if (MapFileParser.loadDrumMap(drumFile)) {
            MidiPlayerMain.say(player, "Drum map file loaded.");
        } else {
            MidiPlayerMain.say(player, "Error loading drum map file");
            if (!MapFileParser.loadDefaultDrumMap()) {
                MidiPlayerMain.say(player, "Error loading default drum map.");
                return false;
            } else {
                MidiPlayerMain.say(player, "Loaded default drum map.");
            }
        }
        return true;
    }
}
