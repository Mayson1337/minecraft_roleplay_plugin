package de.polo.metro_roleplay.Utils;

import de.polo.metro_roleplay.MySQl.MySQL;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class FactionManager {
    public static HashMap<String, String> player_in_faction = new HashMap<String, String>();
    public static HashMap<String, Integer> player_in_faction_grade = new HashMap<String, Integer>();

    public static String faction(Player player) {
        String uuid = player.getUniqueId().toString();
        return player_in_faction.get(uuid);
    }

    public static Integer faction_grade(Player player) {
        String uuid = player.getUniqueId().toString();
        return player_in_faction_grade.get(uuid);
    }
    public static void setPlayerInFrak(Player player, String frak, Integer rang) throws SQLException {
        String uuid = player.getUniqueId().toString();
        if (faction(player) != null) {
            player_in_faction.remove(uuid);
            player_in_faction_grade.remove(uuid);
        }
        player_in_faction.put(uuid, frak);
        player_in_faction_grade.put(uuid, rang);
        Statement statement = MySQL.getStatement();
        assert statement != null;
        statement.executeUpdate("UPDATE `players` SET `faction` = '" + frak + "', `faction_grade` = " + rang + " WHERE `uuid` = '" + uuid + "'");
    }

    public static void removePlayerFromFrak(Player player) throws SQLException {
        String uuid = player.getUniqueId().toString();
        if (faction(player) != null) {
            player_in_faction.remove(uuid);
            player_in_faction_grade.remove(uuid);
        }
        Statement statement = MySQL.getStatement();
        assert statement != null;
        statement.executeUpdate("UPDATE `players` SET `faction` = 'Fraktionslos', `faction_grade` = 0 WHERE `uuid` = '" + uuid + "'");
    }
}
