package com.advanced_enchants.event;

import com.advanced_enchants.datagen.AdvancementProvider;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

/**
 * 怪物升级进度。
 *
 * @see AdvancementProvider
 */
public class MobLevelupAdvancement {
    // 注册怪物升级进度。
    public static void mainEffect() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (server.getTicks() % 20 != 0) return;
            ServerWorld overworld = server.getWorld(World.OVERWORLD);
            if (overworld == null) return;
            long time = overworld.getTimeOfDay();
            for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
                if (time > 192000L * 10) AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "mob_levelup3");
                if (time > 192000L * 5) AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "mob_levelup2");
                if (time > 192000L) AdvancementProvider.SIMPLE_TRIGGER.trigger(serverPlayer, "mob_levelup");
            }
        });
    }
}
