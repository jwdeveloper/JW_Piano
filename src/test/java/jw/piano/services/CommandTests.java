/*
 * MIT License
 *
 * Copyright (c)  2023. jwdeveloper
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package jw.piano.services;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiBuilder;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class CommandTests
{
    private ServerMock serverMock;
    private PlayerMock playerMock;
    private MockPlugin pluginMock;

    @Before
    public void setup()
    {
        if(MockBukkit.isMocked())
        {
            return;
        }
         serverMock = MockBukkit.mock();
         playerMock = serverMock.addPlayer();
         pluginMock = MockBukkit.createMockPlugin();
    }

    @Test
    public void commandTest()
    {
        //Arrange
        var simpleCommand = new SimpleCommand();
        var command = mock(Command.class);
        var label = "label";
        var args = new String[2];
        args[0] = "hello";
        args[1] = "world";

        //Act
        var result = simpleCommand.onCommand(playerMock,command,label, args);

        //Assert
        Assert.assertTrue(result);
    }


    public class SimpleCommand implements CommandExecutor
    {

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

            if(args.length == 2)
            {
                return true;
            }

            return false;
        }

    }
}
