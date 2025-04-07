package net.justonedev.mc.clickthrough;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public final class Clickthrough extends JavaPlugin implements CommandExecutor, TabCompleter {

    private static Clickthrough instance;

    public static final boolean ENABLE_FRAME_CLICKTHROUGH = true;
    public static final boolean ENABLE_SIGN_CLICKTHROUGH = true;

    public static boolean isEnableFrameClickthrough = ENABLE_FRAME_CLICKTHROUGH;
    public static boolean isEnableSignClickthrough = ENABLE_SIGN_CLICKTHROUGH;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Config.initialize();
        Bukkit.getPluginManager().registerEvents(new ChestEvents(this), this);
        PluginCommand command = getCommand("clickthrough");
        if (command != null) {
            command.setExecutor(this);
            command.setTabCompleter(this);
        } else {
            getLogger().warning("Command /clickthrough not found in plugin.yml");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, @Nonnull String[] args) {
        if (!(sender instanceof ConsoleCommandSender) && !sender.isOp()) {
            return false;
        }
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("§e/clickthrough reload - Reloads the config");
            return false;
        }
        Config.initialize();
        sender.sendMessage("Clickthrough Config reloaded:%n§eEnable Item Frame Clickthrough: %s%b%n§eEnable Sign Clickthrough: %s%b"
                .formatted(color(isEnableFrameClickthrough), isEnableFrameClickthrough, color(isEnableSignClickthrough), isEnableSignClickthrough));
        return true;
    }

    @Override
    public List<String> onTabComplete(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, String[] args) {
        if (args.length == 1) {
            return Stream.of("reload").filter(s -> s.startsWith(args[0])).toList();
        }
        return null;
    }

    private static String color(boolean enabled) {
        return enabled ? "§a" : "§c";
    }

    public static File getFolder() {
        return instance.getDataFolder();
    }
}
