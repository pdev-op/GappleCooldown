package com.pdev.gapplecooldown.hooks;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Hook;
import com.pdev.gapplecooldown.api.Placeholders;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class PapiHook extends Hook {

    public PapiHook(GappleCooldown plugin, String name) {
        super(plugin, name);
    }

    @Override
    public boolean load() {
        if (Bukkit.getPluginManager().isPluginEnabled(name)) {
            try {
                new Placeholders(plugin).register();
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage("        §ePlaceholderAPI §7hook §cerror:!");
                Bukkit.getConsoleSender().sendMessage(e.getMessage());
                return false;
            }

            Bukkit.getConsoleSender().sendMessage("        §ePlaceholderAPI §7hook §aEnabled!");
            return true;
        }

        return false;
    }

    public String loadPlaceholders(Player player, String message) {
        return PlaceholderAPI.setPlaceholders(player, message);
    }
}
