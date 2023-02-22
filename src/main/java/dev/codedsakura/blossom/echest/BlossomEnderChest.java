package dev.codedsakura.blossom.echest;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.codedsakura.blossom.lib.config.ConfigManager;
import dev.codedsakura.blossom.lib.permissions.Permissions;
import dev.codedsakura.blossom.lib.utils.CustomLogger;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.apache.logging.log4j.core.Logger;

import java.util.Arrays;

import static net.minecraft.server.command.CommandManager.literal;

public class BlossomEnderChest implements ModInitializer {
    static BlossomEnderChestConfig CONFIG = ConfigManager.register(BlossomEnderChestConfig.class, "BlossomEnderChest.json", newConfig -> CONFIG = newConfig);
    public static final Logger LOGGER = CustomLogger.createLogger("BlossomEnderChest");

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            CommandDispatcher<ServerCommandSource> dispatcher = server.getCommandManager().getDispatcher();
            LOGGER.debug(CONFIG.commands);
            Arrays.stream(CONFIG.commands)
                    .forEach(cmdName -> dispatcher
                            .register(literal(cmdName)
                                    .requires(Permissions.require("blossom.ender-chest", true)
                                            .and(Permissions.require("blossom.ender-chest.disallowed", false).negate()))
                                    .executes(this::run)));
        });
    }

    private int run(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();

        // see EnderChestBlock#onUse
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (syncId, inventory, playerEntity) -> GenericContainerScreenHandler.createGeneric9x3(
                        syncId, inventory, playerEntity.getEnderChestInventory()
                ),
                Text.translatable(CONFIG.nameTranslationKey)
                        .styled(s -> s.withColor(TextColor.parse(CONFIG.nameColor)))
        ));
        player.incrementStat(Stats.OPEN_ENDERCHEST);

        return Command.SINGLE_SUCCESS;
    }
}
