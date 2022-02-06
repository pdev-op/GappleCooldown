package com.pdev.gapplecooldown.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.pdev.gapplecooldown.GappleCooldown;

import org.bukkit.command.CommandSender;

public abstract class GCCommand {
    protected GappleCooldown plugin;
    private ArrayList<String> aliases = new ArrayList<String>();
    private String syntax;
    private String description;

    public GCCommand(GappleCooldown plugin) {
        this.plugin = plugin;
    }

    public ArrayList<String> getAliases() { return this.aliases; }

    public void setAliases(ArrayList<String> a)
    {
        this.aliases = a;
    }

    public void addAlias(String a)
    {
        this.aliases.add(a);
    }

    public String getSyntax()
    {
        return this.syntax;
    }

    public void setSyntax(String s)
    {
        this.syntax = s;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setDescription(String d)
    {
        this.description = d;
    }

    public abstract HashMap<String, Integer> getSuggestions(CommandSender sender);

    public abstract boolean hasPermission(CommandSender sender);

    public abstract boolean execute(CommandSender sender, String args[]) throws Exception;
}
