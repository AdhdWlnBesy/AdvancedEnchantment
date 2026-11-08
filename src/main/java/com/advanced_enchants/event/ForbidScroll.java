package com.advanced_enchants.event;

import com.advanced_enchants.register.ModTags;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.screen.GrindstoneScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

/**
 * 卷轴使用限制。
 *
 * @see BookToScroll
 */
public class ForbidScroll {
    // 注册卷轴使用限制。
    public static void mainEvent() {
        ServerTickEvents.END_SERVER_TICK.register((server) -> {
            if (server.getTicks() % 2 == 0) {
                for (PlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    ScreenHandler handler = player.currentScreenHandler;
                    if (handler instanceof EnchantmentScreenHandler) {
                        OutScroll(handler, 0, player);
                    } else if (handler instanceof AnvilScreenHandler || handler instanceof GrindstoneScreenHandler) {
                        OutScroll(handler, 0, player);
                        OutScroll(handler, 1, player);
                    }
                }
            }
        });
    }

    private static void OutScroll(ScreenHandler screenHandler, int slotIndex, PlayerEntity player) {
        if (slotIndex >= screenHandler.slots.size()) {
            return;
        }
        Slot slot = screenHandler.slots.get(slotIndex);
        if (slot.getStack().isIn(ModTags.ADVANCED_ENCHANTS_SCROLL)) {
            screenHandler.quickMove(player, slotIndex);
        }
    }
}
