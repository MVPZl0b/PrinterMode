package com.biteforcemc.printermode.objects;

import org.bukkit.configuration.file.FileConfiguration;

public enum Permissions {
    ADMIN,
    PRINTER,
    RELOAD;

    private String val;

    Permissions() {
    }

    public static void init(FileConfiguration config) {
        for (Permissions s : values()) {
            s.set(config.getString("Permissions." + s.name().toLowerCase()));
        }
    }

    public String get() {
        return val;
    }

    public void set(String val) {
        this.val = val;
    }
}
