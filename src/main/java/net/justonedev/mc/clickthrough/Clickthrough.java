package net.justonedev.mc.clickthrough;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Clickthrough extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new ChestEvents(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
