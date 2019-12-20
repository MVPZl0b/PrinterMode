package com.biteforcemc.printermode.commands;

import com.biteforcemc.printermode.Main;
import com.biteforcemc.printermode.objects.Messages;
import com.biteforcemc.printermode.objects.Permissions;
import com.biteforcemc.printermode.util.Util;
import com.massivecraft.factions.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrinterCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String args[]) {
        if(cmd.getName().equalsIgnoreCase("printer")) {
            if(Util.checkPermissions(sender, Permissions.PRINTER.get())) {
                if(args.length == 0) {
                    Player player = (Player) sender;
                    FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);

                    FLocation flocation = new FLocation(player.getLocation());
                    Faction faction = Board.getInstance().getFactionAt(flocation);

                    if (faction.getId().equals(fplayer.getFactionId())) {

                        if (Main.getHandler().getPrinterPlayers().containsKey(player.getUniqueId())) {
                            player.sendMessage(Util.color(Messages.PRINTER_MODE_DISABLE.get()));
                            player.sendMessage(Util.replaceAmount(Messages.FUNDS_SPENT.get(), Main.getHandler().getPrinterPlayers().get(player.getUniqueId())));
                            Main.getHandler().updateMode(player, false);
                        } else {
                            Main.getHandler().updateMode(player, true);
                            player.sendMessage(Util.color(Messages.PRINTER_MODE_ENABLE.get()));
                        }
                    } else {
                        player.sendMessage("Not in faction territory!");
                    }
                }
            } else if(Util.checkPermissions(sender, Permissions.RELOAD.get()) && args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                Main.getInstance().reloadConfig();
                Permissions.init(Main.getInstance().getConfig());
                Messages.init(Main.getInstance().getConfig());
                sender.sendMessage(Util.color(Messages.RELOAD_SUCCESS.get()));
            }
        }
        return false;
    }
}
