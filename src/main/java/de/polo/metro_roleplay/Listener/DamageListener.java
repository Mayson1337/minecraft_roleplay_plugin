package de.polo.metro_roleplay.Listener;

import de.polo.metro_roleplay.commands.aduty;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity()).getPlayer();
            if (aduty.aduty.get(player.getName()) != null) {
                event.setCancelled(true);
            } else {
                event.setCancelled(false);
            }
        }
    }
}
