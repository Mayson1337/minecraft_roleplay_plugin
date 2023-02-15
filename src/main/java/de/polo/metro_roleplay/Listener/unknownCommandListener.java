package de.polo.metro_roleplay.Listener;

import de.polo.metro_roleplay.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class unknownCommandListener implements Listener {
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        String[] args = msg.split(" ");
        Player player = event.getPlayer();

        if (Bukkit.getServer().getHelpMap().getHelpTopic(args[0]) == null) {
            event.setCancelled(true);
            player.sendMessage(Main.error + "Der Befehl §c" + msg + "§7 wurde nicht gefunden.");
        }
    }
}
