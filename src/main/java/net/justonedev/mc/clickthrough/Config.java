package net.justonedev.mc.clickthrough;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {
    public static void initialize() {
        File f = new File(Clickthrough.getFolder(), "config.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

        if (!f.exists()) {
            // Default Values are already set
            cfg.set("Enable Item Frame Clickthrough", Clickthrough.ENABLE_FRAME_CLICKTHROUGH);
            cfg.set("Enable Sign Clickthrough", Clickthrough.ENABLE_SIGN_CLICKTHROUGH);
            saveCfg(f, cfg);
            return;
        }

        Map<String, Object> updateThese = new HashMap<>();

        Clickthrough.isEnableFrameClickthrough = getOrDefaultBoolean("Enable Item Frame Clickthrough", cfg, updateThese, Clickthrough.ENABLE_FRAME_CLICKTHROUGH);
        Clickthrough.isEnableSignClickthrough = getOrDefaultBoolean("Enable Sign Clickthrough", cfg, updateThese, Clickthrough.ENABLE_SIGN_CLICKTHROUGH);

        if (updateThese.isEmpty()) return;
        cfg = YamlConfiguration.loadConfiguration(f);	// Reload config
        for (Map.Entry<String, Object> entry : updateThese.entrySet()) {
            cfg.set(entry.getKey(), entry.getValue());
        }
        saveCfg(f, cfg);
    }

    private static boolean getOrDefaultBoolean(String key, YamlConfiguration cfg, Map<String, Object> updateThese, boolean defaultValue)
    {
        if (cfg.isSet(key)) return cfg.getBoolean(key);
        updateThese.put(key, defaultValue);
        return defaultValue;
    }

    private static void saveCfg(File f, YamlConfiguration cfg) {
        try {
            cfg.save(f);
        } catch (IOException ignored) {}
    }
}
