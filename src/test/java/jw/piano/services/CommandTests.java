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
