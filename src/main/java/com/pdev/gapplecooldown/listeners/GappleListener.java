package com.pdev.gapplecooldown.listeners;

import com.pdev.gapplecooldown.GappleCooldown;
import com.pdev.gapplecooldown.api.Cooldown;
import com.pdev.gapplecooldown.managers.CooldownManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GappleListener implements Listener {

    @EventHandler
    public void onGappleEat(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack gapple = player.getInventory().getItemInMainHand();

        if (gapple != null && gapple.getType() == Material.ENCHANTED_GOLDEN_APPLE) {
            CooldownManager cooldownManager = GappleCooldown.getInstance().getCooldownManager();
            Cooldown cooldown = cooldownManager.getGappleCooldown(player);

            if (cooldown != null) {
                e.setUseItemInHand(Event.Result.DEFAULT);
                e.setCancelled(true);

                String message = GappleCooldown.getInstance().getConfiguration().getCooldownMessage(cooldown.getTimeFormatted(), player);

                player.sendMessage(message);
            } else {
                cooldownManager.addCooldown(player);
            }
        }
    }
}
