package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Utils.Json;
import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.UUID;

public class geldbeutelCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("");
        player.sendMessage("§7     ===§8[§6GELDBEUTEL§8]§7===");
        player.sendMessage("§8 ➥ §eBargeld§8:§7 " + PlayerManager.money(player) + "§7$");
        //Json.json(String.valueOf(player), " [\"\",{\"text\":\"§8 \\u27a5 §ePersonalausweis§8:§7 \",\"color\":\"#C3E88D\"},{\"text\":\"Anzeigen\",\"color\":\"gray\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/personalausweis info\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"§6Personalausweis anzeigen\"}}]");
        player.sendMessage("§8 ➥ §ePKW-Lizenz§8: " );
        player.sendMessage("§8 ➥ §eWaffenschein§8: §7§lAnzeigen");
        player.sendMessage("§8 ➥ §eDienstausweis§8: §7§lAnzeigen");
        player.sendMessage("");
        return false;
    }
}
