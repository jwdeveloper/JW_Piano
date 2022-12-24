package jw.piano.mediator.piano.create;


import be.seeseemelk.mockbukkit.entity.PlayerMock;
import jw.fluent.api.utilites.SpigotMock;
import jw.piano.api.data.models.PianoData;
import jw.piano.api.piano.Piano;
import jw.piano.core.mediator.piano.create.CreatePiano;
import jw.piano.core.mediator.piano.create.CreatePianoHandler;
import jw.piano.core.services.PianoService;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreatePianoHandlerTest {
    private CreatePianoHandler sut;
    private PianoService pianoServiceMock;
    private PlayerMock playerMock;

    @Before
    public void setUp() {
        playerMock = SpigotMock.getInstance().getPlayer();
        pianoServiceMock = mock(PianoService.class);
        sut = new CreatePianoHandler(pianoServiceMock);
    }

    @Test
    public void handle_success() {
        //Arrange
        var mockPiano = mock(Piano.class);
        when(pianoServiceMock.canCreate()).thenReturn(true);
        when(pianoServiceMock.create(any(PianoData.class))).thenReturn(Optional.of(mockPiano));

        //Act
        var request = new CreatePiano.Request(playerMock);
        var result = sut.handle(request);

        //Assert
        assertTrue(result.created());
    }

    @Test
    public void handle_fail_when_piano_limit_is_excited() {
        //Arrange
        when(pianoServiceMock.canCreate()).thenReturn(false);

        //Act
        var request = new CreatePiano.Request(playerMock);
        var result = sut.handle(request);

        //Assert
        assertFalse(result.created());
        assertEquals("Can't add more pianos on the server",result.message());
    }

    @Test
    public void handle_fail_when_piano_has_not_been_created() {
        //Arrange
        when(pianoServiceMock.canCreate()).thenReturn(true);
        when(pianoServiceMock.create(any(PianoData.class))).thenReturn(Optional.empty());

        //Act
        var request = new CreatePiano.Request(playerMock);
        var result = sut.handle(request);

        //Assert
        assertFalse(result.created());
        assertEquals("Unable to add new piano",result.message());
    }
}