package com.biteforcemc.printermode.handler;

import com.biteforcemc.printermode.Main;
import lombok.Getter;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class PrinterHandler {

    @Getter
    public HashMap<UUID, Double> printerPlayers = new HashMap<UUID, Double>();
    @Getter
    public HashMap<String, ItemStack[]> playerInventories = new HashMap<String, ItemStack[]>();

    public void updateMode(Player p, final boolean PrinterMode) {
        if(!PrinterMode) {
            //disable printer mode
            printerPlayers.remove(p.getUniqueId());
            p.getInventory().setContents(getPlayerInventories().get(p.getUniqueId() + ".items"));
            p.getInventory().setArmorContents(getPlayerInventories().get(p.getUniqueId() + ".armor"));
            getPlayerInventories().remove(p.getUniqueId() + ".items");
            getPlayerInventories().remove(p.getUniqueId() + ".armor");
            p.setGameMode(GameMode.SURVIVAL);
        } else {
            //enable printer mode
            printerPlayers.put(p.getUniqueId(), (double) 0);
            getPlayerInventories().put(p.getUniqueId() + ".armor", p.getInventory().getArmorContents());
            getPlayerInventories().put(p.getUniqueId() + ".items", p.getInventory().getContents());
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);

            p.setGameMode(GameMode.CREATIVE);
        }
    }
}
