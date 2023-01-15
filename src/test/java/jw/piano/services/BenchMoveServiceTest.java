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

import be.seeseemelk.mockbukkit.ServerMock;
import jw.piano.api.data.dto.BenchMove;
import jw.piano.api.piano.Bench;
import jw.piano.api.piano.Piano;
import org.bukkit.entity.Player;

import static org.junit.Assert.assertTrue;

public class BenchMoveServiceTest {
    private static Player player;
    private static ServerMock serverMock;
    private BenchMove benchMoveDto;
    private Piano pianoMock;
    private Bench benchMock;

  /*  @Before
    public void setUp() {

        player = SpigotMock.getInstance().getPlayer();
        serverMock = SpigotMock.getInstance().getServerMock();

        sut = new BenchMoveService();
        benchMoveDto = new BenchMoveDto();
        pianoMock = mock(Piano.class);
        benchMock = mock(Bench.class);

        benchMoveDto.setPiano(pianoMock);
        benchMoveDto.setPlayer(player);
        benchMoveDto.setOriginLocation(player.getLocation());
        benchMoveDto.setMoveType(MoveGameObjectAxis.X);
    }

    @Test
    public void register_success() {
        //arrange
        when(pianoMock.getBench()).thenReturn(Optional.of(benchMock));

        //act
        var result = sut.register(player.getUniqueId(), benchMoveDto);

        //assert
        assertTrue(result);
    }

    @Test
    public void register_fail() {
        //arrange
        AtomicBoolean hasBeenCalled = new AtomicBoolean(false);
        benchMoveDto.setOnCanceled((e) ->
        {
            hasBeenCalled.set(true);
        });
        when(pianoMock.getBench()).thenReturn(Optional.empty());

        //act
        var result = sut.register(player.getUniqueId(), benchMoveDto);

        //assert
        assertFalse(result);
        assertTrue(hasBeenCalled.get());
    }


    @Test
    public void move_success_increase() {
        //arrange
        var initialLocation = player.getLocation();
        when(pianoMock.getBench()).thenReturn(Optional.of(benchMock));
        when(benchMock.getLocation()).thenReturn(initialLocation);
        // act
        sut.register(player.getUniqueId(), benchMoveDto);
        var result = sut.move(player, true);
        var newLocation = result.get();
        // assert
        assertFalse(result.isEmpty());
        assertTrue(initialLocation.getX() < newLocation.getX());
    }

    @Test
    public void move_success_decrease() {
        //arrange
        var initialLocation = player.getLocation();
        when(pianoMock.getBench()).thenReturn(Optional.of(benchMock));
        when(benchMock.getLocation()).thenReturn(initialLocation);
        // act
        sut.register(player.getUniqueId(), benchMoveDto);
        var result = sut.move(player, false);
        var newLocation = result.get();
        // assert
        assertFalse(result.isEmpty());
        assertTrue(initialLocation.getX() > newLocation.getX());
    }

    @Test
    public void move_fail_when_player_not_registered() {
        //arrange
        // act
        var result = sut.move(player, true);
        // assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void move_fail_when_bench_is_invalid() {
        //arrange
        var hasBeenCalled = new AtomicBoolean(false);
        benchMoveDto.setOnCanceled((e) ->
        {
            hasBeenCalled.set(true);
        });
        // act
        sut.register(player.getUniqueId(), benchMoveDto);
        var result = sut.move(player, false);

        // assert
        assertTrue(result.isEmpty());
        assertTrue(hasBeenCalled.get());
    }

    @Test
    public void accept_success() {
        //arrange
        var hasBeenCalled = new AtomicBoolean(false);
        benchMoveDto.setOnAccept(unused ->
        {
            hasBeenCalled.set(true);
        });
        when(pianoMock.getBench()).thenReturn(Optional.of(benchMock));
        when(benchMock.getLocation()).thenReturn(player.getLocation());
        // act
        sut.register(player.getUniqueId(), benchMoveDto);
        var result = sut.accept(player);

        // assert
        assertTrue(result);
        assertTrue(hasBeenCalled.get());
        verify(benchMock, times(1)).updateHitbox();
    }

    @Test
    public void cancel_success() {
        //arrange
        var hasBeenCalled = new AtomicBoolean(false);
        benchMoveDto.setOnCanceled(unused ->
        {
              hasBeenCalled.set(true);
        });
        when(pianoMock.getBench()).thenReturn(Optional.of(benchMock));
        when(benchMock.getLocation()).thenReturn(player.getLocation());
        // act
        sut.register(player.getUniqueId(), benchMoveDto);
        var result = sut.cancel(player);

        // assert
        assertTrue(result);
        assertTrue(hasBeenCalled.get());
        verify(benchMock, times(1)).setLocation(benchMoveDto.getOriginLocation());
    }*/
}