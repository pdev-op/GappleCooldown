package com.pdev.gapplecooldown.api;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.managers.CooldownManager;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class Placeholders extends PlaceholderExpansion {
    private GappleCooldown plugin;

    public Placeholders(GappleCooldown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean persist() {
        // Always return true since it's internal
        return true;
    }

    @Override
    public boolean canRegister() {
        // Always return true since it's internal
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "gapplecooldown";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        CooldownManager cooldownManager = plugin.getCooldownManager();

        // %gapplecooldown_cooldown%
        if (identifier.equals("cooldown")) {
            Cooldown cooldown = cooldownManager.getGappleCooldown(player);
            return cooldown != null ? cooldown.getTimeFormatted() : "None";
        }

        // %gapplecooldown_total_cooldowns
        if (identifier.equals("total_cooldowns")) {
            return Integer.toString(cooldownManager.getTotalCooldowns());
        }

        return "Invalid Placeholder!";
    }
}
