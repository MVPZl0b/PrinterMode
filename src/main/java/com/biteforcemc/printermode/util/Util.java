package com.biteforcemc.printermode.util;

import com.biteforcemc.printermode.objects.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static boolean checkPlayer(Player p) {
        return p != null;
    }

    public static String replacePlayer(String s, Player p) {
        return ChatColor.translateAlternateColorCodes('&', s.replace("%player%", p.getName()));
    }

    public static String replaceAmount(String s, Double amt) {
        return ChatColor.translateAlternateColorCodes('&', s.replace("%amount%", String.valueOf(amt)));
    }

    public static boolean checkPermissions(CommandSender p, String permission) {
        return p.hasPermission(Permissions.ADMIN.get()) || p.hasPermission(permission);
    }

    public static boolean checkPermissions(CommandSender p, String permission, String permission2) {
        return p.hasPermission(Permissions.ADMIN.get()) || p.hasPermission(permission) || p.hasPermission(permission2);
    }

    public static void sendMulti(Player p, String s) {
        p.sendMessage(s.replace(',', '\n').replace("[", "").replace("]", ""));
    }

    public static boolean isInt(final String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }
}
