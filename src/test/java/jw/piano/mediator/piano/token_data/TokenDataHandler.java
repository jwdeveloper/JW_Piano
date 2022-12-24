package jw.piano.mediator.piano.token_data;

import be.seeseemelk.mockbukkit.ServerMock;
import jw.fluent.api.utilites.SpigotMock;
import jw.fluent.plugin.implementation.modules.websocket.api.FluentWebsocket;
import jw.piano.api.data.models.PianoData;
import jw.piano.core.mediator.piano.token_data.TokenData;
import jw.piano.core.mediator.piano.token_data.TokenDataGeneratorHandler;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TokenDataHandler {

    private TokenDataGeneratorHandler sut;
    private FluentWebsocket websocketMock;
    private static ServerMock serverMock;

    @Before
    public void setUp() {

        serverMock = SpigotMock.getInstance().getServerMock();;
        websocketMock = mock(FluentWebsocket.class);
        when(websocketMock.getPort()).thenReturn(2000);
        when(websocketMock.getServerIp()).thenReturn("craftserver.com");
        sut = new TokenDataGeneratorHandler(websocketMock);
    }

    @Test
    public void handle_success()
    {
        //Arrange
        var player = serverMock.addPlayer();
        var pianoData = new PianoData();
        var pianoId = UUID.randomUUID();
        pianoData.setUuid(pianoId);
        var request = new TokenData.Request( pianoData);

        //Act
        var result = sut.handle(request);

       //Assert
        assertNotNull(result);
        assertNotEquals(0,result.getUrl().length());
    }

    @Test
    public void handle_fail()
    {
        //Arrange
        var request = new TokenData.Request( null);

        //Act
        var result = sut.handle(request);

        //Assert
        assertNull(result);
    }
}