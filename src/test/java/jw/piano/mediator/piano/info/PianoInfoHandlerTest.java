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

package jw.piano.mediator.piano.info;

import be.seeseemelk.mockbukkit.entity.PlayerMock;
import jw.fluent.api.utilites.FluentApiMock;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.Piano;
import jw.piano.core.mediator.piano.info.PianoInfo;
import jw.piano.core.mediator.piano.info.PianoInfoHandler;
import jw.piano.core.services.PianoService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PianoInfoHandlerTest {

    private PianoInfoHandler sut;
    private PianoService pianoServiceMock;

    private PlayerMock playerMock;



    @Before
    public void setUp() {

        playerMock = FluentApiMock.getInstance().getPlayer();
        pianoServiceMock = mock(PianoService.class);
        sut = new PianoInfoHandler(pianoServiceMock, FluentApiMock.getInstance().getPluginMock());
    }

    @Test
    public void handle_success() {
        //Arrange
        var pianoId = UUID.randomUUID();
        var pianoName = "piano1";
        var pianoVolume = 10;
        var pianoSkin = 10;
        var pianoLocation = playerMock.getLocation();

        var pianoData = new PianoData();
        pianoData.setUuid(pianoId);
        pianoData.setName(pianoName);
        pianoData.setVolume(pianoVolume);
        pianoData.setLocation(pianoLocation);

        var pianoMock = mock(Piano.class);
        when(pianoMock.getPianoObserver().getPianoData()).thenReturn(pianoData);
        when(pianoServiceMock.find(pianoId)).thenReturn(Optional.of(pianoMock));

        //Act
        var request = new PianoInfo.Request(pianoId);
        var result = sut.handle(request);

        //Assert
        assertNotNull(request);
        assertEquals(pianoId.toString(),result.getId());
        assertEquals(String.valueOf(pianoSkin),result.getType());
        assertEquals(pianoName,result.getName());
        assertEquals(pianoVolume,result.getVolume());
        assertEquals(pianoLocation.toString(),result.getLocation());
    }

    @Test
    public void handle_fail_when_piano_has_not_been_found() {
        //Arrange
        var pianoId = UUID.randomUUID();
        when(pianoServiceMock.find(pianoId)).thenReturn(Optional.empty());

        //Act
        var request = new PianoInfo.Request(pianoId);
        var result = sut.handle(request);

        //Assert
        assertNull(result);
    }
}