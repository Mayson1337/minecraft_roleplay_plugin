package de.polo.metro_roleplay.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = (Player) event.getPlayer();
        event.setCancelled(true);
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (player.getLocation().distance(players.getLocation()) <= 3) {
                players.sendMessage(player.getDisplayName() + "§8:§f " + event.getMessage());
            } else if (player.getLocation().distance(players.getLocation()) <= 6) {
                players.sendMessage(player.getDisplayName() + "§8:§7 " + event.getMessage());
            } else if (player.getLocation().distance(players.getLocation()) <= 12) {
                players.sendMessage(player.getDisplayName() + "§8:§8 " + event.getMessage());
            }
        }
    }
}
