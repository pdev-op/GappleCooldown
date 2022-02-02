package com.pdev.gapplecooldown.commands;

import java.util.HashMap;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.GCCommand;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class GappleCooldownCommand extends GCCommand {

    public GappleCooldownCommand() {
        this.addAlias("gapplecooldown");
        this.addAlias("gc");
        this.setDescription("The main command for the plugin");
        this.setSyntax("/gapplecooldown [reload]");
    }

    @Override
    public HashMap<String, Integer> getSuggestions(CommandSender sender) {
        HashMap<String, Integer> suggestions = new HashMap<>();

        suggestions.put("reload", 1);

        return suggestions;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("gapplecooldown.admin");
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) throws Exception {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&e&lGappleCooldown &r&7version &a" + GappleCooldown.getInstance().getDescription().getVersion()
                            + " &7by &dpdev &7:)"));
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            GappleCooldown plugin = GappleCooldown.getInstance();

            plugin.reloadPlugin();
            sender.sendMessage(plugin.getConfiguration().getReloadMessage());
        } else {
            throw new Exception("usage");
        }

        return true;
    }
}
