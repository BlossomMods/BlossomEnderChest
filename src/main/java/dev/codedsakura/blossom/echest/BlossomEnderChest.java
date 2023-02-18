package dev.codedsakura.blossom.echest;

import dev.codedsakura.blossom.lib.config.ConfigManager;
import dev.codedsakura.blossom.lib.utils.CustomLogger;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.core.Logger;

public class BlossomEnderChest implements ModInitializer {
    static BlossomEnderChestConfig CONFIG = ConfigManager.register(BlossomEnderChestConfig.class, "BlossomEnderChest.json", newConfig -> CONFIG = newConfig);
    public static final Logger LOGGER = CustomLogger.createLogger("BlossomEnderChest");

    @Override
    public void onInitialize() {
    }
}
