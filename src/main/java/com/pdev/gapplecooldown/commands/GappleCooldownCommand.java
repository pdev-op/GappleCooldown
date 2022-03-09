package com.pdev.gapplecooldown.commands;

import java.util.HashMap;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Cooldown;
import com.pdev.gapplecooldown.api.GCCommand;
import com.pdev.gapplecooldown.utils.StringUtils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class GappleCooldownCommand extends GCCommand {
    public GappleCooldownCommand(GappleCooldown plugin) {
        super(plugin);

        this.addAlias("gapplecooldown");
        this.addAlias("gc");
        this.setUsage("/gapplecooldown [reload | get | total | help] [player]");
    }

    @Override
    public HashMap<String, Integer> getSuggestions(CommandSender sender) {
        HashMap<String, Integer> suggestions = new HashMap<>();

        suggestions.put("reload", 1);
        suggestions.put("get", 1);
        suggestions.put("total", 1);
        suggestions.put("help", 1);
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
            sendHelpMessage(sender);
        } else if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadPlugin();
                sender.sendMessage(
                        plugin.getConfiguration().getReloadMessage(sender instanceof Player ? (Player) sender : null));
            } else if (args[0].equalsIgnoreCase("total")) {
                int total = plugin.getCooldownManager().getTotalCooldowns();
                sender.sendMessage(plugin.getConfiguration().getTotalCooldownsMessage(total));
            } else if (args[0].equalsIgnoreCase("help")) {
                sendHelpMessage(sender);
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

    private void sendHelpMessage(CommandSender sender) {
        // Spacer
        sender.sendMessage(" ");

        // Title
        sender.sendMessage(StringUtils.colorize(
                "&#fbf100&lG&#fbdf00&la&#fccc00&lp&#fcba00&lp&#fca700&ll&#fd9500&le&#fd8200&lC&#fc700b&lo&#f95d21&lo&#f64a36&ll&#f3384c&ld&#f12562&lo&#ee1377&lw&#eb008d&ln"));
        TextComponent title = new TextComponent(StringUtils.colorize("&6Help Page"));
        title.setClickEvent(
                new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/gapplecooldown.99706/"));
        title.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new Text(StringUtils.colorize("&6&lGappleCooldown\n&7Version: &f" + plugin.getDescription().getVersion()
                        + "\n&7Written by &dpdev\n\n&7➥ &eClick to go to plugin page"))));
        sender.spigot().sendMessage(title);

        // Spacer
        sender.sendMessage(" ");

        // Reload
        TextComponent reload = new TextComponent(StringUtils.colorize("&8- &f/gc &nreload"));
        reload.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gc reload"));
        reload.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(StringUtils
                .colorize("&7Description: &fReload the plugin & config.\n&7Permission: &fgapplecooldown.admin"))));

        sender.spigot().sendMessage(reload);

        // Total
        TextComponent total = new TextComponent(StringUtils.colorize("&8- &f/gc &ntotal"));
        total.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gc total"));
        total.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(StringUtils.colorize(
                "&7Description: &fGet the total amount of\n&fplayers on cooldown.\n&7Permission: &fgapplecooldown.admin"))));

        sender.spigot().sendMessage(total);

        // Get
        TextComponent get = new TextComponent(StringUtils.colorize("&8- &f/gc &nget&r &2[player]"));
        get.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/gc get "));
        get.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(StringUtils.colorize(
                "&7Description: &fGet the remaining cooldown\n&ffor a specific player.\n&7Permission: &fgapplecooldown.admin"))));

        sender.spigot().sendMessage(get);

        // Spacer
        sender.sendMessage(" ");
    }
}
