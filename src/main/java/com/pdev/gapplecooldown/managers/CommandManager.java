package com.pdev.gapplecooldown.managers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Config;
import com.pdev.gapplecooldown.api.GCCommand;
import com.pdev.gapplecooldown.commands.GappleCooldownCommand;
import com.pdev.gapplecooldown.utils.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor, TabCompleter {
    private GappleCooldown plugin;
    private ArrayList<GCCommand> commands;

    public CommandManager(GappleCooldown plugin) {
        // Plugin
        this.plugin = plugin;

        // Commands List
        commands = new ArrayList<GCCommand>();

        // Main Command
        commands.add(new GappleCooldownCommand(plugin));

        // Register the commands
        registerCommands();
    }

    public void addCommand(GCCommand command) {
        commands.add(command);
    }

    public ArrayList<GCCommand> getCommands() {
        return commands;
    }

    public void registerCommands() {
        for (GCCommand c : commands) {
            for (String s : c.getAliases()) {
                if (plugin.getCommand(s) != null) {
                    plugin.getCommand(s).setExecutor(this);
                    plugin.getCommand(s).setTabCompleter(this);
                }
            }
        }
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> suggestions = new ArrayList<String>();

        for (GCCommand c : commands) {
            for (String al : c.getAliases()) {
                if (al.equalsIgnoreCase(alias) && c.hasPermission(sender)) {
                    HashMap<String, Integer> suggestionsMap = c.getSuggestions(sender);

                    for (String s : suggestionsMap.keySet()) {
                        if (args.length == suggestionsMap.get(s)) {
                            if (!s.equalsIgnoreCase("players")) {
                                suggestions.add(s);
                            } else {
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    suggestions.add(player.getName());
                                }
                            }
                        }
                    }
                }
            }
        }

        return suggestions;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (GCCommand c : commands) {
            if (c.getAliases().contains(label.toLowerCase())) {

                Config config = plugin.getConfiguration();

                // Check Permissions
                if (!c.hasPermission(sender)) {
                    if (!(sender instanceof Player)) {
                        return false;
                    }

                    sender.sendMessage(config.getInsufficientPerms(null));

                    return false;
                }

                // Send it
                try {
                    c.execute(sender, args);
                } catch (Exception e) {
                    if (e.getMessage().equalsIgnoreCase("insufficient-permissions")) {
                        sender.sendMessage(config.getInsufficientPerms(null));
                    } else if (e.getMessage().equalsIgnoreCase("usage")) {
                        sender.sendMessage(config.getUsageMessage(null).replace("%usage%", c.getUsage()));
                    } else if (e.getMessage().equalsIgnoreCase("no-player")) {
                        sender.sendMessage(config.getPlayerNotFoundMessage());
                    } else {
                        sender.sendMessage(StringUtils.colorize(
                                "&cAn internal error has occured, please contact an admin. We are sorry for the inconvenience!"));

                        e.printStackTrace();
                    }
                }

                return true;
            }
        }

        return false;
    }
}
