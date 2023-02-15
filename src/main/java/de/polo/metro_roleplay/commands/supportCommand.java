package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.PlayerManager;
import de.polo.metro_roleplay.Utils.SupportManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class supportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length >= 1) {
            if (!SupportManager.ticketCreated(player)) {
                player.sendMessage(Main.support_prefix + "Du hast ein Ticket §aerstellt§7.");
                StringBuilder msg = new StringBuilder(args[0]);
                for (int i = 1; i < args.length; i++) {
                    msg.append(' ').append(args[i]);
                }
                SupportManager.createTicket(player, String.valueOf(msg));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (PlayerManager.onPlayer.get(players.getUniqueId().toString())) {
                        players.sendMessage(Main.support_prefix + "§a" + player.getName() + "§7 hat ein Ticket erstellt.");
                    }
                }
            } else {
                player.sendMessage(Main.support_prefix + "Du hast bereits ein Ticket offen.");
            }
        } else {
            player.sendMessage(Main.error + "Syntax-Fehler: /support [Anliegen]");
        }
        return false;
    }
}
