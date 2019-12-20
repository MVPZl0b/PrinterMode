package com.biteforcemc.printermode.listeners;

import com.biteforcemc.printermode.Main;
import com.biteforcemc.printermode.objects.Messages;
import com.biteforcemc.printermode.util.Util;
import com.massivecraft.factions.*;
import com.massivecraft.factions.struct.Relation;
import net.brcdev.shopgui.ShopGuiPlusApi;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class PrinterListener implements Listener {


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        double price;
        ItemStack item = new ItemStack(e.getBlockPlaced().getType());
        if (Main.getHandler().getPrinterPlayers().containsKey(e.getPlayer().getUniqueId())) {
            price = ShopGuiPlusApi.getItemStackPriceBuy(e.getPlayer(), item);
            if (item.getType() == Material.REDSTONE_WIRE) {
                price = ShopGuiPlusApi.getItemStackPriceBuy(e.getPlayer(), new ItemStack(Material.REDSTONE));
            }
            if (item.getType() == Material.DIODE_BLOCK_OFF) {
                price = ShopGuiPlusApi.getItemStackPriceBuy(e.getPlayer(), new ItemStack(Material.DIODE));
            }
            if (item.getType() == Material.REDSTONE_COMPARATOR_OFF) {
                price = ShopGuiPlusApi.getItemStackPriceBuy(e.getPlayer(), new ItemStack(Material.REDSTONE_COMPARATOR));
            }
            if(item.getType() == Material.SUGAR_CANE_BLOCK) {
                price = ShopGuiPlusApi.getItemStackPriceBuy(e.getPlayer(), new ItemStack(Material.SUGAR_CANE));
            }
            if (price == -1) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Util.color(Messages.NOT_IN_SHOP.get()));
            } else {
                if(Main.getEconomy().getBalance(e.getPlayer()) < price){
                    e.setCancelled(true);
                }
                double value = Main.getHandler().getPrinterPlayers().get(e.getPlayer().getUniqueId());
                Main.getHandler().getPrinterPlayers().remove(e.getPlayer().getUniqueId());
                Main.getHandler().getPrinterPlayers().put(e.getPlayer().getUniqueId(), value + price);
                Main.getEconomy().withdrawPlayer(e.getPlayer(), price);
            }
        }
    }

    @EventHandler
    public void onChestOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
//            if (event.getInventory().getTitle().equals(ChatColor.RED + "Online Staff:") || event.getInventory().getTitle().contains("'s Inventory"))
//                return;
            if (!Main.getHandler().getPrinterPlayers().containsKey(event.getPlayer().getUniqueId()))
                return;
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!Main.getHandler().getPrinterPlayers().containsKey(event.getPlayer().getUniqueId()))
            return;
        event.setCancelled(true);
    }

/*
    @EventHandler
    public void onInventoryMove(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!Main.getHandler().getPrinterPlayers().contains(event.getWhoClicked().getUniqueId()))
            return;
        event.setCancelled(true);
    }
*/

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!Main.getHandler().getPrinterPlayers().containsKey(e.getPlayer().getUniqueId()))
            return;
        FPlayer fplayer = FPlayers.getInstance().getByPlayer(e.getPlayer());

        FLocation flocation = new FLocation(e.getTo());
        Faction faction = Board.getInstance().getFactionAt(flocation);

        if (faction != fplayer.getFaction()) {
            Main.getHandler().updateMode(e.getPlayer(), false);
            e.getPlayer().sendMessage(Util.color(Messages.PRINTER_MODE_DISABLE.get()));
            e.getPlayer().sendMessage(Util.color(Messages.NOT_IN_FACTIONS_TERRITORY.get()));
        }

        e.getPlayer().getWorld().getNearbyEntities(e.getPlayer().getLocation(), 50, 50, 50).stream().filter(Player.class::isInstance).map(player ->

                FPlayers.getInstance().getByPlayer((Player) player)

        ).forEach(fPlayer -> {

            Relation rel = fplayer.getFaction().getRelationTo(fPlayer.getFaction());

            if (rel == Relation.ENEMY) {

                Main.getHandler().updateMode(e.getPlayer(), false);

                e.getPlayer().sendMessage(Util.color(Messages.PRINTER_MODE_DISABLE.get()));
            }
        });
    }

}
