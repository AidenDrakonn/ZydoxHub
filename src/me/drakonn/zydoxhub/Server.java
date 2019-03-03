package me.drakonn.zydoxhub;

import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.inventory.ItemStack;

public class Server {

    private String ip;
    private int port;
    private boolean status = false;
    private int playerCount = 0;
    private int guiInvSlot;
    private ItemStack guiItem;
    private String name;
    private boolean runCommand;
    private String command;

    public Server(String ip, int port, String name, int guiInvSlot, ItemStack guiItem, boolean runCommand, String command)
    {
        this.ip = ip;
        this.port = port;
        this.guiInvSlot = guiInvSlot;
        this.guiItem = guiItem;
        this.name = name;
        this.runCommand = runCommand;
        this.command = command;
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public String getIp() {
        return ip;
    }

    public int getPort(){
        return port;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getGuiInvSlot() {
        return guiInvSlot;
    }

    public ItemStack getGuiItem() {
        return guiItem.clone();
    }

    public String getName() {
        return name;
    }

    public String getStringStatus()
    {
        if(status)
            return Util.color("&2Online");
        else
            return Util.color("&cOffline");

    }

    public boolean shouldRunCommand() {
        return runCommand;
    }

    public String getCommand() {
        return command;
    }
}
