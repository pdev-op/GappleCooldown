package com.pdev.gapplecooldown.managers;

import java.util.HashMap;
import java.util.UUID;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Cooldown;

import org.bukkit.entity.Player;

public class CooldownManager {
    private GappleCooldown plugin;
    private HashMap<UUID, Cooldown> cooldowns;

    public CooldownManager(GappleCooldown plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
    }

    public HashMap<UUID, Cooldown> getCooldowns() {
        return cooldowns;
    }

    public Cooldown getGappleCooldown(Player player) {
        if (getCooldowns().containsKey(player.getUniqueId())) {
            Cooldown cooldown = cooldowns.get(player.getUniqueId());

            if (cooldown.getCooldown() - System.currentTimeMillis() > 0) {
                return cooldown;
            }
        }

        return null;
    }

    public void addCooldown(Player player) {
        if (!player.hasPermission("gapplecooldown.bypass")) {
            Long time = plugin.getConfiguration().getCooldownTime();
            Cooldown cooldown = new Cooldown(player, time);
            cooldowns.put(player.getUniqueId(), cooldown);
        }
    }

    public void resetCooldowns() {
        cooldowns.clear();
    }
}
