package de.polo.metro_roleplay.Utils;

import de.polo.metro_roleplay.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LocationManager {

    public static void setLocation(String name, Player p){
        File ordner = new File("plugins//Locations//");
        File file = new File("plugins//Locations//" + name + ".yml");

        if(!ordner.exists()){
            ordner.mkdir();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Location loc = p.getLocation();

        cfg.set("X", loc.getBlockX());
        cfg.set("Y", loc.getBlockY());
        cfg.set("Z", loc.getBlockZ());
        cfg.set("Welt", loc.getWorld().getName());
        cfg.set("Yaw", loc.getYaw());
        cfg.set("Pitch", loc.getPitch());

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void useLocation(Player p, String name){
        File file = new File("plugins//Locations//" + name + ".yml");

        if(!file.exists()){
            p.sendMessage(Main.prefix + "Die Location wurde nicht gefunden");
        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        World welt = Bukkit.getWorld(cfg.getString("Welt"));
        double yaw = cfg.getDouble("Yaw");
        double pitch = cfg.getDouble("Pitch");

        p.teleport(new Location(welt, cfg.getDouble("X"), cfg.getDouble("Y"), cfg.getDouble("Z"), (float) yaw, (float) pitch));
    }

    public static double getDistanceBetweenCoords(Player player, String name) {
        File file = new File("plugins//Locations//" + name + ".yml");
        if(!file.exists()){
            player.sendMessage(Main.prefix + "Die Location wurde nicht gefunden");
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        World welt = Bukkit.getWorld(Objects.requireNonNull(cfg.getString("Welt")));
        double yaw = cfg.getDouble("Yaw");
        double pitch = cfg.getDouble("Pitch");

        return player.getLocation().distance(new Location(welt, cfg.getDouble("X"), cfg.getDouble("Y"), cfg.getDouble("Z"), (float) yaw, (float) pitch));
    }

    public static void setATM(Player p){
        File ordner = new File("plugins//Bankautomaten//");
        int id = Objects.requireNonNull(ordner.list()).length + 1;
        File file = new File("plugins//Bankautomaten//atm_" + id + ".yml");
        p.sendMessage(Main.prefix + "Du hast eine ATM-Location (ATM-" + id + ") gesetzt");
        if(!ordner.exists()){
            ordner.mkdir();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Location loc = p.getLocation();

        cfg.set("X", loc.getBlockX());
        cfg.set("Y", loc.getBlockY());
        cfg.set("Z", loc.getBlockZ());
        cfg.set("Welt", loc.getWorld().getName());
        cfg.set("Yaw", loc.getYaw());
        cfg.set("Pitch", loc.getPitch());

        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getDistanceBetweenATM(Player player, String name) {
        File file = new File("plugins//Bankautomaten//" + name + ".yml");
        if(!file.exists()){
            player.sendMessage(Main.prefix + "Die Location wurde nicht gefunden");
        }
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

        World welt = Bukkit.getWorld(Objects.requireNonNull(cfg.getString("Welt")));
        double yaw = cfg.getDouble("Yaw");
        double pitch = cfg.getDouble("Pitch");

        return player.getLocation().distance(new Location(welt, cfg.getDouble("X"), cfg.getDouble("Y"), cfg.getDouble("Z"), (float) yaw, (float) pitch));
    }

    public static boolean nearATM(Player player) {
        boolean isNearATM = false;
        File file = new File("plugins//Bankautomaten//");
        for (int i = 0; i < Objects.requireNonNull(file.list()).length; i++) {
            Bukkit.broadcastMessage(Main.debug_prefix + "ATM-Count: " + i);
            if (getDistanceBetweenATM(player, "atm_" + i) <= 3) {
                isNearATM = true;
            }
        }
        return isNearATM;
    }

}
