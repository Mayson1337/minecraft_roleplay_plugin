package de.polo.metro_roleplay.commands;

import de.polo.metro_roleplay.Main;
import de.polo.metro_roleplay.PlayerUtils.BankingUtils;
import de.polo.metro_roleplay.Utils.LocationManager;
import de.polo.metro_roleplay.Utils.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class bankCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String syntax_error = Main.error + "Syntax-Fehler: /bank [Info/Einzahlen/Abbuchen/Überweisen] [Betrag] [<Spieler>]";
        if (LocationManager.nearATM(player)) {
            if (args.length == 0) {
                player.sendMessage(syntax_error);
            } else if (args.length >= 2) {
                int amount = Integer.parseInt(args[1]);
                if (amount >= 1) {
                    try {
                        if (args[0].equalsIgnoreCase("info")) {
                            BankingUtils.sendKontoauszug(player);
                        } else if (args[0].equalsIgnoreCase("einzahlen")) {
                            if (PlayerManager.money(player) >= amount) {
                                PlayerManager.removeMoney(player, amount);
                                PlayerManager.addBankMoney(player, amount);
                                player.sendMessage(Main.bank_prefix + "Du hast §c" + amount + "§7$ auf dein Bankkonto eingezahlt.");
                                BankingUtils.sendKontoauszug(player);
                            }
                        } else if (args[0].equalsIgnoreCase("abbuchen")) {
                            if (PlayerManager.bank(player) >= amount) {
                                PlayerManager.removeBankMoney(player, amount);
                                PlayerManager.addMoney(player, amount);
                                player.sendMessage(Main.bank_prefix + "Du hast §c" + amount + "§7$ von deinem Bankkonto abgebucht.");
                                BankingUtils.sendKontoauszug(player);
                            }
                        } else if (args[0].equalsIgnoreCase("überweisen")) {
                            if (args.length >= 3) {
                                Player targetplayer = Bukkit.getPlayer(args[2]);
                                assert targetplayer != null;
                                if (targetplayer.isOnline()) {
                                    if (PlayerManager.bank(player) >= amount) {
                                        PlayerManager.removeBankMoney(player, amount);
                                        PlayerManager.addBankMoney(targetplayer, amount);
                                        player.sendMessage(Main.bank_prefix + "Du hast §c" + amount + "§7$ auf das Bankkonto von §c" + targetplayer.getName() + "§7 überwiesen.");
                                        targetplayer.sendMessage(Main.bank_prefix + "§c" + player.getName() + "§7 hat dir §c" + amount + "$§7 überwiesen");
                                        BankingUtils.sendBankChangeReason(player, "Überweisung");
                                        BankingUtils.sendBankChangeReason(targetplayer, "Überweisung");
                                    }
                                } else {
                                    player.sendMessage(Main.error + "§c" + args[2] + "§7 ist nicht online.");
                                }
                            } else {
                                player.sendMessage(syntax_error);
                            }
                        } else {
                            player.sendMessage(syntax_error);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    player.sendMessage(Main.error + "Die Bankautomaten können nicht mit Negativen Zahlen umgehen...");
                }
            } else {
                player.sendMessage(syntax_error);
            }
        } else {
            player.sendMessage(Main.error + "Du bist nicht in der nähe eines Bankautomaten.");
        }
        return false;
    }
}
