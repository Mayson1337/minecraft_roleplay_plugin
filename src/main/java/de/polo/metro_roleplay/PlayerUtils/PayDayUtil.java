package de.polo.metro_roleplay.PlayerUtils;

import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class PayDayUtil {
    public static void givePayDay(Player player) throws SQLException {
        double plus = 0;
        double zinsen = Math.round(PlayerManager.bank(player) * 0.05);
        double steuern = Math.round(PlayerManager.bank(player) * 0.015);
        int visumbonus = PlayerManager.visum(player) * 30;
        plus = plus + zinsen - steuern + visumbonus;
        player.sendMessage("");
        player.sendMessage("§7     ===§8[§2KONTOAUSZUG§8]§7===");
        player.sendMessage(" ");
        player.sendMessage("§8 ➜ §3Kontoveränderung§8:§b PayDay");
        player.sendMessage(" ");
        player.sendMessage("§8 ➥ §aZinsen§8:§7 " + zinsen + "$");
        player.sendMessage("§8 ➥ §cSteuern§8:§7 " + steuern + "$");
        player.sendMessage(" ");
        player.sendMessage("§8 ➥ §2Sozialbonus§8:§7 " + visumbonus + "$");
        player.sendMessage(" ");
        plus = Math.round(plus);
        player.sendMessage("§8 ➥ §cGesamt§8:§7 " + plus + "$");
        player.sendMessage(" ");
        PlayerManager.addBankMoney(player, (int) plus);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }
}
