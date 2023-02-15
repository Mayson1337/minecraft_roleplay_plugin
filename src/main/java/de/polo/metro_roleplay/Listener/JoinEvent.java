package de.polo.metro_roleplay.Listener;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.PlayerUtils.DeathUtil;
import de.polo.metro_roleplay.Utils.LocationManager;
import de.polo.metro_roleplay.commands.aduty;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import de.polo.metro_roleplay.Utils.PlayerManager;

import java.sql.SQLException;

public class  JoinEvent implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws SQLException {
        Player player = event.getPlayer();
        event.setJoinMessage("");
        if (PlayerManager.isCreated(String.valueOf(player.getUniqueId()))) {
            //PlayerManager.money = PlayerManager.playerMoney(String.valueOf(player.getUniqueId()));
            PlayerManager.startTimeTracker(player);
            PlayerManager.loadPlayer(player);
            PlayerManager.updatePlayer(player.getUniqueId().toString(), player.getName());
            PlayerManager.rang.put(player.getName(), (String) PlayerManager.playerRang(player)); // Spieler in die HashMap packen für Rangsynchronisation ohne erneute DB Abfrage
            if (PlayerManager.rang.get(player.getName()).equalsIgnoreCase("Administrator") || PlayerManager.rang.get(player.getName()).equalsIgnoreCase("Fraktionsmanager") || PlayerManager.rang.get(player.getName()).equalsIgnoreCase("Moderator") || PlayerManager.rang.get(player.getName()).equalsIgnoreCase("Supporter")) {
                PlayerManager.onPlayer.put(player.getUniqueId().toString(), true);
                player.sendMessage(Main.debug_prefix + "In HashMap 'onPlayer' als '" + PlayerManager.rang.get(player.getName()) + "' hinzugefügt");
                player.setDisplayName("§8[§7Team§8]§7 " + player.getName());
                player.getPlayer().setPlayerListName("§8[§7Team§8]§7 " + player.getName());
                player.getPlayer().setCustomName("§8[§7Team§8]§7 " + player.getName());
                player.setCustomNameVisible(true);
            } else {
                PlayerManager.onPlayer.put(player.getUniqueId().toString(), false);
                player.sendMessage(Main.debug_prefix + "In HashMap 'onPlayer' mit value 'false' hinzugefügt");
                player.setDisplayName("§7" + player.getName());
                player.getPlayer().setPlayerListName("§7" + player.getName());
                player.getPlayer().setCustomName("§7" + player.getName());
                player.setCustomNameVisible(true);
            }
            if (DeathUtil.isDead(player)) {
                DeathUtil.killPlayer(player);
            }
            player.sendMessage(Main.debug_prefix + "Dein Bargeld beträgt: " + PlayerManager.money(player) + "§7$");
            player.sendMessage(Main.debug_prefix + "Dein Bankguthaben beträgt: " + PlayerManager.bank(player) + "§7$");
            player.sendMessage(Main.debug_prefix + "Dein Nutzer-Rang: " + PlayerManager.rang.get(player.getName()));

            aduty.send_message("§c" + player.getName() + "§7 hat den Server betreten.");
        } else {
            player.sendMessage(" ");
            player.sendMessage("§cMetro §8»§7 Herzlich Wilkommen in Metro - the City of Future, " + player.getName() + "!");
            player.sendMessage(" ");
            LocationManager.useLocation(player, "Spawn");
            player.getWorld().playEffect(player.getLocation().add(0.0D, 0.0D, 0.0D), Effect.ENDER_SIGNAL, 1);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1,2);
        }
    }
}
