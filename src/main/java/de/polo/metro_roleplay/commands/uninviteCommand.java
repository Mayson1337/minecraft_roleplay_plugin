package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;

public class uninviteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (FactionManager.faction_grade(player) >= 10) {
            if (args.length >= 1) {
                Player targetplayer = Bukkit.getPlayer(args[0]);
                assert targetplayer != null;
                if (Objects.equals(FactionManager.faction(player), FactionManager.faction(targetplayer))) {
                    if (FactionManager.faction_grade(player) > FactionManager.faction_grade(targetplayer)) {
                        if (args[1].equalsIgnoreCase("confirm")) {
                            try {
                                FactionManager.removePlayerFromFrak(targetplayer);
                                targetplayer.sendMessage(Main.prefix + "Du wurdest von §c" + player.getName() + "§7 aus der Fraktion §c" + FactionManager.faction(player)+ "§7 geworfen.");
                                for (Player players : Bukkit.getOnlinePlayers()) {
                                    if (Objects.equals(FactionManager.faction(players), FactionManager.faction(player))) {
                                        players.sendMessage(Main.faction_prefix + "§c" + targetplayer.getName() + "§7 wurde von §c" + player.getName() + "§7 aus der Fraktion geworfen.");
                                    }
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            player.sendMessage(Main.error + "Syntax-Fehler: /uninvite " + targetplayer.getName() + " confirm");
                        }
                    } else {
                        player.sendMessage(Main.error_nopermission);
                    }
                } else {
                    player.sendMessage(Main.faction_prefix + "Du und " + targetplayer.getName() + " seid nicht in der gleichen Fraktion.");
                }
            } else {
                player.sendMessage(Main.faction_prefix + "Syntax-Fehler: /uninvite [Spieler] confirm");
            }
        } else {
            player.sendMessage(Main.error_nopermission);
        }
        return false;
    }
}
