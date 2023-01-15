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

package jw.piano.mediator.piano.token_data;

import be.seeseemelk.mockbukkit.ServerMock;
import jw.fluent.api.utilites.FluentApiMock;
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

        serverMock = FluentApiMock.getInstance().getServerMock();;
        websocketMock = mock(FluentWebsocket.class);
        when(websocketMock.getPort()).thenReturn(2000);
        when(websocketMock.getServerIp()).thenReturn("craftserver.com");
        sut = new TokenDataGeneratorHandler(websocketMock, FluentApiMock.getInstance().getPluginMock());
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