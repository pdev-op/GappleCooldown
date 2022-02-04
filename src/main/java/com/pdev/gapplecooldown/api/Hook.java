package com.pdev.gapplecooldown.api;

import com.pdev.gapplecooldown.GappleCooldown;

public abstract class Hook {
    protected String name;
    protected GappleCooldown plugin;

    public Hook(GappleCooldown plugin, String name) {
        this.name = name;
        this.plugin = plugin;
    }

    public abstract boolean load();

    public String getName() {
        return name;
    }
}