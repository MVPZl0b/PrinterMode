package com.biteforcemc.printermode;

import com.biteforcemc.printermode.commands.PrinterCMD;
import com.biteforcemc.printermode.handler.PrinterHandler;
import com.biteforcemc.printermode.listeners.PrinterListener;
import com.biteforcemc.printermode.objects.Messages;
import com.biteforcemc.printermode.objects.Permissions;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    @Getter  public static Main instance;
    @Getter public static PrinterHandler handler;
    private static Economy econ = null;
    File file = new File(getDataFolder(), "config.yml");

    public void onEnable() {
        instance = this;
        register();
        createConfig();
        Permissions.init(getConfig());
        Messages.init(getConfig());
        registerHandlers();
        if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    public void onDisable() {
        saveDefaultConfig();
    }

    private void register() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        getCommand("Printer").setExecutor(new PrinterCMD());
        pm.registerEvents(new PrinterListener(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    public static Economy getEconomy() {
        return econ;
    }
    private void registerHandlers() {
        handler = new com.biteforcemc.printermode.handler.PrinterHandler();
    }

}
