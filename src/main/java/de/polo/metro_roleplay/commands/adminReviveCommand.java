package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.PlayerUtils.DeathUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class adminReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (aduty.isAduty(player)) {
            if (args.length >= 1) {
                Player targetplayer = Bukkit.getPlayer(args[0]);
                assert targetplayer != null;
                if (targetplayer.isOnline()) {
                    DeathUtil.RevivePlayer(targetplayer);
                    player.sendMessage(Main.admin_prefix + "Du hast §c" + targetplayer.getName() + "§7 wiederbelebt.");
                } else {
                    player.sendMessage(Main.admin_error + "§c" + args[0] + "§7 ist nicht online.");
                }
            } else {
                DeathUtil.RevivePlayer(player);
                player.sendMessage(Main.admin_prefix + "Du hast dich wiederbelebt.");
            }
        } else {
            player.sendMessage(Main.admin_error + "Du bist nicht im Admindienst!");
        }
        return false;
    }
}
