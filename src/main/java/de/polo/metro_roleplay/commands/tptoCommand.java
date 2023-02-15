package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class tptoCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (aduty.isAduty(player)) {
            if (args.length >= 1) {
                LocationManager.useLocation(player, args[0]);
                player.sendMessage(Main.admin_prefix + "Du hast dich zu §c" + args[0] + "§7 teleportiert.");
            } else {
                player.sendMessage(Main.admin_error + "Syntax-Fehler: /tpto [Punkt]");
            }
        } else {
            player.sendMessage(Main.admin_error + "Du bist nicht im Admindienst!");
        }
        return false;
    }
}
