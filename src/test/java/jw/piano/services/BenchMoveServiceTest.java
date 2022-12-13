package jw.piano.services;

import be.seeseemelk.mockbukkit.ServerMock;
import jw.fluent.api.utilites.SpigotMock;
import jw.piano.data.dto.BenchMoveDto;
import jw.piano.data.enums.MoveGameObjectAxis;
import jw.piano.spigot.gameobjects.Piano;
import jw.piano.spigot.gameobjects.models.BenchGameObject;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BenchMoveServiceTest {
    private static Player player;
    private static ServerMock serverMock;
    private BenchMoveService sut;
    private BenchMoveDto benchMoveDto;
    private Piano pianoMock;
    private BenchGameObject benchMock;

    @Before
    public void setUp() {

        player = SpigotMock.getInstance().getPlayer();
        serverMock = SpigotMock.getInstance().getServerMock();

        sut = new BenchMoveService();
        benchMoveDto = new BenchMoveDto();
        pianoMock = mock(Piano.class);
        benchMock = mock(BenchGameObject.class);

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
        verify(benchMock, times(1)).updateHitBox();
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
    }
}