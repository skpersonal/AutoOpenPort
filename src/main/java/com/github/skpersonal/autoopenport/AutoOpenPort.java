package com.github.skpersonal.autoopenport;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AutoOpenPort extends JavaPlugin {

    private int port;
    private boolean available;

    @Override
    public void onEnable() {
        // Plugin startup logic
        available = UPnP.isUPnPAvailable();
        if (available) {
            port = Bukkit.getServer().getPort();
            Bukkit.getServer().getLogger().info("Opening TCP port: " + port);
            if (UPnP.openPortTCP(port)) {
                Bukkit.getServer().getLogger().info("Opened TCP port: " + port);
            } else {
                Bukkit.getServer().getLogger().info("Opening port is rejected.");
                Bukkit.getServer().getLogger().info("Disabling plugin...");
                available = false;
                Bukkit.getServer().getPluginManager().disablePlugin(this);
            }
        } else {
            Bukkit.getServer().getLogger().info("UPnP is not available");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (available) {
            Bukkit.getServer().getLogger().info("Closing TCP port: " + port);
            if (UPnP.closePortTCP(port)) {
                Bukkit.getServer().getLogger().info("Closed TCP port: " + port);
            } else {
                Bukkit.getServer().getLogger().info("Closing port is rejected.");
            }
        }
    }
}
