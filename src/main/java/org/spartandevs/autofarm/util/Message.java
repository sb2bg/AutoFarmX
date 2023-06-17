package org.spartandevs.autofarm.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Message {
    private static final String MSG_PREFIX = "&8&l[&e&lAutoFarmX&8&l] ";

    public static void sendSuccess(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MSG_PREFIX + "&a" + message));
    }

    public static void sendError(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MSG_PREFIX + "&c" + message));
    }

    public static void sendInfo(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', MSG_PREFIX + "&7" + message));
    }

    public static String capitalize(String str) {
        if (str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder(str.length());
        boolean capitalizeNext = true;

        for (char ch : str.toCharArray()) {
            if (Character.isWhitespace(ch)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                ch = Character.toTitleCase(ch);
                capitalizeNext = false;
            }

            result.append(ch);
        }

        return result.toString();
    }
}
