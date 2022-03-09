package com.pdev.gapplecooldown;

import com.pdev.gapplecooldown.api.Config;
import com.pdev.gapplecooldown.hooks.PapiHook;
import com.pdev.gapplecooldown.listeners.GappleListener;
import com.pdev.gapplecooldown.managers.CommandManager;
import com.pdev.gapplecooldown.managers.CooldownManager;
import com.pdev.gapplecooldown.managers.HooksManager;
import com.pdev.gapplecooldown.utils.bStats;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GappleCooldown extends JavaPlugin {
    private static GappleCooldown instance;

    private CooldownManager cooldownManager;
    private CommandManager commandManager;
    private HooksManager hooksManager;

    private Config config;

    public GappleCooldown() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Timings
        long start = System.currentTimeMillis();

        // Logging
        Bukkit.getConsoleSender()
                .sendMessage("§eGappleCooldown §6v" + getDescription().getVersion() + " §7by §dpdev §7enabling...");

        // Metrics
        new bStats(this, 14156);

        // Hooks Manager
        hooksManager = new HooksManager(this);

        // PlaceholderAPI Hook
        PapiHook papiHook = new PapiHook(this, "PlaceholderAPI");
        hooksManager.addHook(papiHook);

        // Config
        config = new Config(this);

        // Cooldown Manager
        cooldownManager = new CooldownManager(this);

        // Command Manager
        commandManager = new CommandManager(this);

        // Listeners
        getServer().getPluginManager().registerEvents(new GappleListener(), this);

        // Fancy again
        Bukkit.getConsoleSender().sendMessage("§aEnabled in §e" + (System.currentTimeMillis() - start) + "ms");
    }

    @Override
    public void onDisable() {
        // Config
        config.save();
        // Fancy messages

        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage("§eGapple§6Cooldown §7by §dpdev disabled");
    }

    public static GappleCooldown getInstance() {
        return instance;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public HooksManager getHooksManager() {
        return hooksManager;
    }

    public Config getConfiguration() {
        return config;
    }

    public void reloadPlugin() {
        // Save Config
        config.save();

        // Remove all cooldowns
        cooldownManager.resetCooldowns();

        // Config
        config = new Config(this);
    }
}
