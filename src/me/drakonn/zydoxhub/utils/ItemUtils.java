package me.drakonn.zydoxhub.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemUtils {

    public ItemUtils() {
    }

    public static ItemStack create(Material material, Integer amount, Short subid, String displayname, List<String> lore) {
        ItemStack output = new ItemStack(material, amount, subid);
        ItemMeta meta = output.getItemMeta();
        if (displayname != null) {
            meta.setDisplayName(displayname);
        }

        if (lore != null && !lore.isEmpty()) {
            meta.setLore(lore);
        }

        return output;
    }

    public static ItemStack create(Material material, Short subid, String displayname, List<String> lore, String ip, Integer port) {
        ItemStack output = new ItemStack(material, 1, subid);
        ItemMeta meta = output.getItemMeta();
        boolean online = Ping.online(ip, port, 120);
        int on = Ping.onpl(ip, port, 120);
        int max = Ping.maxpl(ip, port, 120);
        if (displayname != null) {
            meta.setDisplayName(displayname);
        }

        if (online) {
            output.setAmount(max);
        }

        if (lore != null && !lore.isEmpty()) {
            List<String> lore_out = new ArrayList();

            String s;
            for(Iterator var13 = lore.iterator(); var13.hasNext(); lore_out.add(s)) {
                s = (String)var13.next();
                if (s.contains("%on_pl%")) {
                    if (online) {
                        s.replaceAll("%on_pl%", "§a" + String.valueOf(on));
                    } else {
                        s.replaceAll("%on_pl%", "§c--");
                    }
                }

                if (s.contains("%max_pl%")) {
                    if (online) {
                        s.replaceAll("%max_pl%", "§a" + String.valueOf(max));
                    } else {
                        s.replaceAll("%max_pl%", "§c--");
                    }
                }

                if (s.contains("%online%")) {
                    if (online) {
                        s.replaceAll("%online%", "§a§lOnline");
                    } else {
                        s.replaceAll("%online%", "§c§lOffline");
                    }
                }
            }

            meta.setLore(lore_out);
        }

        output.setItemMeta(meta);
        return output;
    }
}
