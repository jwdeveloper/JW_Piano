package jw;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.FluentApiBuilder;
import jw.fluent.plugin.implementation.modules.dependecy_injection.FluentInjectionImpl;
import lombok.Getter;

public class SpigotMock
{
    private static SpigotMock INSTANCE;

    @Getter
    private MockPlugin pluginMock;

    @Getter
    private ServerMock serverMock;

    @Getter
    private WorldMock worldMock;

    public PlayerMock getPlayer()
    {
      return  serverMock.addPlayer();
    }

    @Getter
    private FluentInjectionImpl injection;

    public SpigotMock()
    {
        if(MockBukkit.isMocked())
        {
            return;
        }
        serverMock = MockBukkit.mock();
        worldMock = serverMock.addSimpleWorld("world");
        serverMock.addWorld(worldMock);

        pluginMock = MockBukkit.createMockPlugin();
        FluentApiBuilder.create(pluginMock).build();
        injection = (FluentInjectionImpl)FluentApi.container();
    }

    public static SpigotMock getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new SpigotMock();
        }
        return INSTANCE;
    }
}
