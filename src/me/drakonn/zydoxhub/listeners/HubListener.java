package me.drakonn.zydoxhub.listeners;

import me.drakonn.zydoxhub.ZydoxHub;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class HubListener implements Listener {

    public HubListener() {
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntityType().equals(EntityType.PLAYER)) {
            e.setCancelled(true);

            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                Player player = (Player)e.getEntity();
                Location loc = ZydoxHub.getSpawnPoint();
                player.teleport(loc);
            }
        }

    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onForm(BlockFormEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityForm(EntityBlockFormEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player)e.getEntity();
            if (p.getGameMode() != GameMode.CREATIVE || !p.hasPermission("zhub.build")) {
                e.setCancelled(true);
            }
        } else {
            e.setCancelled(true);
        }

    }
}
