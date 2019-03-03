package me.drakonn.zydoxhub.commands;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.datamanagers.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadHubCommand implements CommandExecutor {

    public ReloadHubCommand() {
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cs.hasPermission("zhub.reload")) {
            ZydoxHub.getInstance().settingsManager.loadSettings();
            ZydoxHub.getInstance().messageManager.loadMessages();
            ZydoxHub.getInstance().loadServers();
            ZydoxHub.getInstance().loadSpawnPoint();
            cs.sendMessage("§7You success reloaded ZydoxHub.");
        } else {
            cs.sendMessage(MessageManager.NO_PERMISSION);
        }

        return true;
    }
}
