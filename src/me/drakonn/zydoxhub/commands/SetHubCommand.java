package me.drakonn.zydoxhub.commands;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.datamanagers.MessageManager;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHubCommand implements CommandExecutor {

    public SetHubCommand() {
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs.hasPermission("zhub.setspawn")) {
            if (cs instanceof Player) {
                Player p = (Player)cs;
                ZydoxHub.getInstance().getConfig().set("spawnpoint.world", p.getLocation().getWorld().getName());
                ZydoxHub.getInstance().getConfig().set("spawnpoint.x", p.getLocation().getX());
                ZydoxHub.getInstance().getConfig().set("spawnpoint.y", p.getLocation().getY());
                ZydoxHub.getInstance().getConfig().set("spawnpoint.z", p.getLocation().getZ());
                ZydoxHub.getInstance().getConfig().set("spawnpoint.yaw", p.getLocation().getYaw());
                ZydoxHub.getInstance().getConfig().set("spawnpoint.pitch", p.getLocation().getPitch());
                ZydoxHub.getInstance().saveConfig();
                ZydoxHub.getInstance().reloadConfig();
                ZydoxHub.getInstance().loadSpawnPoint();
                p.sendMessage("§7You success set the spawnpoint.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.5F, 1.0F);
            } else {
                cs.sendMessage("§7This command is only executable by Players!");
            }
        } else {
            cs.sendMessage(MessageManager.NO_PERMISSION);
        }

        return true;
    }
}
