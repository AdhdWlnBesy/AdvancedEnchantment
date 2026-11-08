package com.advanced_enchants.event;

import com.advanced_enchants.gui.CompendiumWindow;
import com.advanced_enchants.register.ModItems;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TypedActionResult;

/**
 * 打开附魔指南界面。
 *
 * @see CompendiumWindow
 */
public class OpenCompendium {
    // 注册打开附魔指南的交互。
    public static void mainEvent() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack held = player.getStackInHand(hand);
            if (held.isOf(ModItems.ADVANCED_ENCHANTS_COMPENDIUM)) {
                if (world.isClient) {
                    MinecraftClient.getInstance().setScreen(new CompendiumWindow(player));
                    player.playSound(SoundEvents.ITEM_BOOK_PAGE_TURN, 1, 1);
                    return TypedActionResult.success(held);
                }
            }
            return TypedActionResult.pass(held);
        });
    }
}
