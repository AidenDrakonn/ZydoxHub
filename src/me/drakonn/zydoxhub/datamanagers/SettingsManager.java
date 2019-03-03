package me.drakonn.zydoxhub.datamanagers;

import me.drakonn.zydoxhub.ZydoxHub;
import me.drakonn.zydoxhub.utils.Util;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    public static List<String> allowedCommands = new ArrayList<>();
    public static List<PotionEffect> effects = new ArrayList<>();

    private Configuration config;
    public SettingsManager(ZydoxHub plugin)
    {
        config = plugin.getConfig();
    }

    public void loadSettings()
    {
        ConfigurationSection section = config.getConfigurationSection("settings");
        allowedCommands = section.getStringList("allowedcommands");
        for(String name : section.getConfigurationSection("potioneffects").getKeys(false))
        {
            ConfigurationSection effect = section.getConfigurationSection("potioneffects."+name);
            effects.add(Util.getEffect(effect));
        }
    }
}
