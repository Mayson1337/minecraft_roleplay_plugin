package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.PlayerManager;
import de.polo.metro_roleplay.Utils.SupportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class closeticketCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (PlayerManager.isTeam(player)) {
            Player targetplayer = null;
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (SupportManager.getConnection(player).equalsIgnoreCase(players.getUniqueId().toString())) {
                    targetplayer = players;
                }
            }
            SupportManager.deleteTicketConnection(player, targetplayer);
            assert targetplayer != null;
            targetplayer.sendMessage(Main.support_prefix + "§c" + PlayerManager.rang(player) + " " + targetplayer.getName() + "§7 hat dein Ticket geschlossen!");
                player.sendMessage(Main.support_prefix + "Du hast das Ticket von §c" + targetplayer.getName() + "§7 geschlossen.");
        } else {
            player.sendMessage(Main.error_nopermission);
        }
        return false;
    }
}
