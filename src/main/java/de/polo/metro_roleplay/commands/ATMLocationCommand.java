package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.Utils.LocationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class ATMLocationCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args){
        Player p = (Player) sender;

        File ordner = new File("plugins//Bankautomaten//");
        File file = new File("plugins//Bankautomaten//" + args[0] + ".yml");

        if(p.hasPermission("lobby.admin")){
            LocationManager.setATM(p);
        } else {
            p.sendMessage(Main.error_nopermission);
        }
        return false;
    }

}
