package me.drakonn.zydoxhub.listeners;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.datamanagers.MessageManager;
import me.drakonn.zydoxhub.datamanagers.SettingsManager;
import me.drakonn.zydoxhub.gadgets.PaintballGun;
import me.drakonn.zydoxhub.gadgets.VisibilityToggler;
import me.drakonn.zydoxhub.guis.ServerSelector;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class PlayerJoinListener implements Listener {

    private Location loc;


    public PlayerJoinListener() {
        loc = ZydoxHub.getSpawnPoint();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand().equals(ServerSelector.getItem())) {
            e.setCancelled(true);
            ServerSelector.openInv(p);
        }

    }

    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        if (!e.getPlayer().hasPermission("zhub.bypass")) {
            List<String> allowedCommands = SettingsManager.allowedCommands;
            boolean allowed = false;

            for(int i =0; i < allowedCommands.size(); i++) {
                String s = allowedCommands.get(i);
                if (e.getMessage().startsWith("/" + s)) {
                    allowed = true;
                }
            }

            if (!allowed) {
                e.getPlayer().sendMessage(MessageManager.COMMAND_BLOCKED);
                e.setCancelled(true);
            }

        }
    }

    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent e) {
        if (!e.getPlayer().isOp()) {
            if (e.getChatMessage().startsWith("/")) {
                e.getTabCompletions().clear();
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage((String)null);
        p.sendMessage("event triggered");

        VisibilityToggler.updateHidePlayers(p);

        for(int i = 0; i < 100; ++i) {
            p.sendMessage("");
        }

        p.setFoodLevel(20);
        p.setFireTicks(0);
        p.setExp(0.0F);
        p.setLevel(0);
        p.setMaxHealth(20.0D);
        p.setHealth(20.0D);
        p.setHealthScale(20.0D);
        p.setGameMode(GameMode.SURVIVAL);
        for(PotionEffect effect : SettingsManager.effects)
            if(effect != null)
                effect.apply(p);
        p.getInventory().clear();
        p.getInventory().setArmorContents((ItemStack[])null);
        if (loc != null) {
            p.teleport(loc);
        } else {
            p.sendMessage("§7Hub spawnpoint couldn't be found! Please contact a Staff Member!");
        }

        PlayerInventory inv = p.getInventory();
        inv.setItem(PaintballGun.getInvSlot(), PaintballGun.getItem());
        inv.setItem(ServerSelector.getInvSlot(), ServerSelector.getItem());
        inv.setItem(VisibilityToggler.getInvSlot(), VisibilityToggler.getHidePlayers());
        p.updateInventory();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage((String)null);
        quit(p);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        Player p = e.getPlayer();
        e.setLeaveMessage((String)null);
        quit(p);
    }

    public static void quit(Player p) {
        if (ServerSelector.opens.contains(p.getUniqueId().toString())) {
            ServerSelector.opens.remove(p.getUniqueId().toString());
        }

        VisibilityToggler.updateUnHidePlayers(p);
    }
}
