package me.drakonn.zydoxhub.listeners;

import me.drakonn.zydoxhub.gadgets.VisibilityToggler;
import me.drakonn.zydoxhub.guis.ServerSelector;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.beans.Visibility;

public class PlayerListener implements Listener {

    public PlayerListener() {
    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickup(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onChangeXP(PlayerExpChangeEvent e) {
        e.setAmount(0);
    }

    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            e.setFoodLevel(20);
            p.setSaturation(20.0F);
            p.setExhaustion(20.0F);
        }

    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event) {
        Player p = (Player)event.getWhoClicked();
        if (!p.isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(InventoryInteractEvent event) {
        Player p = (Player)event.getWhoClicked();
        if (!p.isOp()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("zhub.build")) {
            e.setCancelled(true);
        }

        ItemStack item = p.getItemInHand();

        if(item.getItemMeta().getDisplayName().equals(VisibilityToggler.getHidePlayers().getItemMeta().getDisplayName()) && item.getType().equals(VisibilityToggler.getHidePlayers().getType()))
        {
            e.setCancelled(true);
        }

        if(item.getItemMeta().getDisplayName().equals(VisibilityToggler.getShowPlayers().getItemMeta().getDisplayName()) && item.getType().equals(VisibilityToggler.getShowPlayers().getType()))
        {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("zhub.break")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onHangingBreak(HangingBreakByEntityEvent e) {
        if (e.getRemover().getType() == EntityType.PLAYER) {
            Entity entity = e.getRemover();
            Player p = (Player)entity;
            if (!p.hasPermission("zhub.break")) {
                e.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onHangingPlace(HangingPlaceEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("zhub.build")) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(!p.isOp())
            e.setCancelled(true);

    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if(!p.isOp())
                e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteractAt(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if(!p.isOp())
            e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        Entity damager = e.getDamager();
        if(!damager.isOp())
            e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();

        if (e.getInventory().getName().equals(ServerSelector.getInvName())) {
            p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 0.5F, 1.0F);
            if (ServerSelector.opens.contains(p.getUniqueId().toString())) {
                ServerSelector.opens.remove(p.getUniqueId().toString());
            }
        }

    }


}
