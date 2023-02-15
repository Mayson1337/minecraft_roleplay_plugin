package de.polo.metro_roleplay.Listener;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.PlayerManager;
import de.polo.metro_roleplay.Utils.SupportManager;
import de.polo.metro_roleplay.commands.aduty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class QuitListener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
        event.setQuitMessage("");
        aduty.send_message("§c" + player.getName() + "§7 hat den Server verlassen.");
        try {
            PlayerManager.savePlayer(player);
            if (SupportManager.isInConnection(player)) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (SupportManager.getConnection(players).equalsIgnoreCase(player.getUniqueId().toString())) {
                        SupportManager.deleteTicketConnection(players, player);
                        players.sendMessage(Main.support_prefix + "§c" + player.getName() + "§7 ist offline gegangen. Das Ticket wurde geschlossen.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}