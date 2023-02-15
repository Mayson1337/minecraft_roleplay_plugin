package de.polo.metro_roleplay;

import de.polo.metro_roleplay.Listener.*;
import de.polo.metro_roleplay.Utils.PlayerManager;
import de.polo.metro_roleplay.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Main extends JavaPlugin {
    public static String prefix = "§cMetro§8 » §7";
    public static String debug_prefix = "§8[§7§lDEBUG§8] §cMetro§8 » §7";
    public static String admin_prefix = "§cAdmin§8 » §7";
    public static String PayDay_prefix = "§3PayDay§8 » §7";
    public static String faction_prefix = "§9Fraktion§8 » §7";
    public static String support_prefix = "§3Support§8 » §7";
    public static String bank_prefix = "§3Bank§8 » §7";

    public static String error_nopermission = "§cFehler§8 » §7Für den ausgeführten Befehl hast du keine Rechte.";
    public static String error = "§cFehler§8 » §7";
    public static String admin_error = "§8[§c§lADMIN§8] §cFehler§8 » §7";
    public static String admin_info = "§8[§9§lINFO§8] §cAdmin§8 » §7";

    private static Main instance;
    public void onLoad() {
        instance = this;
    }
    @Override
    public void onEnable() {
        getLogger().info("§cMETRO RP STARTED.");
        registerListener();
        registerCommands();
    }

    private void registerListener(){

        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new DamageListener(), this);
        getServer().getPluginManager().registerEvents(new chatListener(), this);
        getServer().getPluginManager().registerEvents(new blockbreakListener(), this);
        getServer().getPluginManager().registerEvents(new blockplaceListener(), this);
        getServer().getPluginManager().registerEvents(new unknownCommandListener(), this);
        getServer().getPluginManager().registerEvents(new deathListener(), this);
    }

    private void registerCommands(){
        getCommand("aduty").setExecutor(new aduty());
        getCommand("setgroup").setExecutor(new setteamCommand());
        getCommand("geldbeutel").setExecutor(new geldbeutelCommand());
        getCommand("personalausweis").setExecutor(new personalausweisCommand());
        getCommand("teamchat").setExecutor(new teamchatCommand());
        getCommand("leadfrak").setExecutor(new leadfrakCommand());
        getCommand("fraktionschat").setExecutor(new fraktionschatCommand());
        getCommand("uninvite").setExecutor(new uninviteCommand());
        getCommand("setloc").setExecutor(new LocationCommand());
        getCommand("bank").setExecutor(new bankCommand());
        getCommand("setfraktion").setExecutor(new setfrakCommand());
        getCommand("adminuninvite").setExecutor(new adminuninviteCommand());
        getCommand("assistentchat").setExecutor(new assistentchatCommand());
        getCommand("support").setExecutor(new supportCommand());
        getCommand("cancelsupport").setExecutor(new cancelsupportCommand());
        getCommand("acceptsupport").setExecutor(new acceptticketCommand());
        getCommand("closesupport").setExecutor(new closeticketCommand());
        getCommand("target-ip").setExecutor(new targetipCommand());
        getCommand("tpto").setExecutor(new tptoCommand());
        getCommand("adminrevive").setExecutor(new adminReviveCommand());
        getCommand("registeratm").setExecutor(new ATMLocationCommand());
    }


    @Override
    public void onDisable() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            try {
                PlayerManager.savePlayer(players);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static Main getInstance() {
        return instance;
    }
}
