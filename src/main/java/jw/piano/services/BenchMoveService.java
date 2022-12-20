package jw.piano.services;

import jw.fluent.api.desing_patterns.dependecy_injection.api.annotations.Injection;
import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.data.dto.BenchMoveDto;
import jw.piano.data.enums.MoveGameObjectAxis;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Injection
public class BenchMoveService {
    private final HashMap<UUID, BenchMoveDto> scrollingPlayers;
    private final float SPEED = 0.2f;

    public BenchMoveService() {
        scrollingPlayers = new HashMap<>();
    }

    public boolean register(UUID uuid, BenchMoveDto benchMoveDto) {
        if(!validateBench(benchMoveDto))
        {
            return false;
        }
        var bench = benchMoveDto.getPiano().getBench().get();
        benchMoveDto.setOriginLocation(bench.getLocation());
        scrollingPlayers.put(uuid, benchMoveDto);
        return true;
    }

    public void unregister(UUID uuid) {
        scrollingPlayers.remove(uuid);
    }

    public Optional<Location> move(Player player, boolean increase) {
        if (!validatePlayer(player)) {
            return Optional.empty();
        }
        var benchMoveDto = scrollingPlayers.get(player.getUniqueId());
        if(!validateBench(benchMoveDto))
        {
            return Optional.empty();
        }
        var bench = benchMoveDto.getPiano().getBench().get();

        var oldLocation = bench.getLocation();
        var location = createNewLocation(oldLocation,increase,benchMoveDto.getMoveType());
        bench.setLocation(location);
        return Optional.of(location);
    }

    public boolean accept(Player player) {
        if (!validatePlayer(player)) {
            return false;
        }
        var benchMoveDto = scrollingPlayers.get(player.getUniqueId());
        if(!validateBench(benchMoveDto))
        {
            return false;
        }
        unregister(player.getUniqueId());
        var piano = benchMoveDto.getPiano();
        var bench = piano.getBench().get();
        var offset  = bench.updateHitbox();
        piano.getPianoData().getBenchSettings().setOffset(offset);
        benchMoveDto.getOnAccept().accept(FluentApi.messages().chat().info().text("Location changed").toString());
        return true;
    }

    public boolean cancel(Player player) {
        if (!validatePlayer(player)) {
            return false;
        }
        var benchMoveDto = scrollingPlayers.get(player.getUniqueId());
        if(!validateBench(benchMoveDto))
        {
            return false;
        }
        unregister(player.getUniqueId());
        benchMoveDto.getPiano().getBench().get().setLocation(benchMoveDto.getOriginLocation());
        benchMoveDto.getOnCanceled().accept(FluentApi.messages().chat().info().text("Location canceled").toString());
        return true;
    }

    private Location createNewLocation(Location location, boolean increase, MoveGameObjectAxis moveGameType)
    {
        var grain = increase ?SPEED:-SPEED;
        var loc = location.clone();
        switch (moveGameType)
        {
            case X -> loc.add(grain,0,0);
            case Y -> loc.add(0,grain,0);
            case Z -> loc.add(0,0,grain);
        }
        return loc;
    }

    private boolean validatePlayer(Player player)
    {
        return scrollingPlayers.containsKey(player.getUniqueId());
    }

    private boolean validateBench(BenchMoveDto dto)
    {
        var optional = dto.getPiano().getBench();
        if (optional.isEmpty()) {
            dto.getOnCanceled().accept( FluentApi.messages().chat().error().text("Both piano and bench must be visible").toString());
            return false;
        }
        return true;
    }
}
