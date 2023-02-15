package de.polo.metro_roleplay.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

public class Json {
    public static void json(String p, String text) {
        Player player = (Player) Bukkit.getPlayer(p);
        assert player != null;
        Bukkit.dispatchCommand(player, "tellraw " + player.getName() + text);
    }
}
