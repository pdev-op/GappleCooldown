package com.pdev.gapplecooldown.api;

import java.io.File;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.hooks.PapiHook;
import com.pdev.gapplecooldown.utils.StringUtils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Config {
    private File file;
    private int version;
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

        this.version = config.getInt("config-version", 1);
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

    public String getPlayerNotFoundMessage() {
        if (version > 1) {
            return addPlaceholders(config.getString("no-player"), null);
        }

        return addPlaceholders("&cPlayer not found, please try again", null);
    }

    public String getOtherCooldownMessage(Player player, Cooldown cooldown) {
        if (cooldown != null) {
            return addPlaceholders(version > 1
                    ? config.getString("cooldown-other-active").replaceAll("%cooldown%", cooldown.getTimeFormatted())
                            .replaceAll("%player%", player.getName())
                    : "%prefix% &e" + player.getName() + " &7has &a" + cooldown.getTimeFormatted()
                            + " &7left on their cooldown.",
                    player);
        }

        return addPlaceholders(
                version > 1 ? config.getString("cooldown-other-inactive").replaceAll("%player%", player.getName())
                        : "%prefix% &e" + player.getName() + " &7currently is not on cooldown.",
                player);
    }

    public String getTotalCooldownsMessage(int total) {
        if (version > 1) {
            return addPlaceholders(config.getString("total-cooldowns").replaceAll("%total%", Integer.toString(total)),
                    null);
        }

        return addPlaceholders("%prefix% There are &e" + total + " &7players on cooldown right now.", null);
    }

    public String addPlaceholders(String message, Player player) {
        message = message.replaceAll("%prefix%", config.getString("prefix"));

        if (papiHook != null) {
            message = papiHook.loadPlaceholders(player, message);
        }

        return StringUtils.colorize(message);
    }
}
