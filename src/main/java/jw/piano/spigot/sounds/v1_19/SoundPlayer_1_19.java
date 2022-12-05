package jw.piano.spigot.sounds.v1_19;

import jw.fluent.plugin.implementation.FluentApi;
import jw.piano.gameobjects.utils.MappedSounds;
import jw.piano.spigot.sounds.NmsSoundPlayer;
import lombok.SneakyThrows;
import net.minecraft.network.protocol.game.PacketPlayOutCustomSoundEffect;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class SoundPlayer_1_19 implements NmsSoundPlayer {
    private final HashMap<Player, PlayerConnection> playerConnections = new HashMap<>();
    private final HashMap<Integer, MinecraftKey> sounds = new HashMap<>();
    private final HashMap<Integer, MinecraftKey> soundsWithPedal = new HashMap<>();
    private MinecraftKey sound;

    private void playSoundNMS(PacketPlayOutCustomSoundEffect packet) {
        for (final var player : Bukkit.getOnlinePlayers()) {
            if (!playerConnections.containsKey(player)) {
                try {
                    var connection = getConnection(player);
                    playerConnections.put(player, connection);
                } catch (Exception e) {
                    FluentApi.logger().error("Player " + e);
                    return;
                }
            }
            playerConnections.get(player).a(packet);
        }
    }

    private PlayerConnection getConnection(Player player) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        var method = player.getClass().getDeclaredMethod("getHandle");
        var handler = (EntityPlayer) method.invoke(player);
        return handler.b;
    }

    @SneakyThrows
    private PacketPlayOutCustomSoundEffect getPacket(Location location, int note, float volume, boolean pressed) {
        if (pressed) {
            if (!soundsWithPedal.containsKey(note)) {
                var s = MappedSounds.getSound(note, true);
                var key = new MinecraftKey(s);
                soundsWithPedal.put(note, key);
            }
            sound = soundsWithPedal.get(note);
        } else {
            if (!sounds.containsKey(note)) {
                var s = MappedSounds.getSound(note, false);
                var key = new MinecraftKey(s);
                sounds.put(note, key);
            }
            sound = sounds.get(note);
        }


        for(var c : PacketPlayOutCustomSoundEffect.class.getConstructors())
        {
            FluentApi.logger().success(c.getParameterCount()+" Params ");
        }

       return (PacketPlayOutCustomSoundEffect) PacketPlayOutCustomSoundEffect.class.getConstructors()[0].newInstance( sound,
                SoundCategory.a,
                new Vec3D(location.getBlockX(), location.getBlockY(), location.getBlockZ()),
                volume,
                1.0f,1.0f);
    }


    @Override
    public void play(Location location, int note, float volume, boolean pressed) {
        playSoundNMS(getPacket(location, note, volume, pressed));
    }

    @Override
    public void test() {

    }
}
