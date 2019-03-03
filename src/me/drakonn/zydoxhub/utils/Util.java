package me.drakonn.zydoxhub.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.*;

public class Util {

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> color(List<String> list) {
        List<String> colored = new ArrayList();
        Iterator var3 = list.iterator();

        while(var3.hasNext()) {
            String s = (String)var3.next();
            colored.add(color(s));
        }

        return colored;
    }

    public static Material getMaterial(String s) {
        s = s.toUpperCase().replace(" ", "_");
        if (s.contains(":")) {
            s = s.split(":")[0];
        }

        return Material.getMaterial(s) != null ? Material.getMaterial(s) : (Material.matchMaterial(s) != null ? Material.matchMaterial(s) : (Material.valueOf(s) != null ? Material.valueOf(s) : (isInt(s) ? Material.getMaterial(Integer.parseInt(s)) : Material.PAPER)));
    }


    public static class EnchantGlow extends EnchantmentWrapper {
        private static Enchantment glow = null;
        private final String name = "Glow";

        public static ItemStack addGlow(ItemStack itemstack) {
            itemstack.addEnchantment(getGlow(), 1);
            return itemstack;
        }

        public static Enchantment getGlow() {
            if (glow != null) {
                return glow;
            } else {
                Field field = null;

                try {
                    field = Enchantment.class.getDeclaredField("acceptingNew");
                } catch (SecurityException | NoSuchFieldException var4) {
                    var4.printStackTrace();
                    return glow;
                }

                field.setAccessible(true);

                try {
                    field.set((Object)null, true);
                } catch (IllegalAccessException | IllegalArgumentException var3) {
                    var3.printStackTrace();
                }

                try {
                    glow = new EnchantGlow(Enchantment.values().length + 100);
                } catch (Exception var2) {
                    glow = Enchantment.getByName("Glow");
                }

                if (Enchantment.getByName("Glow") == null) {
                    Enchantment.registerEnchantment(glow);
                }

                return glow;
            }
        }

        public String getName() {
            return this.name;
        }

        public Enchantment getEnchantment() {
            return Enchantment.getByName("Glow");
        }

        public int getMaxLevel() {
            return 1;
        }

        public int getStartLevel() {
            return 1;
        }

        public EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.ALL;
        }

        public boolean canEnchantItem(ItemStack item) {
            return true;
        }

        public boolean conflictsWith(Enchantment other) {
            return false;
        }

        public EnchantGlow(int i) {
            super(i);
        }
    }

    public static ItemStack getItem(final ConfigurationSection section) {
        Set<String> keys = section.getKeys(false);
        ItemStack item = new ItemStack(Material.PAPER);
        if(keys.contains("material")) {
            if (getMaterial(section.getString("material")) != null)
                item.setType(getMaterial(section.getString("material")));
            if (section.getString("material").split(":").length > 1 && isInt(section.getString("material").split(":")[1]))
                item.setDurability((short) Integer.parseInt(section.getString("material").split(":")[1]));
        }

        final ItemMeta meta = item.getItemMeta();

        if (keys.contains("name"))
            meta.setDisplayName(color(section.getString( "name")));
        else meta.setDisplayName(color("&cNo name Set"));

        if (keys.contains("lore"))
            meta.setLore(color(section.getStringList( "lore")));

        if (keys.contains("enchanted") && section.getBoolean("enchanted"))
            meta.addEnchant(EnchantGlow.getGlow(), 1, true);

        item.setItemMeta(meta);
        return item;
    }

    public static PotionEffectType getPotionEffect(final String s) {
        final Map<String, PotionEffectType> types = new HashMap<String, PotionEffectType>();
        types.put("hearts", PotionEffectType.ABSORPTION);
        types.put("blind", PotionEffectType.BLINDNESS);
        types.put("nausea", PotionEffectType.CONFUSION);
        types.put("resistence", PotionEffectType.DAMAGE_RESISTANCE);
        types.put("haste", PotionEffectType.FAST_DIGGING);
        types.put("fireresistence", PotionEffectType.FIRE_RESISTANCE);
        types.put("damage", PotionEffectType.HARM);
        types.put("health", PotionEffectType.HEALTH_BOOST);
        types.put("healthboost", PotionEffectType.HEALTH_BOOST);
        types.put("strength", PotionEffectType.INCREASE_DAMAGE);
        types.put("regen", PotionEffectType.REGENERATION);
        types.put("food", PotionEffectType.SATURATION);
        types.put("slowness", PotionEffectType.SLOW_DIGGING);
        types.put("miningfatigue", PotionEffectType.SLOW_DIGGING);
        types.put("waterbreathing", PotionEffectType.WATER_BREATHING);
        types.put("weak", PotionEffectType.WEAKNESS);
        types.put("damageresistence", PotionEffectType.DAMAGE_RESISTANCE);
        types.put("fastdigging", PotionEffectType.FAST_DIGGING);
        types.put("increasedamage", PotionEffectType.INCREASE_DAMAGE);
        types.put("nightvision", PotionEffectType.NIGHT_VISION);
        types.put("slowdigging", PotionEffectType.SLOW_DIGGING);
        return PotionEffectType.getByName(s.toUpperCase()) != null ? PotionEffectType.getByName(s.toUpperCase())
                : types.containsKey(s.toLowerCase().replace("_", "")) ? types.get(s.toLowerCase().replace("_", ""))
                : null;
    }

    public static PotionEffect getEffect(ConfigurationSection section)
    {
        PotionEffectType type = getPotionEffect(section.getName());
        int level = section.getInt("level")-1;
        PotionEffect effect = new PotionEffect(type, Integer.MAX_VALUE, level);
        if(type != null)
            return effect;
        else
            return new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, level);
    }
}
