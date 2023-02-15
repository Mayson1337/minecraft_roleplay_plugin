package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class LocationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args){
        Player p = (Player) sender;

        File ordner = new File("plugins//Lobby//");
        File file = new File("plugins//Lobby//" + args[0] + ".yml");

        if(p.hasPermission("lobby.admin")){
            LocationManager.setLocation(args[0], p);
            p.sendMessage(Main.prefix + "Du hast die Location §c" + args[0] + " §7gesetzt");
        } else {
            p.sendMessage(Main.error_nopermission);
        }
        return false;
    }

}
