package jw.piano.spigot.gameobjects.sounds;

import net.minecraft.network.protocol.game.PacketPlayOutCustomSoundEffect;
import net.minecraft.resources.MinecraftKey;
import net.minecraft.server.network.PlayerConnection;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import net.minecraft.sounds.SoundCategory;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class SoundPlayer
{
    private static HashMap<Player, PlayerConnection> connectionDictionary = new HashMap<>();
    private static HashMap<Integer, MinecraftKey> sounds = new HashMap<>();
    private static PacketPlayOutCustomSoundEffect packet;
    public static void playSound(Player player, Location location, int id, float volume)  {
        if (!connectionDictionary.containsKey(player))
        {
            var con =  ((CraftPlayer) player).getHandle().b;
            connectionDictionary.put(player,con);
        }

        if (!sounds.containsKey(id))
        {
            var s=  SoundsMapper.getSound(id,false);
            var key = new MinecraftKey(s);
            sounds.put(id,key);
        }


        packet = new PacketPlayOutCustomSoundEffect(
                sounds.get(id),
                SoundCategory.a,
                new Vec3D(location.getBlockX(),location.getBlockY(),location.getBlockZ()),
                volume,
                1.0f);
        connectionDictionary.get(player).a(packet);
        }

    public static void playSound(Location location, int id, float volume)
    {
        for(var player : Bukkit.getOnlinePlayers())
        {
             playSound(player,player.getLocation().add(0,1,0),id,volume);
        }
    }
}
