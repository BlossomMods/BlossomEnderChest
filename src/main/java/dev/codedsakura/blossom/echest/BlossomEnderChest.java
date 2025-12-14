package dev.codedsakura.blossom.echest;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.codedsakura.blossom.lib.BlossomLib;
import dev.codedsakura.blossom.lib.config.ConfigManager;
import dev.codedsakura.blossom.lib.permissions.Permissions;
import dev.codedsakura.blossom.lib.text.TextUtils;
import dev.codedsakura.blossom.lib.utils.CustomLogger;
import net.fabricmc.api.ModInitializer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import org.apache.logging.log4j.core.Logger;

import java.util.Arrays;

import static net.minecraft.commands.Commands.literal;

public class BlossomEnderChest implements ModInitializer {
    static BlossomEnderChestConfig CONFIG = ConfigManager.register(BlossomEnderChestConfig.class, "BlossomEnderChest.json", newConfig -> CONFIG = newConfig);
    public static final Logger LOGGER = CustomLogger.createLogger("BlossomEnderChest");

    @Override
    public void onInitialize() {
        LOGGER.debug("loaded commands: {}", String.join(", ", CONFIG.commands));
        Arrays.stream(CONFIG.commands)
                .forEach(cmdName -> BlossomLib.addCommand(literal(cmdName)
                        .requires(Permissions.require("blossom.ender-chest", true)
                                .and(Permissions.require("blossom.ender-chest.disallowed", false).negate()))
                        .executes(this::run)));
    }

    private int run(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = ctx.getSource().getPlayerOrException();

        // see EnderChestBlock#onUse
        player.openMenu(new SimpleMenuProvider(
                (syncId, inventory, playerEntity) -> ChestMenu.threeRows(
                        syncId, inventory, playerEntity.getEnderChestInventory()
                ),
                Component.translatable(CONFIG.nameTranslationKey)
                        .withStyle(s -> s.withColor(TextUtils.parseColor(CONFIG.nameColor)))
        ));
        player.awardStat(Stats.OPEN_ENDERCHEST);

        return Command.SINGLE_SUCCESS;
    }
}
