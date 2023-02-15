package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

public class setteamCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission("operator")) {
            if (args.length == 2) {
                Player targetplayer = getServer().getPlayer(args[0]);
                String rank = args[1].toString();
                PlayerManager.updatePlayerTeam(targetplayer.getUniqueId().toString(), rank);
                player.sendMessage(Main.admin_prefix + targetplayer.getName() + " ist nun §c" + rank + "§7.");
                targetplayer.sendMessage(Main.admin_prefix + "Du bist nun §c" + rank + "§7!");
                PlayerManager.rang.remove(targetplayer.getUniqueId().toString());
                PlayerManager.rang.put(targetplayer.getUniqueId().toString(), rank);
            } else {
                player.sendMessage(Main.admin_error + "Syntax-Fehler: /setgroup [Spieler] [Rang]");
            }
        } else {
            player.sendMessage(Main.error_nopermission);
        }
        return false;
    }
}
