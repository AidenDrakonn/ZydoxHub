package me.drakonn.zydoxhub.gadgets;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class VisibilityToggler implements Listener {

    public static List<Player> enabled = new ArrayList();

    private static ItemStack showPlayers;
    private static ItemStack hidePlayers;
    private static int invSlot;

    public VisibilityToggler(ZydoxHub plugin) {
        showPlayers = Util.getItem(plugin.getConfig().getConfigurationSection("gadgets.visibilitytoggler.showitem"));
        hidePlayers = Util.getItem(plugin.getConfig().getConfigurationSection("gadgets.visibilitytoggler.hideitem"));
        invSlot = plugin.getConfig().getInt("gadgets.visibilitytoggler.invslot");
    }

    @EventHandler
    public void onToggle(PlayerInteractEvent e) {
        Action a = e.getAction();
        Player player = e.getPlayer();
        ItemStack item = e.getItem();
        if (a != Action.RIGHT_CLICK_AIR && a != Action.RIGHT_CLICK_BLOCK)
            return;

        if(item == null || item.getType().equals(Material.AIR))
            return;

        if(!item.hasItemMeta())
            return;

        if(!item.getItemMeta().hasDisplayName())
            return;

        if(item.getItemMeta().getDisplayName().equals(hidePlayers.getItemMeta().getDisplayName()) && item.getType().equals(hidePlayers.getType()))
        {
            enabled.add(player);
            hidePlayers(player);
            player.setItemInHand(showPlayers);
            return;
        }

        if(item.getItemMeta().getDisplayName().equals(showPlayers.getItemMeta().getDisplayName()) && item.getType().equals(showPlayers.getType()))
        {
            enabled.remove(player);
            showPlayers(player);
            player.setItemInHand(hidePlayers);
            return;
        }
    }

    private void showPlayers(Player player)
    {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            if(!player.equals(onlinePlayer))
                player.showPlayer(onlinePlayer);
        }
    }

    private void hidePlayers(Player player)
    {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers())
        {
            if(!player.equals(onlinePlayer))
                player.hidePlayer(onlinePlayer);
        }
    }

    public static void updateHidePlayers(Player newPlayer)
    {
        for(Player player : enabled)
            player.hidePlayer(newPlayer);
    }

    public static void updateUnHidePlayers(Player newPlayer)
    {
        for(Player player : enabled)
            player.showPlayer(newPlayer);
    }

    public static ItemStack getHidePlayers() {
        return hidePlayers;
    }

    public static int getInvSlot() {
        return invSlot;
    }

    public static ItemStack getShowPlayers() {
        return showPlayers;
    }
}
