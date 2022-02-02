package com.pdev.gapplecooldown.api;

import com.pdev.gapplecooldown.utils.NumberUtils;

import org.bukkit.entity.Player;

public class Cooldown {
    private Long cooldown;
    private Player player;

    public Cooldown(Player player, Long cooldown) {
        this.cooldown = cooldown + System.currentTimeMillis();
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public Long getCooldown() {
        return cooldown;
    }

    public boolean isDone() {
        return cooldown - System.currentTimeMillis() < 0;
    }

    public String getTimeFormatted() {
        Long remaining = cooldown - System.currentTimeMillis();

        return NumberUtils.getTimeFormatted(remaining);
    }
}
