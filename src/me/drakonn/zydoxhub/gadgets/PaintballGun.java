package me.drakonn.zydoxhub.gadgets;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.datamanagers.MessageManager;
import me.drakonn.zydoxhub.utils.Lists;
import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class PaintballGun implements Listener {

    private List<Player> shoot = new ArrayList();
    private HashMap<String, Long> cooldown = new HashMap();
    private HashMap<Player, ArrayList<Block>> array = new HashMap();

    private static ItemStack item;
    private static int invSlot;

    private long cooldownTime;

    public PaintballGun(ZydoxHub plugin) {
        item = Util.getItem(plugin.getConfig().getConfigurationSection("gadgets.paintballgun.item"));
        invSlot = plugin.getConfig().getInt("gadgets.paintballgun.invslot");
        cooldownTime = plugin.getConfig().getInt("gadgets.paintballgun.cooldown")*1000;
    }

    @EventHandler
    public void onPaintballThrow(PlayerInteractEvent e) {
        Action a = e.getAction();
        Player p = e.getPlayer();
        if (a != Action.RIGHT_CLICK_AIR && a != Action.RIGHT_CLICK_BLOCK)
            return;

        ItemStack eItem = e.getItem();

        if(!eItem.hasItemMeta())
            return;

        if(!eItem.getItemMeta().hasDisplayName())
            return;

        if(!eItem.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
            return;
        }

        e.setCancelled(true);
        String uuid = p.getUniqueId().toString();
        if (this.cooldown.containsKey(uuid) && System.currentTimeMillis() >= this.cooldown.get(uuid)) {
            cooldown.remove(uuid);
        }

        if (cooldown.containsKey(uuid)) {
            String message = MessageManager.PAINTBALL_GUN_ON_COOLDOWN;
            message = message.replaceAll("%time%", Long.toString((this.cooldown.get(uuid) - System.currentTimeMillis()) / 1000L + 1L));
            p.sendMessage(message);
            return;
        }

        if (!p.hasPermission("zhub.bypass")) {
            cooldown.put(uuid, System.currentTimeMillis() + cooldownTime);
        }

        shoot.add(p);
        p.throwSnowball();
        p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 10.0F, 1.0F);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Snowball) {
            final Player p = (Player)e.getEntity().getShooter();
            Location loc = e.getEntity().getLocation();
            if (this.shoot.contains(p)) {
                ArrayList<Block> blocks = new ArrayList();

                for(int x = -1; x < 2; ++x) {
                    for(int y = -1; y < 2; ++y) {
                        for(int z = -1; z < 2; ++z) {
                            blocks.add(loc.getBlock().getLocation().clone().add((double)x, (double)y, (double)z).getBlock());
                        }
                    }
                }

                Iterator var10 = blocks.iterator();

                while(true) {
                    Block b28;
                    do {
                        do {
                            if (!var10.hasNext()) {
                                (new BukkitRunnable() {
                                    public void run() {
                                    long l = 0L;
                                    Iterator var4 = array.get(p).iterator();

                                    while(var4.hasNext()) {
                                        final Block b = (Block)var4.next();
                                        l += 10L;
                                        (new BukkitRunnable() {
                                            public void run() {
                                                Iterator var2 = Bukkit.getOnlinePlayers().iterator();

                                                while(var2.hasNext()) {
                                                    Player pl = (Player)var2.next();
                                                    if(!b.getType().equals(Material.AIR))
                                                        pl.sendBlockChange(b.getLocation(), b.getType(), b.getData());
                                                }
                                            }
                                        }).runTaskLater(ZydoxHub.getInstance(), l);
                                    }

                                    if (me.drakonn.zydoxhub.gadgets.PaintballGun.this.shoot.contains(p)) {
                                        me.drakonn.zydoxhub.gadgets.PaintballGun.this.shoot.remove(p);
                                    }

                                    array.get(p).clear();
                                    }
                                }).runTaskLater(ZydoxHub.getInstance(), 100L);
                                return;
                            }

                            b28 = (Block)var10.next();
                        } while(Lists.mats.contains(b28.getType()));

                        if (!this.array.containsKey(p)) {
                            this.array.put(p, new ArrayList());
                        }

                        array.get(p).add(b28);
                    } while((new Random()).nextInt(8) == 1);

                    Iterator var8 = Bukkit.getOnlinePlayers().iterator();

                    while(var8.hasNext()) {
                        Player pl = (Player)var8.next();
                        if(!b28.getType().equals(Material.AIR))
                            pl.sendBlockChange(b28.getLocation(), Material.STAINED_CLAY, (byte)(new Random()).nextInt(15));
                    }
                }
            }
        }

    }

    public static ItemStack getItem() {
        return item;
    }

    public static int getInvSlot() {
        return invSlot;
    }
}
