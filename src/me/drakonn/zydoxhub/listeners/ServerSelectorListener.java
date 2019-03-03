package me.drakonn.zydoxhub.listeners;


import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.drakonn.zydoxhub.Server;
import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.datamanagers.MessageManager;
import me.drakonn.zydoxhub.guis.ServerSelector;
import me.drakonn.zydoxhub.utils.Ping;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ServerSelectorListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();

        try {
             if (e.getClickedInventory().getTitle().equals(ServerSelector.getInvName()))
             {
                 e.setCancelled(true);
                 Server server = ZydoxHub.getServer(e.getCurrentItem());
                 if(server == null) {
                     return;
                 }

                 if(!server.shouldRunCommand()) {
                     if (Ping.online(server.getIp(), server.getPort(), 120)) {
                         ByteArrayDataOutput out2 = ByteStreams.newDataOutput();

                         try {
                             out2.writeUTF("Connect");
                             out2.writeUTF(server.getName());
                             p.playSound(p.getLocation(), Sound.LEVEL_UP, 0.5F, 1.0F);
                             String message = MessageManager.CONNECTING;
                             message = message.replaceAll("%server%", server.getName());
                             p.sendMessage(message);
                             p.sendPluginMessage(ZydoxHub.getInstance(), "BungeeCord", out2.toByteArray());
                         } catch (Exception var6) {
                             p.sendMessage("§7Can't connect to " + server.getName() + ", contact a developer.");
                         }
                     } else {
                         String message = MessageManager.SERVER_OFFLINE;
                         message = message.replaceAll("%server%", server.getName());
                         p.sendMessage(message);
                     }
                 }
                 else
                 {
                     org.bukkit.Server bukkitServer = ZydoxHub.getInstance().getServer();
                     String command = server.getCommand();
                     command = command.replaceAll("%player%", p.getName());
                     bukkitServer.dispatchCommand(bukkitServer.getConsoleSender(), command);
                 }
             }
        } catch (Exception var9) {
            System.out.println("error in zydox hub, contact dev");
            e.setCancelled(true);
        }

    }
}
