package com.pdev.gapplecooldown.api;

import java.io.File;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.hooks.PapiHook;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Config {
    private File file;
    private GappleCooldown plugin;
    private YamlConfiguration config;
    private PapiHook papiHook;

    public Config(GappleCooldown plugin) {
        this.plugin = plugin;
        this.papiHook = (PapiHook) plugin.getHooksManager().getHook("PlaceholderAPI");

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

    public String getInsufficientPerms(Player player) {
        return addPlaceholders(config.getString("insufficient-perms"), player);
    }

    public String getCooldownMessage(String cooldown, Player player) {
        return addPlaceholders(config.getString("cooldown-message").replaceAll("%cooldown%", cooldown), player);
    }

    public String getUsageMessage(Player player) {
        return addPlaceholders(config.getString("wrong-usage"), player);
    }

    public String getReloadMessage(Player player) {
        return addPlaceholders(config.getString("reload-message"), player);
    }

    public String addPlaceholders(String message, Player player) {  
        message = message.replaceAll("%prefix%", config.getString("prefix"));

        if (papiHook != null) {
            message = papiHook.loadPlaceholders(player, message);
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
