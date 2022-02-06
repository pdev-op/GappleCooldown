package com.pdev.gapplecooldown.commands;

import java.util.HashMap;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Cooldown;
import com.pdev.gapplecooldown.api.GCCommand;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GappleCooldownCommand extends GCCommand {

    public GappleCooldownCommand(GappleCooldown plugin) {
        super(plugin);

        this.addAlias("gapplecooldown");
        this.addAlias("gc");
        this.setDescription("The main command for the plugin");
        this.setSyntax("/gapplecooldown [reload | get | total] [player]");
    }

    @Override
    public HashMap<String, Integer> getSuggestions(CommandSender sender) {
        HashMap<String, Integer> suggestions = new HashMap<>();

        suggestions.put("reload", 1);
        suggestions.put("get", 1);
        suggestions.put("total", 1);
        suggestions.put("players", 2);

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
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadPlugin();
                sender.sendMessage(
                        plugin.getConfiguration().getReloadMessage(sender instanceof Player ? (Player) sender : null));
            } else if (args[0].equalsIgnoreCase("total")) {
                int total = plugin.getCooldownManager().getTotalCooldowns();
                sender.sendMessage(plugin.getConfiguration().getTotalCooldownsMessage(total));
            } else {
                throw new Exception("usage");
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("get")) {
            Player player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                throw new Exception("no-player");
            }

            Cooldown cooldown = plugin.getCooldownManager().getGappleCooldown(player);
            sender.sendMessage(plugin.getConfiguration().getOtherCooldownMessage(player, cooldown));
        } else {
            throw new Exception("usage");
        }

        return true;
    }
}
