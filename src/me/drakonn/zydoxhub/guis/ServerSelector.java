package me.drakonn.zydoxhub.guis;

import me.drakonn.zydoxhub.Server;
import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ServerSelector {

    public static ArrayList<String> opens = new ArrayList();
    private static String invName;
    private static int invSize;
    private static ItemStack fillItem;
    private static ItemStack item;
    private static int invSlot;

    public ServerSelector(ZydoxHub plugin) {
        item = Util.getItem(plugin.getConfig().getConfigurationSection("serverselector.item"));
        invSlot = plugin.getConfig().getInt("serverselector.invslot");
        invName = Util.color(plugin.getConfig().getString("serverselector.gui.name"));
        invSize = plugin.getConfig().getInt("serverselector.gui.size");
        fillItem = Util.getItem(plugin.getConfig().getConfigurationSection("serverselector.gui.fillitem"));
    }

    public static void openInv(final Player player) {
        Inventory inv = Bukkit.createInventory(null, invSize, invName);
        for(Server server : ZydoxHub.getServers())
            inv.setItem(server.getGuiInvSlot(), setPlaceHolders(server));

        for(int i = 0; i< invSize; i++)
        {
            ItemStack item = inv.getItem(i);
            if(item == null || item.getType().equals(Material.AIR))
                inv.setItem(i, fillItem);
        }

        opens.add(player.getUniqueId().toString());
        player.openInventory(inv);
        new BukkitRunnable() {
            public void run() {
            if(!opens.contains(player.getUniqueId().toString())) {
                cancel();
                return;
            }

            for(Server server : ZydoxHub.getServers())
                inv.setItem(server.getGuiInvSlot(), setPlaceHolders(server));
            player.updateInventory();
            }
        }.runTaskTimer(ZydoxHub.getInstance(), 20, 20);
    }

    private static ItemStack setPlaceHolders(Server server)
    {
        ItemStack item = server.getGuiItem();
        if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> newLore = new ArrayList<>();
            for (String string : item.getItemMeta().getLore())
            {
                string = string.replaceAll("%status%", server.getStringStatus());
                string = string.replaceAll("%players%", Integer.toString(server.getPlayerCount()));
                newLore.add(string);
            }
            ItemMeta meta = item.getItemMeta();
            meta.setLore(newLore);
            item.setItemMeta(meta);
        }
        return item;
    }


    public static String getInvName() {
        return invName;
    }

    public static ItemStack getItem() {
        return item;
    }

    public static int getInvSlot() {
        return invSlot;
    }
}
