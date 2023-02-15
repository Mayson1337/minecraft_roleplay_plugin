package de.polo.metro_roleplay.Utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class SupportManager {
    public static HashMap<String, Boolean> ticketIsCreated = new HashMap<String, Boolean>();
    public static HashMap<String, String> ticketReason = new HashMap<String, String>();
    public static HashMap<String, String> ticketConnection = new HashMap<String, String>();
    public static HashMap<String, Boolean> isInConnection = new HashMap<String, Boolean>();

    public static boolean ticketCreated(Player player) {
        return ticketIsCreated.get(player.getUniqueId().toString()) != null;
    }

    public static void createTicket(Player player, String reason) {
        ticketIsCreated.put(player.getUniqueId().toString(), true);
        ticketReason.put(player.getUniqueId().toString(), reason);
    }

    public static void deleteTicket(Player player) {
        if (ticketCreated(player)) {
            ticketReason.remove(player.getUniqueId().toString());
            ticketIsCreated.remove(player.getUniqueId().toString());
        }
    }

    public static void createTicketConnection(Player player, Player targetplayer) {
        ticketConnection.put(player.getUniqueId().toString(), targetplayer.getUniqueId().toString());
        isInConnection.put(player.getUniqueId().toString(), true);
        isInConnection.put(targetplayer.getUniqueId().toString(), true);
    }
    public static void deleteTicketConnection(Player player, Player targetplayer) {
        ticketConnection.remove(player.getUniqueId().toString());
        deleteTicket(targetplayer);
        isInConnection.remove(targetplayer.getUniqueId().toString());
        isInConnection.remove(player.getUniqueId().toString());
    }
    public static String getConnection(Player player) {
        return ticketConnection.get(player.getUniqueId().toString());
    }

    public static boolean isInConnection(Player player) {
        return isInConnection.get(player.getUniqueId().toString()) != null;
    }
}
