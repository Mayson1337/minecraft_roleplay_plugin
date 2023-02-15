package de.polo.metro_roleplay.PlayerUtils;

import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.entity.Player;

public class BankingUtils {
    public static void sendKontoauszug(Player player) {
        player.sendMessage(" ");
        player.sendMessage("§7     ===§8[§2KONTOAUSZUG§8]§7===");
        player.sendMessage(" ");
        player.sendMessage("§8 ➥ §aBankguthaben§8:§7 " + PlayerManager.bank(player) + "$");
    }

    public static void sendBankChangeReason(Player player, String reason) {
        player.sendMessage(" ");
        player.sendMessage("§7     ===§8[§2KONTOAUSZUG§8]§7===");
        player.sendMessage(" ");
        player.sendMessage("§8 ➜ §3Kontoveränderung§8:§b " + reason);
        player.sendMessage(" ");
        player.sendMessage("§8 ➥ §aBankguthaben§8:§7 " + PlayerManager.bank(player) + "$");
    }
}
