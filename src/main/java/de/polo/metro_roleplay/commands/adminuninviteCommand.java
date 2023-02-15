package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.FactionManager;
import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class adminuninviteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String playergroup = PlayerManager.rang.get(player.getName());
        if (playergroup.equalsIgnoreCase("Administrator") || playergroup.equalsIgnoreCase("Fraktionsmanager")) {
            if (args.length >= 1) {
                try {
                    Player targetplayer = Bukkit.getPlayer(args[0]);
                    assert targetplayer != null;
                    if (targetplayer.isOnline()) {
                        FactionManager.removePlayerFromFrak(targetplayer);
                        player.sendMessage(Main.admin_prefix + "Du hast §c" + targetplayer.getName() + "§7 Administrativ aus der Fraktion geworfen.");
                        targetplayer.sendMessage(Main.support_prefix + "Du wurdest von §c" + player.getName() + "§7 Administrativ aus der Fraktion geworfen.");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                player.sendMessage(Main.admin_error + "Syntax-Fehler: /adminuninvite [Spieler]");
            }
        } else {
            player.sendMessage(Main.error_nopermission);
        }
        return false;
    }
}
