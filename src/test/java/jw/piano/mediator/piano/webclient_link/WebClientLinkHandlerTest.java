package jw.piano.mediator.piano.webclient_link;

import be.seeseemelk.mockbukkit.ServerMock;
import jw.SpigotMock;
import jw.fluent.plugin.implementation.modules.websocket.api.FluentWebsocket;
import jw.piano.data.models.PianoData;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebClientLinkHandlerTest {

    private WebClientLinkHandler sut;
    private FluentWebsocket websocketMock;
    private static ServerMock serverMock;

    @Before
    public void setUp() {

        serverMock = SpigotMock.getInstance().getServerMock();;
        websocketMock = mock(FluentWebsocket.class);
        when(websocketMock.getPort()).thenReturn(2000);
        when(websocketMock.getServerIp()).thenReturn("craftserver.com");
        sut = new WebClientLinkHandler(websocketMock);
    }

    @Test
    public void handle_success()
    {
        //Arrange
        var player = serverMock.addPlayer();
        var pianoData = new PianoData();
        var pianoId = UUID.randomUUID();
        pianoData.setUuid(pianoId);
        var request = new WebClientLink.Request(player, pianoData);

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
        var request = new WebClientLink.Request(null, null);

        //Act
        var result = sut.handle(request);

        //Assert
        assertNull(result);
    }
}