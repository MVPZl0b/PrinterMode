package com.biteforcemc.printermode.objects;

import org.bukkit.configuration.file.FileConfiguration;

public enum Messages {
    NO_PERM,
    USER_NOT_FOUND,
    RELOAD_SUCCESS,

    PRINTER_MODE_ENABLE,
    PRINTER_MODE_DISABLE,
    NOT_IN_SHOP,
    INSUFFICIENT_FUNDS,
    FUNDS_SPENT,
    NOT_IN_FACTIONS_TERRITORY;

    private String val;

    Messages() {
    }

    public static void init(FileConfiguration config) {
        for (Messages s : values()) {
            s.set(config.getString("Messages." + s.name()));
        }
    }

    public String get() {
        return val;
    }

    public void set(String val) {
        this.val = val;
    }
}
