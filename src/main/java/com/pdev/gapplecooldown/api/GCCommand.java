package com.pdev.gapplecooldown.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.pdev.gapplecooldown.GappleCooldown;

import org.bukkit.command.CommandSender;

public abstract class GCCommand {
    protected GappleCooldown plugin;
    private ArrayList<String> aliases = new ArrayList<String>();
    private String usage;

    public GCCommand(GappleCooldown plugin) {
        this.plugin = plugin;
    }

    public ArrayList<String> getAliases() {
        return this.aliases;
    }

    public void setAliases(ArrayList<String> a) {
        this.aliases = a;
    }

    public void addAlias(String a) {
        this.aliases.add(a);
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String s) {
        this.usage = s;
    }

    public abstract HashMap<String, Integer> getSuggestions(CommandSender sender);

    public abstract boolean hasPermission(CommandSender sender);

    public abstract boolean execute(CommandSender sender, String args[]) throws Exception;
}
