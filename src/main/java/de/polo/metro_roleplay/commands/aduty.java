package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class aduty implements CommandExecutor {
    public static HashMap<String, Boolean> aduty = new HashMap<String, Boolean>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        if (PlayerManager.onPlayer.get(player.getUniqueId().toString())) {
            if (aduty.get(player.getName()) != null) {
                aduty.remove(player.getName());
                player.sendMessage(Main.admin_prefix + "Du hast den Admindienst §cverlassen§7.");
                (player).setFlying(false);
                (player).setAllowFlight(false);
                player.getPlayer().setPlayerListName("§8[§7Team§8]§7 " + player.getName());
                player.getPlayer().setDisplayName("§8[§7Team§8]§7 " + player.getName());
            } else {
                aduty.put(player.getName(), true);
                player.sendMessage(Main.admin_prefix + "Du hast den Admindienst §abetreten§7.");
                (player).setAllowFlight(true);
                player.getPlayer().setPlayerListName("§8[§cTeam§8]§c " + player.getName());
                player.getPlayer().setDisplayName("§8[§cTeam§8]§c " + player.getName());
            }
        } else {
            player.sendMessage(Main.error_nopermission);
        }
        return false;
    }
    public static boolean isAduty(Player player) {
        return aduty.get(player.getName()) != null;
    }

    public static void send_message(String msg) {
        for (Player player1 : Bukkit.getOnlinePlayers()) {
            if (isAduty(player1)) {
                player1.sendMessage(Main.admin_info + msg);
            }
        }
    }
}
