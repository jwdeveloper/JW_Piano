package jw.piano.api.data;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

//@JW generated code source don't modify it

public class PluginModels{
    private static final Material MATERIAL = Material.LEATHER_HORSE_ARMOR;

    public static ResourceModel  BENCH = new ResourceModel(167072, "bench");

    public static ResourceModel  FLYINGNOTE = new ResourceModel(167073, "flyingnote");

    public static ResourceModel ICON = new ResourceModel(167074, "icon");

    public static ResourceModel PIANO_BLACK_KEY = new ResourceModel(167075, "piano black key");

    public static ResourceModel PIANO_BLACK_KEY_DOWN = new ResourceModel(167076, "piano black key down");

    public static ResourceModel PIANO_KEY = new ResourceModel(167077, "piano key");

    public static ResourceModel PIANO_KEY_DOWN = new ResourceModel(167078, "piano key down");

    public static ResourceModel PIANO_PEDAL = new ResourceModel(167079, "piano pedal");

    public static ResourceModel PIANO_PEDAL_DOWN = new ResourceModel(167080, "piano pedal down");

    public static ResourceModel PIANIST_BODY = new ResourceModel(167081, "pianist body");

    public static ResourceModel PIANIST_HANDS = new ResourceModel(167082, "pianist hands");

    public static ResourceModel PIANIST_HEAD = new ResourceModel(167083, "pianist head");

    public static ResourceModel ELECTRIC_PIANO = new ResourceModel(167084, "electric piano");

    public static ResourceModel GRAND_PIANO = new ResourceModel(167085, "grand piano");

    public static ResourceModel GRAND_PIANO_CLOSE = new ResourceModel(167086, "grand piano close");

    public static ResourceModel UP_RIGHT_PIANO_CLOSE = new ResourceModel(167087, "up right piano close");


        public record ResourceModel(int id, String name) {
    
                 public Material getMaterial() {
                     return MATERIAL;
                 }
    
                 public ItemStack toItemStack() {
                     return toItemStack(Color.WHITE);
                 }
                 public ItemStack toItemStack(Color color) {
                         var itemStack = new ItemStack(getMaterial());
                         var meta = itemStack.getItemMeta();
                         if(meta == null)
                          {
                             return itemStack;
                          }
                          meta.setDisplayName(name);
                          meta.setCustomModelData(id);
                          if(meta instanceof LeatherArmorMeta leatherArmorMeta)
                          {
                             leatherArmorMeta.setColor(color);
                          }
                          itemStack.setItemMeta(meta);
                          return itemStack;
                 }
             }

}
