package me.drakonn.zydoxhub.datamanagers;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

public class MessageManager {

    public static String PAINTBALL_GUN_ON_COOLDOWN;
    public static String COMMAND_BLOCKED;
    public static String NO_PERMISSION;
    public static String SERVER_OFFLINE;
    public static String CONNECTING;

    private Configuration config;
    public MessageManager(ZydoxHub plugin)
    {
        config = plugin.getConfig();
    }

    public void loadMessages()
    {
        ConfigurationSection section = config.getConfigurationSection("settings.messages");
        PAINTBALL_GUN_ON_COOLDOWN = Util.color(section.getString("paintballgunoncooldown"));
        COMMAND_BLOCKED = Util.color(section.getString("commandblocked"));
        NO_PERMISSION = Util.color(section.getString("nopermission"));
        SERVER_OFFLINE = Util.color(section.getString("serveroffline"));
        CONNECTING = Util.color(section.getString("connecting"));
    }
}
