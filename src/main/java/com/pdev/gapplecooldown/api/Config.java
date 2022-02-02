package com.pdev.gapplecooldown.api;

import java.io.File;

import com.pdev.gapplecooldown.GappleCooldown;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {
    private File file;
    private GappleCooldown plugin;
    private YamlConfiguration config;

    public Config(GappleCooldown plugin) {
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "config.yml");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public void save() {
        if (!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
    }

    public YamlConfiguration getConfiguration() {
        return config;
    }

    public long getCooldownTime() {
        // Time is in seconds, need to get to milliseconds
        return config.getLong("cooldown") * 1000L;
    }

    public String getInsufficientPerms() {
        return addPrefix(config.getString("insufficient-perms"));
    }

    public String getCooldownMessage(String cooldown) {
        return addPrefix(config.getString("cooldown-message").replaceAll("%cooldown%", cooldown));
    }

    public String getUsageMessage() {
        return addPrefix(config.getString("wrong-usage"));
    }

    public String getReloadMessage() {
        return addPrefix(config.getString("reload-message"));
    }

    public String addPrefix(String message) {        
        return ChatColor.translateAlternateColorCodes('&', message.replaceAll("%prefix%", config.getString("prefix")));
    }
}
