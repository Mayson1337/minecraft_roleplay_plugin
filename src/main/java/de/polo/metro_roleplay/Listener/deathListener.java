package de.polo.metro_roleplay.Listener;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.PlayerUtils.DeathUtil;
import de.polo.metro_roleplay.Utils.LocationManager;
import de.polo.metro_roleplay.commands.aduty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class deathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity()).getPlayer();
            event.setKeepInventory(true);
            assert player != null;
            DeathUtil.startDeathTimer(player);
            aduty.send_message(Main.admin_info + "§c" + player.getName() + "§7 starb.");
        }
    }
}
