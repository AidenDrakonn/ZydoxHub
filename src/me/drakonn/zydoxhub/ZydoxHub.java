package me.drakonn.zydoxhub;


import me.drakonn.zydoxhub.commands.ReloadHubCommand;
import me.drakonn.zydoxhub.commands.SetHubCommand;
import me.drakonn.zydoxhub.datamanagers.MessageManager;
import me.drakonn.zydoxhub.datamanagers.SettingsManager;
import me.drakonn.zydoxhub.gadgets.PaintballGun;
import me.drakonn.zydoxhub.gadgets.VisibilityToggler;
import me.drakonn.zydoxhub.guis.ServerSelector;
import me.drakonn.zydoxhub.listeners.HubListener;
import me.drakonn.zydoxhub.listeners.PlayerJoinListener;
import me.drakonn.zydoxhub.listeners.PlayerListener;
import me.drakonn.zydoxhub.listeners.ServerSelectorListener;
import me.drakonn.zydoxhub.utils.Ping;
import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;


public class ZydoxHub extends JavaPlugin {

    private static ZydoxHub instance;
    private static  List<Server> servers = new ArrayList<>();
    private static Location spawnPoint;

    public MessageManager messageManager = new MessageManager(this);
    public SettingsManager settingsManager = new SettingsManager(this);

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        messageManager.loadMessages();
        settingsManager.loadSettings();
        new ServerSelector(this);
        loadSpawnPoint();
        loadServers();
        registerListeners();
        setCommands();

        try {
            this.getServer().getWorld(getConfig().getString("settings.world")).setGameRuleValue("doDaylightCycle", "false");
        } catch (Exception var3) {}
        serverMonitor();
    }

    public void loadServers()
    {
        servers.clear();
        ConfigurationSection serversSection = getConfig().getConfigurationSection("servers");
        for(String name : serversSection.getKeys(false))
        {
            ConfigurationSection section = serversSection.getConfigurationSection(name);
            String ip = section.getString("ip");
            int port = section.getInt("port");
            int invSlot = section.getInt("invslot");
            ItemStack item = Util.getItem(section.getConfigurationSection("item"));
            boolean runCommand = section.getBoolean("runcommand");
            String command = "";
            if(runCommand)
                command = section.getString("command");

            servers.add(new Server(ip, port, name, invSlot, item, runCommand, command));
        }
    }

    public void loadSpawnPoint()
    {
        World world =Bukkit.getWorld(ZydoxHub.getInstance().getConfig().getString("spawnpoint.world"));
        double x = ZydoxHub.getInstance().getConfig().getDouble("spawnpoint.x");
        double y = ZydoxHub.getInstance().getConfig().getDouble("spawnpoint.y");
        double z = ZydoxHub.getInstance().getConfig().getDouble("spawnpoint.z");
        Location loc = new Location(world, x, y, z);
        loc.setPitch((float)ZydoxHub.getInstance().getConfig().getDouble("spawnpoint.pitch"));
        loc.setYaw((float)ZydoxHub.getInstance().getConfig().getDouble("spawnpoint.yaw"));
        spawnPoint = loc;
    }

    private void serverMonitor()
    {
        (new BukkitRunnable() {
            public void run() {
            for (Server server : servers) {
                server.setStatus(Ping.online(server.getIp(), server.getPort(), 120));
                server.setPlayerCount(Ping.onpl(server.getIp(), server.getPort(), 120));
            }
            }
        }).runTaskTimer(this, 0L, 100L);
    }

    private void registerListeners()
    {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new HubListener(), this);
        pm.registerEvents(new PlayerJoinListener(), this);
        pm.registerEvents(new PaintballGun(this), this);
        pm.registerEvents(new ServerSelectorListener(), this);
        pm.registerEvents(new VisibilityToggler(this), this);
    }

    private void setCommands()
    {
        getCommand("sethub").setExecutor(new SetHubCommand());
        getCommand("reloadhub").setExecutor(new ReloadHubCommand());
    }

    public static Server getServer(ItemStack item) {
        if(!item.hasItemMeta() || !item.getItemMeta().hasDisplayName())
            return null;

        return servers.stream().filter(server -> server.getGuiItem().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()))
            .findFirst().orElse(null);
    }

    public static List<Server> getServers()
    {
        return servers;
    }

    public static ZydoxHub getInstance() {
        return instance;
    }

    public static Location getSpawnPoint() {
        return spawnPoint;
    }
}
