package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class teamchatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String uuid = player.getUniqueId().toString();
        if (PlayerManager.onPlayer.get(uuid)) {
            if (args.length >= 1) {
                String msg = args[0];
                for (int i = 1; i < args.length; i++) {
                    msg = msg + ' ' + args[i];
                }
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (PlayerManager.onPlayer.get(players.getUniqueId().toString())) {
                        players.sendMessage(Main.admin_prefix + "[" + PlayerManager.rang.get(player.getName()) + "] " + player.getName() + "§8:§7 " + msg);
                    }
                }
            } else {
                player.sendMessage(Main.admin_error + "Syntax-Error: /teamchat [Nachricht]");
            }
        } else {
            player.sendMessage(Main.error_nopermission);
        }
        return false;
    }
}
