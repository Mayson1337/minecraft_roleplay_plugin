package de.polo.metro_roleplay.Utils;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.MySQl.MySQL;
import de.polo.metro_roleplay.PlayerUtils.PayDayUtil;
import de.polo.metro_roleplay.commands.aduty;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.bukkit.Bukkit.getPlayer;

public class PlayerManager {
    public static HashMap<String, Integer> money = new HashMap<String, Integer>();
    public static HashMap<String, Integer> bank = new HashMap<String, Integer>();
    public static HashMap<String, String> firstname = new HashMap<String, String>();
    public static HashMap<String, String> lastname = new HashMap<String, String>();
    //public static String rang = "Spieler";
    public static HashMap<String, String> rang = new HashMap<String, String>();
    public static HashMap<String, Boolean> onPlayer = new HashMap<String, Boolean>();
    public static HashMap<String, Integer> visum = new HashMap<String, Integer>();
    public static boolean isCreated(String uuid) {

        try {
            Statement statement = MySQL.getStatement();
            assert statement != null;
            ResultSet result = statement.executeQuery("SELECT `uuid` FROM `players` WHERE `uuid` = '" + uuid + "'");
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = MySQL.getStatement();
            ResultSet result = statement.executeQuery("SELECT `uuid` FROM `players` WHERE `uuid` = '" + uuid + "'");
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            Date date = new Date();
            String newDate = formatter.format(date);
            statement.execute("INSERT INTO `players` (`uuid`, `firstjoin`) VALUES ('" + uuid + "', '" + newDate + "')");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public static void updatePlayer(String uuid, String name) {
        try {
            Statement statement = MySQL.getStatement();
            statement.executeUpdate("UPDATE `players` SET `player_name` = '" + name + "' WHERE uuid = '"+ uuid + "'");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Serializable playerRang(Player player) {
        String uuid = player.getUniqueId().toString();
        try {
            Statement statement = MySQL.getStatement();
            assert statement != null;
            ResultSet result = statement.executeQuery("SELECT `player_rank` FROM `players` WHERE `uuid` = '" + uuid + "'");
            if (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void loadPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        try {
            Statement statement = MySQL.getStatement();
            assert statement != null;
            ResultSet name = statement.executeQuery("SELECT `firstname`, `lastname`, `bargeld`, `bank`, `visum`, `faction`, `faction_grade` FROM `players` WHERE `uuid` = '" + uuid + "'");
            if (name.next()) {
                firstname.put(player.getUniqueId().toString(), name.getString(1));
                lastname.put(player.getUniqueId().toString(), name.getString(2));
                money.put(player.getUniqueId().toString(), name.getInt(3));
                bank.put(player.getUniqueId().toString(), name.getInt(4));
                visum.put(player.getUniqueId().toString(), name.getInt(5));
                if (name.getString(6) != null) {
                    FactionManager.player_in_faction.put(player.getUniqueId().toString(), name.getString(6));
                    FactionManager.player_in_faction_grade.put(player.getUniqueId().toString(), name.getInt(7));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayer(Player player) throws SQLException {
        String uuid = player.getUniqueId().toString();
        Statement statement = MySQL.getStatement();
        assert statement != null;
        money.remove(uuid);
        bank.remove(uuid);
        firstname.remove(uuid);
        lastname.remove(uuid);
        aduty.aduty.remove(uuid);
        onPlayer.remove(uuid);
        statement.executeUpdate("UPDATE `players` SET `player_rank` = " + rang.get(player.getName()) + " WHERE `uuid` = '" + uuid + "'");
        rang.remove(player.getName());
        visum.remove(uuid);
        FactionManager.player_in_faction_grade.remove(uuid);
        FactionManager.player_in_faction.remove(uuid);
    }

    public static Serializable createCpAccount(String uuid, String email, String password) {
        try {
            Statement statement = MySQL.getStatement();
            assert statement != null;
            String query = "UPDATE `players` SET `email` = '" + email + "', `password` = '" + password + "' WHERE `uuid` = '" + uuid + "'";
            PreparedStatement preparedStatement = MySQL.connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void add1MinutePlaytime(Player player) {
        try {
            String uuid = player.getUniqueId().toString();
            Statement statement = MySQL.getStatement();
            assert statement != null;
            ResultSet result = statement.executeQuery("SELECT `playtime_hours`, `playtime_minutes`, `current_hours`, `needed_hours`, `visum` FROM `players` WHERE `uuid` = '" + uuid + "'");
            if (result.next()) {
                int hours = result.getInt(1) + 1;
                int minutes =  result.getInt(2);
                int newMinutes = result.getInt(2) + 1;
                int current_hours = result.getInt(3);
                int needed_hours = result.getInt(4);
                int visum = result.getInt(5) + 1;
                if (minutes >= 60) {
                    if (current_hours >= needed_hours) {
                        needed_hours = needed_hours + 4;
                        PayDayUtil.givePayDay(player);
                        statement.executeUpdate("UPDATE `players` SET `playtime_hours` = " + hours + ", `playtime_minutes` = 1, `current_hours` = 0, `needed_hours` = " + needed_hours + ", `visum` = " + visum + " WHERE `uuid` = '" + uuid + "'");
                        player.sendMessage(Main.prefix + "Aufgrund deiner Spielzeit bist du nun Visumstufe §c" + visum + "§7!");
                    } else {
                        PayDayUtil.givePayDay(player);
                        current_hours = current_hours + 1;
                        statement.executeUpdate("UPDATE `players` SET `playtime_hours` = " + hours + ", `playtime_minutes` = 1, `current_hours` = " + current_hours + " WHERE `uuid` = '" + uuid + "'");
                    }
                } else {
                    statement.executeUpdate("UPDATE `players` SET `playtime_minutes` = " + newMinutes + " WHERE `uuid` = '" + uuid + "'");
                    if (newMinutes == 56) {
                        player.sendMessage(Main.PayDay_prefix + "Du erhälst in 5 Minuten deinen PayDay.");
                    } else if (newMinutes == 58) {
                        player.sendMessage(Main.PayDay_prefix + "Du erhälst in 3 Minuten deinen PayDay.");
                    } else if (newMinutes == 60) {
                        player.sendMessage(Main.PayDay_prefix + "Du erhälst in 1 Minute deinen PayDay.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Serializable addMoney(Player player, int amount) throws SQLException {
        Statement statement = MySQL.getStatement();
        assert statement != null;
        String uuid = player.getUniqueId().toString();
        int oldmoney = money.get(uuid);
        money.remove(uuid);
        money.put(uuid, oldmoney + amount);
        ResultSet result = statement.executeQuery("SELECT `bargeld` FROM `players` WHERE `uuid` = '" + uuid + "'");
        if (result.next()) {
            int res =  result.getInt(1);
            statement.executeUpdate("UPDATE `players` SET `bargeld` = " + money.get(uuid) + " WHERE `uuid` = '" + uuid + "'");
        }
        return 0;
    }

    public static Serializable removeMoney(Player player, int amount) throws SQLException {
        Statement statement = MySQL.getStatement();
        assert statement != null;
        String uuid = player.getUniqueId().toString();
        int oldmoney = money.get(uuid);
        money.remove(uuid);
        money.put(uuid, oldmoney - amount);
        ResultSet result = statement.executeQuery("SELECT `bargeld` FROM `players` WHERE `uuid` = '" + uuid + "'");
        if (result.next()) {
            int res =  result.getInt(1);
            statement.executeUpdate("UPDATE `players` SET `bargeld` = " + money.get(uuid) + " WHERE `uuid` = '" + uuid + "'");
        }
        return 0;
    }

    public static Serializable addBankMoney(Player player, int amount) throws SQLException {
        Statement statement = MySQL.getStatement();
        assert statement != null;
        String uuid = player.getUniqueId().toString();
        int oldmoney = bank.get(uuid);
        bank.remove(uuid);
        bank.put(uuid, oldmoney + amount);
        ResultSet result = statement.executeQuery("SELECT `bank` FROM `players` WHERE `uuid` = '" + uuid + "'");
        if (result.next()) {
            int res =  result.getInt(1);
            statement.executeUpdate("UPDATE `players` SET `bank` = " + bank.get(uuid) + " WHERE `uuid` = '" + uuid + "'");
        }
        return 0;
    }
    public static Serializable removeBankMoney(Player player, int amount) throws SQLException {
        Statement statement = MySQL.getStatement();
        assert statement != null;
        String uuid = player.getUniqueId().toString();
        int oldmoney = bank.get(uuid);
        bank.remove(uuid);
        bank.put(uuid, oldmoney - amount);
        ResultSet result = statement.executeQuery("SELECT `bank` FROM `players` WHERE `uuid` = '" + uuid + "'");
        if (result.next()) {
            int res =  result.getInt(1);
            statement.executeUpdate("UPDATE `players` SET `bank` = " + bank.get(uuid) + " WHERE `uuid` = '" + uuid + "'");
        }
        return 0;
    }


    public static Serializable updatePlayerTeam(String uuid, String rank) {
        try {
            Statement statement = MySQL.getStatement();
            statement.executeUpdate("UPDATE `players` SET `player_rank` = '" + rank + "' WHERE uuid = '"+ uuid + "'");
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int money(Player player) {
        String uuid = player.getUniqueId().toString();
        return money.get(uuid);
    }

    public static int bank(Player player) {
        String uuid = player.getUniqueId().toString();
        return bank.get(uuid);
    }

    public static String firstname(Player player) {
        String uuid = player.getUniqueId().toString();
        return firstname.get(uuid);
    }

    public static String lastname(Player player) {
        String uuid = player.getUniqueId().toString();
        return lastname.get(uuid);
    }

    public static int visum(Player player) {
        String uuid = player.getUniqueId().toString();
        return visum.get(uuid);
    }

    public static boolean isTeam(Player player) {
        return onPlayer.get(player.getUniqueId().toString()) != null;
    }
    public static String rang(Player player) {
        return rang.get(player.getName());
    }

    public static void startTimeTracker(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                add1MinutePlaytime(player);
                //player.sendMessage(Main.debug_prefix + "Es wurde 1 Minute zur Levelzeit hinzugefügt.");

            }
        }.runTaskTimer(Main.getInstance(), 20*2, 20*60);
    }
}
